package com.moekr.shadow.panel.logic.service;

import com.moekr.shadow.panel.logic.vo.PlanVO;
import com.moekr.shadow.panel.web.dto.ChangePlanRequest;
import com.moekr.shadow.panel.web.dto.PlanDTO;

import java.util.List;

public interface PlanService {
	PlanVO create(PlanDTO planDTO);

	PlanVO retrieve(int id);

	List<PlanVO> retrieve();

	PlanVO update(int id, PlanDTO planDTO);

	void changePlan(ChangePlanRequest request);
}
