package com.moekr.shadow.panel.data.repository;

import com.moekr.shadow.panel.data.entity.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NodeRepository extends JpaRepository<Node, Integer> {
	@Query(value = "SELECT * FROM ENTITY__NODE AS n WHERE n.id IN (SELECT lpn.node_id FROM ENTITY__USER AS u, LINK__PLAN_NODE AS lpn WHERE u.id = ?1 AND u.plan_id = lpn.plan_id)", nativeQuery = true)
	List<Node> available(Integer userId);
}
