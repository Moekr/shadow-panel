package com.moekr.shadow.panel.logic.service;

import com.moekr.shadow.panel.logic.vo.model.PlanModel;
import com.moekr.shadow.panel.web.dto.form.CreatePlanForm;

import java.util.List;

public interface PlanService {
	List<PlanModel> findAll();

	PlanModel findById(int id);

	void create(CreatePlanForm form);
}
