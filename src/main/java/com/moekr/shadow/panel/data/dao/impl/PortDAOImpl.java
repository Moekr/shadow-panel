package com.moekr.shadow.panel.data.dao.impl;

import com.moekr.shadow.panel.data.dao.AbstractDAO;
import com.moekr.shadow.panel.data.dao.PortDAO;
import com.moekr.shadow.panel.data.entity.Port;
import com.moekr.shadow.panel.data.repository.PortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PortDAOImpl extends AbstractDAO<Port, Integer> implements PortDAO {
	@Autowired
	public PortDAOImpl(PortRepository repository) {
		super(repository);
	}
}
