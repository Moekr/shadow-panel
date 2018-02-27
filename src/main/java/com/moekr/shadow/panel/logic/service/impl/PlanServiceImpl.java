package com.moekr.shadow.panel.logic.service.impl;

import com.moekr.shadow.panel.data.dao.NodeDAO;
import com.moekr.shadow.panel.data.dao.PlanDAO;
import com.moekr.shadow.panel.data.dao.UserDAO;
import com.moekr.shadow.panel.data.entity.Plan;
import com.moekr.shadow.panel.data.entity.User;
import com.moekr.shadow.panel.logic.rpc.NodeManager;
import com.moekr.shadow.panel.logic.service.PlanService;
import com.moekr.shadow.panel.logic.vo.PlanVO;
import com.moekr.shadow.panel.util.ToolKit;
import com.moekr.shadow.panel.web.dto.ChangePlanRequest;
import com.moekr.shadow.panel.web.dto.PlanDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanServiceImpl implements PlanService {
	private final UserDAO userDAO;
	private final NodeDAO nodeDAO;
	private final PlanDAO planDAO;
	private final NodeManager nodeManager;

	@Autowired
	public PlanServiceImpl(UserDAO userDAO, NodeDAO nodeDAO, PlanDAO planDAO, NodeManager nodeManager) {
		this.userDAO = userDAO;
		this.nodeDAO = nodeDAO;
		this.planDAO = planDAO;
		this.nodeManager = nodeManager;
	}

	@Override
	@Transactional
	public PlanVO create(PlanDTO planDTO) {
		Plan plan = new Plan();
		BeanUtils.copyProperties(planDTO, plan, "price");
		plan.setPrice(planDTO.getPrice().doubleValue());
		plan.getNodeSet().addAll(nodeDAO.findAllById(planDTO.getNodeIdSet()));
		return new PlanVO(planDAO.save(plan));
	}

	@Override
	public PlanVO retrieve(int id) {
		Plan plan = planDAO.findById(id);
		ToolKit.assertNotNull(plan);
		return new PlanVO(plan);
	}

	@Override
	public List<PlanVO> retrieve() {
		return planDAO.findAll().stream().map(PlanVO::new).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public PlanVO update(int id, PlanDTO planDTO) {
		Plan plan = planDAO.findById(id);
		ToolKit.assertNotNull(plan);
		BeanUtils.copyProperties(planDTO, plan, "price");
		plan.setPrice(planDTO.getPrice().doubleValue());
		plan.setNodeSet(new HashSet<>(nodeDAO.findAllById(planDTO.getNodeIdSet())));
		plan = planDAO.save(plan);
		nodeManager.updateAvailableUser();
		return new PlanVO(plan);
	}

	@Override
	@Transactional
	public void changePlan(ChangePlanRequest request) {
		User user = userDAO.findById(request.getUserId());
		ToolKit.assertNotNull(user);
		Plan plan = planDAO.findById(request.getPlanId());
		ToolKit.assertNotNull(plan);
		user.setPlan(plan);
		userDAO.save(user);
		nodeManager.updateAvailableUser();
	}
}
