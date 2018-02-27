package com.moekr.shadow.panel.data.dao.impl;

import com.moekr.shadow.panel.data.dao.AbstractDAO;
import com.moekr.shadow.panel.data.dao.PlanDAO;
import com.moekr.shadow.panel.data.entity.Plan;
import com.moekr.shadow.panel.data.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PlanDAOImpl extends AbstractDAO<Plan, Integer> implements PlanDAO {

	@Autowired
	public PlanDAOImpl(PlanRepository repository) {
		super(repository);
	}
}
