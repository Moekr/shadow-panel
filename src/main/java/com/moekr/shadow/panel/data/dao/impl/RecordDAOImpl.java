package com.moekr.shadow.panel.data.dao.impl;

import com.moekr.shadow.panel.data.dao.AbstractDAO;
import com.moekr.shadow.panel.data.dao.RecordDAO;
import com.moekr.shadow.panel.data.entity.Record;
import com.moekr.shadow.panel.data.repository.RecordRepository;
import com.moekr.shadow.panel.logic.vo.data.DayData;
import com.moekr.shadow.panel.logic.vo.data.HourData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RecordDAOImpl extends AbstractDAO<Record, Integer> implements RecordDAO {
	private static final Converter<Date, LocalDate> DATE_TO_LOCAL_DATE_CONVERTER = Jsr310Converters.DateToLocalDateConverter.INSTANCE;

	private final RecordRepository repository;

	@Autowired
	public RecordDAOImpl(RecordRepository repository) {
		super(repository);
		this.repository = repository;
	}

	@Override
	public List<HourData> hourData(int userId) {
		return repository.hourData(userId).stream()
				.map(row -> new HourData((Integer) row.get("hour"), ((BigInteger) row.get("data")).longValue()))
				.collect(Collectors.toList());
	}

	@Override
	public List<DayData> dayData(int userId, int day) {
		return repository.dayData(userId, day).stream()
				.map(row -> new DayData(DATE_TO_LOCAL_DATE_CONVERTER.convert((Date) row.get("date")), ((BigDecimal) row.get("data")).longValue()))
				.collect(Collectors.toList());
	}
}
