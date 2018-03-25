package com.moekr.shadow.panel.logic.service.impl;

import com.moekr.shadow.panel.data.dao.InvitationDAO;
import com.moekr.shadow.panel.data.dao.PlanDAO;
import com.moekr.shadow.panel.data.entity.Invitation;
import com.moekr.shadow.panel.data.entity.Plan;
import com.moekr.shadow.panel.logic.service.InvitationService;
import com.moekr.shadow.panel.logic.vo.model.InvitationModel;
import com.moekr.shadow.panel.util.Asserts;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvitationServiceImpl implements InvitationService {
	private final PlanDAO planDAO;
	private final InvitationDAO invitationDAO;

	@Autowired
	public InvitationServiceImpl(PlanDAO planDAO, InvitationDAO invitationDAO) {
		this.planDAO = planDAO;
		this.invitationDAO = invitationDAO;
	}

	@Override
	public List<InvitationModel> findAll() {
		return invitationDAO.findAll().stream().map(InvitationModel::new).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void create(int planId) {
		Plan plan = planDAO.findById(planId).orElse(null);
		Asserts.isTrue(plan != null, "套餐不存在！");
		Invitation invitation = new Invitation();
		invitation.setCode(RandomStringUtils.randomAlphanumeric(24));
		invitation.setUsed(false);
		invitation.setPlan(plan);
		invitationDAO.save(invitation);
	}
}
