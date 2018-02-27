package com.moekr.shadow.panel.data.dao;

import com.moekr.shadow.panel.data.entity.Plan;

import java.util.List;

public interface PlanDAO {
	Plan save(Plan plan);

	Plan findById(Integer id);

	List<Plan> findAll();
}
