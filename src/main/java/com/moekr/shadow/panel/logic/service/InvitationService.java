package com.moekr.shadow.panel.logic.service;

import com.moekr.shadow.panel.logic.vo.InvitationVO;

import java.util.List;

public interface InvitationService {
	InvitationVO create();

	List<InvitationVO> retrieve();

	InvitationVO retrieve(int id);

	void delete(int id);
}
