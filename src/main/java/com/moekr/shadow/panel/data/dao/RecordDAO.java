package com.moekr.shadow.panel.data.dao;

import com.moekr.shadow.panel.data.entity.Record;
import com.moekr.shadow.panel.logic.vo.data.DayData;
import com.moekr.shadow.panel.logic.vo.data.HourData;

import java.util.List;

public interface RecordDAO {
	Record save(Record record);

	List<HourData> hourData(int userId);

	List<DayData> dayData(int userId, int day);
}
