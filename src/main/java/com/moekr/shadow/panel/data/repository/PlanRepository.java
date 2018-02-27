package com.moekr.shadow.panel.data.repository;

import com.moekr.shadow.panel.data.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Integer> {
}
