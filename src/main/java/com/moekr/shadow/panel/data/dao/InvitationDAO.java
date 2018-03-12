package com.moekr.shadow.panel.data.dao;

import com.moekr.shadow.panel.data.entity.Invitation;

import java.util.List;

public interface InvitationDAO {
	Invitation save(Invitation invitation);

	List<Invitation> findAll();

	Invitation findById(Integer id);

	Invitation findByCode(String code);

	void delete(Invitation invitation);
}
