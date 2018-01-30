package com.moekr.shadow.panel.data.dao.impl;

import com.moekr.shadow.panel.data.dao.AbstractDAO;
import com.moekr.shadow.panel.data.dao.ClientDAO;
import com.moekr.shadow.panel.data.entity.Client;
import com.moekr.shadow.panel.data.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ClientDAOImpl extends AbstractDAO<Client, Integer> implements ClientDAO {

	@Autowired
	public ClientDAOImpl(ClientRepository repository) {
		super(repository);
	}
}
