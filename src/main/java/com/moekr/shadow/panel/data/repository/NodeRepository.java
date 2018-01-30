package com.moekr.shadow.panel.data.repository;

import com.moekr.shadow.panel.data.entity.Node;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NodeRepository extends JpaRepository<Node, Integer> {
}
