package com.moekr.shadow.panel.data.dao;

import com.moekr.shadow.panel.data.entity.Node;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NodeDAO extends JpaRepository<Node, Integer> {
	List<Node> findAllByRevokedAtIsNull();

	List<Node> findAllByRevokedAtIsNullAndLevelLessThan(int level);
}
