package com.moekr.shadow.panel.logic.service.impl;

import com.moekr.shadow.panel.data.TransactionWrapper;
import com.moekr.shadow.panel.data.TransactionWrapper.SafeMethod;
import com.moekr.shadow.panel.data.dao.NodeDAO;
import com.moekr.shadow.panel.data.dao.RecordDAO;
import com.moekr.shadow.panel.data.dao.UserDAO;
import com.moekr.shadow.panel.data.entity.Node;
import com.moekr.shadow.panel.data.entity.Record;
import com.moekr.shadow.panel.data.entity.User;
import com.moekr.shadow.panel.logic.service.RecordService;
import com.moekr.shadow.panel.logic.vo.data.DayData;
import com.moekr.shadow.panel.logic.vo.data.HourData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RecordServiceImpl implements RecordService {
	private static final long MEGA = 1024 * 1024;
	private static final Converter<Date, LocalDate> DATE_TO_LOCAL_DATE_CONVERTER = Jsr310Converters.DateToLocalDateConverter.INSTANCE;

	private final UserDAO userDAO;
	private final NodeDAO nodeDAO;
	private final RecordDAO recordDAO;
	private final TransactionWrapper wrapper;

	private final Map<Integer, Map<Integer, Long>> buffer = new HashMap<>();

	@Autowired
	public RecordServiceImpl(UserDAO userDAO, NodeDAO nodeDAO, RecordDAO recordDAO, TransactionWrapper wrapper) {
		this.userDAO = userDAO;
		this.nodeDAO = nodeDAO;
		this.recordDAO = recordDAO;
		this.wrapper = wrapper;
	}

	@Override
	public void record(int nodeId, String statistics) {
		synchronized (buffer) {
			Map<Integer, Long> map = buffer.computeIfAbsent(nodeId, k -> new HashMap<>());
			JSONArray array = new JSONArray(statistics);
			for (int index = 0; index < array.length(); index++) {
				JSONObject object = array.optJSONObject(index);
				if (object == null) continue;
				int id = object.optInt("id");
				long value = object.optLong("value");
				map.put(id, map.getOrDefault(id, 0L) + value);
			}
		}
	}

	@Override
	public String hourData(int userId, int days) {
		return parseHourData(recordDAO.hourData(userId, days));
	}

	@Override
	public String dayData(int userId, int days) {
		return parseDayData(recordDAO.dayData(userId, days), days);
	}

	@Override
	public String hourData(int days) {
		return parseHourData(recordDAO.hourData(days));
	}

	@Override
	public String dayData(int days) {
		return parseDayData(recordDAO.dayData(days), days);
	}

	private String parseHourData(List<Map<String, Object>> dataList) {
		Map<Integer, Long> dataMap = dataList.stream()
				.map(row -> new HourData((Integer) row.get("hour"), ((BigDecimal) row.get("data")).longValue()))
				.collect(Collectors.toMap(HourData::getHour, HourData::getData, (a, b) -> a + b));
		JSONArray dataArray = new JSONArray();
		for (int hour = 0; hour < 24; hour++) {
			JSONObject data = new JSONObject();
			data.put("x", String.valueOf(hour) + "时");
			data.put("y", dataMap.getOrDefault(hour, 0L) / MEGA);
			dataArray.put(data);
		}
		return dataArray.toString();
	}

	private String parseDayData(List<Map<String, Object>> dataList, int days) {
		Map<LocalDate, Long> dataMap = dataList.stream()
				.map(row -> new DayData(DATE_TO_LOCAL_DATE_CONVERTER.convert((Date) row.get("date")), ((BigDecimal) row.get("data")).longValue()))
				.collect(Collectors.toMap(DayData::getDate, DayData::getData, (a, b) -> a + b));
		JSONArray dataArray = new JSONArray();
		LocalDate end = LocalDate.now().plusDays(1);
		LocalDate date = end.minusDays(days);
		while (!date.equals(end)) {
			JSONObject data = new JSONObject();
			data.put("x", date.getMonthValue() + "月" + date.getDayOfMonth() + "日");
			data.put("y", dataMap.getOrDefault(date, 0L) / MEGA);
			dataArray.put(data);
			date = date.plusDays(1);
		}
		return dataArray.toString();
	}

	@Scheduled(cron = "10 0 * * * *")
	protected void scheduledFlush() {
		wrapper.wrap((SafeMethod) this::flush);
	}

	private void flush() {
		synchronized (buffer) {
			LocalDateTime now = LocalDateTime.now().minusHours(1);
			for (Map.Entry<Integer, Map<Integer, Long>> ne : buffer.entrySet()) {
				Node node = nodeDAO.findById(ne.getKey()).orElse(null);
				if (node == null) continue;
				for (Map.Entry<Integer, Long> ue : ne.getValue().entrySet()) {
					if (ue.getValue() < MEGA) continue;
					User user = userDAO.findById(ue.getKey()).orElse(null);
					if (user == null) continue;
					node.setUsedData(node.getUsedData() + ue.getValue());
					Record record = new Record();
					record.setNode(node);
					record.setUser(user);
					record.setDate(now.toLocalDate());
					record.setHour(now.getHour());
					record.setData(ue.getValue());
					recordDAO.save(record);
				}
				nodeDAO.save(node);
			}
		}
	}
}
