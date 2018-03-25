package com.moekr.shadow.panel.data.dao;

import com.moekr.shadow.panel.data.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanDAO extends JpaRepository<Plan, Integer> {
}
