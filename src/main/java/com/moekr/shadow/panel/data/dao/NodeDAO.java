package com.moekr.shadow.panel.data.dao;

import com.moekr.shadow.panel.data.entity.Node;

import java.util.List;

public interface NodeDAO {
	Node save(Node node);

	List<Node> findAll();

	Node findById(Integer id);

	void delete(Node node);
}
