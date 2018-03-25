package com.moekr.shadow.panel.data.dao;

import com.moekr.shadow.panel.data.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface RecordDAO extends JpaRepository<Record, Integer> {
	@Query(value = "SELECT r.hour AS hour, SUM(r.data) AS data FROM ENTITY__RECORD AS r WHERE DATEDIFF(CURRENT_DATE(), r.date) < ?2 AND r.user_id = ?1 GROUP BY r.hour ORDER BY hour ASC", nativeQuery = true)
	List<Map<String, Object>> hourData(int userId, int days);

	@Query(value = "SELECT r.date AS date, SUM(r.data) AS data FROM ENTITY__RECORD AS r WHERE DATEDIFF(CURRENT_DATE(), r.date) < ?2 AND r.user_id = ?1 GROUP BY r.date ORDER BY date ASC", nativeQuery = true)
	List<Map<String, Object>> dayData(int userId, int days);

	@Query(value = "SELECT r.hour AS hour, SUM(r.data) AS data FROM ENTITY__RECORD AS r WHERE DATEDIFF(CURRENT_DATE(), r.date) < ?1 GROUP BY r.hour ORDER BY hour ASC", nativeQuery = true)
	List<Map<String, Object>> hourData(int days);

	@Query(value = "SELECT r.date AS date, SUM(r.data) AS data FROM ENTITY__RECORD AS r WHERE DATEDIFF(CURRENT_DATE(), r.date) < ?1 GROUP BY r.date ORDER BY date ASC", nativeQuery = true)
	List<Map<String, Object>> dayData(int days);
}
