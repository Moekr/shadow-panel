package com.moekr.shadow.panel.logic.service.impl;

import com.moekr.shadow.panel.data.dao.NodeDAO;
import com.moekr.shadow.panel.data.dao.PlanDAO;
import com.moekr.shadow.panel.data.dao.UserDAO;
import com.moekr.shadow.panel.data.entity.Plan;
import com.moekr.shadow.panel.logic.service.PlanService;
import com.moekr.shadow.panel.logic.vo.model.PlanModel;
import com.moekr.shadow.panel.util.Asserts;
import com.moekr.shadow.panel.web.dto.form.CreatePlanForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanServiceImpl implements PlanService {
	private final UserDAO userDAO;
	private final NodeDAO nodeDAO;
	private final PlanDAO planDAO;

	@Autowired
	public PlanServiceImpl(UserDAO userDAO, NodeDAO nodeDAO, PlanDAO planDAO) {
		this.userDAO = userDAO;
		this.nodeDAO = nodeDAO;
		this.planDAO = planDAO;
	}

	@Override
	public List<PlanModel> findAll() {
		return planDAO.findAll().stream().map(PlanModel::new).collect(Collectors.toList());
	}

	@Override
	public PlanModel findById(int id) {
		Plan plan = planDAO.findById(id).orElse(null);
		Asserts.isTrue(plan != null, "套餐不存在！");
		return new PlanModel(plan);
	}

	@Override
	@Transactional
	public void create(CreatePlanForm form) {
		Plan plan = new Plan();
		plan.setName(form.getName());
		plan.setDescription(form.getDescription());
		plan.setLevel(form.getLevel());
		planDAO.save(plan);
	}
}
