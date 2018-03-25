package com.moekr.shadow.panel.logic.service;

public interface RecordService {
	void record(int nodeId, String statistics);

	String hourData(int userId, int days);

	String dayData(int userId, int days);

	String hourData(int days);

	String dayData(int days);
}
