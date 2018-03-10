package com.moekr.shadow.panel.data.dao.impl;

import com.moekr.shadow.panel.data.dao.AbstractDAO;
import com.moekr.shadow.panel.data.dao.InvitationDAO;
import com.moekr.shadow.panel.data.entity.Invitation;
import com.moekr.shadow.panel.data.repository.InvitationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InvitationDAOImpl extends AbstractDAO<Invitation, Integer> implements InvitationDAO {
	private final InvitationRepository repository;

	@Autowired
	public InvitationDAOImpl(InvitationRepository repository) {
		super(repository);
		this.repository = repository;
	}

	@Override
	public Invitation findByCode(String code) {
		return repository.findByCode(code);
	}
}
