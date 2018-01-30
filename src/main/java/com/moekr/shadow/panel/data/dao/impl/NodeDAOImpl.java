package com.moekr.shadow.panel.data.dao.impl;

import com.moekr.shadow.panel.data.dao.AbstractDAO;
import com.moekr.shadow.panel.data.dao.NodeDAO;
import com.moekr.shadow.panel.data.entity.Node;
import com.moekr.shadow.panel.data.repository.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class NodeDAOImpl extends AbstractDAO<Node, Integer> implements NodeDAO {
	@Autowired
	public NodeDAOImpl(NodeRepository repository) {
		super(repository);
	}

	@Override
	public List<Node> findAll() {
		return super.findAll().stream().filter(node -> node.getRevokedAt() == null).collect(Collectors.toList());
	}

	@Override
	public Node findById(Integer id) {
		Node node = super.findById(id);
		return node != null && node.getRevokedAt() == null ? node : null;
	}

	@Override
	public void delete(Node node) {
		node.setRevokedAt(LocalDateTime.now());
		save(node);
	}
}
