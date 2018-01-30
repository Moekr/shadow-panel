package com.moekr.shadow.panel.data.repository;

import com.moekr.shadow.panel.data.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface RecordRepository extends JpaRepository<Record, Integer> {
	@Query(value = "SELECT r.hour AS hour, r.data AS data FROM ENTITY__RECORD AS r WHERE r.date = CURRENT_DATE() AND r.user_id = ?1 ORDER BY hour ASC", nativeQuery = true)
	List<Map<String, Object>> hourData(int userId);

	@Query(value = "SELECT r.date AS date, SUM(r.data) AS data FROM ENTITY__RECORD AS r WHERE DATEDIFF(r.date, CURRENT_DATE()) < ?2 AND r.user_id = ?1 GROUP BY r.date ORDER BY date ASC", nativeQuery = true)
	List<Map<String, Object>> dayData(int userId, int day);
}
