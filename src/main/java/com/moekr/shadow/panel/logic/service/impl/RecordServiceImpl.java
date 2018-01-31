package com.moekr.shadow.panel.logic.service.impl;

import com.moekr.shadow.panel.data.dao.RecordDAO;
import com.moekr.shadow.panel.logic.service.RecordService;
import com.moekr.shadow.panel.logic.vo.data.DayData;
import com.moekr.shadow.panel.logic.vo.data.HourData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RecordServiceImpl implements RecordService {
	private static final long MEGA = 1024 * 1024;

	private final RecordDAO recordDAO;

	@Autowired
	public RecordServiceImpl(RecordDAO recordDAO) {
		this.recordDAO = recordDAO;
	}

	@Override
	public String hourData(int userId) {
		List<HourData> dataList = recordDAO.hourData(userId);
		Map<Integer, Long> dataMap = dataList.stream().collect(Collectors.toMap(HourData::getHour, HourData::getData, (a, b) -> a + b));
		JSONArray dataArray = new JSONArray();
		for (int hour = 0; hour < 24; hour++) {
			JSONObject data = new JSONObject();
			try {
				data.put("x", String.valueOf(hour));
				data.put("y", dataMap.getOrDefault(hour, 0L) / MEGA);
			} catch (JSONException ignored) {
			}
			dataArray.put(data);
		}
		return dataArray.toString();
	}

	@Override
	public String dayData(int userId, int day) {
		List<DayData> dataList = recordDAO.dayData(userId, day);
		Map<LocalDate, Long> dataMap = dataList.stream().collect(Collectors.toMap(DayData::getDate, DayData::getData, (a, b) -> a + b));
		JSONArray dataArray = new JSONArray();
		LocalDate end = LocalDate.now().plusDays(1);
		LocalDate date = end.minusDays(day);
		while (!date.equals(end)) {
			JSONObject data = new JSONObject();
			try {
				data.put("x", date.getMonthValue() + "月" + date.getDayOfMonth() + "日");
				data.put("y", dataMap.getOrDefault(date, 0L) / MEGA);
			} catch (JSONException ignored) {
			}
			dataArray.put(data);
			date = date.plusDays(1);
		}
		return dataArray.toString();
	}
}
