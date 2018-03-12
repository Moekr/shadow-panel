package com.moekr.shadow.panel.logic.service.impl;

import com.moekr.shadow.panel.data.dao.InvitationDAO;
import com.moekr.shadow.panel.data.entity.Invitation;
import com.moekr.shadow.panel.logic.service.InvitationService;
import com.moekr.shadow.panel.logic.vo.InvitationVO;
import com.moekr.shadow.panel.util.ToolKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvitationServiceImpl implements InvitationService {
	private final InvitationDAO invitationDAO;

	@Autowired
	public InvitationServiceImpl(InvitationDAO invitationDAO) {
		this.invitationDAO = invitationDAO;
	}

	@Override
	@Transactional
	public InvitationVO create() {
		Invitation invitation = new Invitation();
		invitation.setCode(ToolKit.randomUUID());
		invitation.setUsed(false);
		return new InvitationVO(invitationDAO.save(invitation));
	}

	@Override
	public List<InvitationVO> retrieve() {
		return invitationDAO.findAll().stream().map(InvitationVO::new).collect(Collectors.toList());
	}

	@Override
	public InvitationVO retrieve(int id) {
		Invitation invitation = invitationDAO.findById(id);
		ToolKit.assertNotNull(invitation);
		return new InvitationVO(invitation);
	}

	@Override
	@Transactional
	public void delete(int id) {
		Invitation invitation = invitationDAO.findById(id);
		ToolKit.assertNotNull(invitation);
		invitationDAO.delete(invitation);
	}
}
