package com.moekr.shadow.panel.logic.service;

import com.moekr.shadow.panel.logic.vo.model.InvitationModel;

import java.util.List;

public interface InvitationService {
	List<InvitationModel> findAll();

	void create(int planId);
}
