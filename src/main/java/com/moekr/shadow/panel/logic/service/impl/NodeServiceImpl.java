package com.moekr.shadow.panel.logic.service.impl;

import com.moekr.shadow.panel.data.dao.NodeDAO;
import com.moekr.shadow.panel.data.dao.PortDAO;
import com.moekr.shadow.panel.data.entity.Node;
import com.moekr.shadow.panel.data.entity.Port;
import com.moekr.shadow.panel.logic.TransactionWrapper;
import com.moekr.shadow.panel.logic.rpc.NodeManager;
import com.moekr.shadow.panel.logic.service.NodeService;
import com.moekr.shadow.panel.logic.vo.NodeVO;
import com.moekr.shadow.panel.logic.vo.PortVO;
import com.moekr.shadow.panel.util.ToolKit;
import com.moekr.shadow.panel.web.dto.NodeDTO;
import com.moekr.shadow.panel.web.dto.PortDTO;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CommonsLog
@Service
public class NodeServiceImpl implements NodeService {
	private final NodeDAO nodeDAO;
	private final PortDAO portDAO;
	private final NodeManager nodeManager;
	private final TransactionWrapper transactionWrapper;

	@Autowired
	public NodeServiceImpl(NodeDAO nodeDAO, PortDAO portDAO, NodeManager nodeManager, TransactionWrapper transactionWrapper) {
		this.nodeDAO = nodeDAO;
		this.portDAO = portDAO;
		this.nodeManager = nodeManager;
		this.transactionWrapper = transactionWrapper;
	}

	@PostConstruct
	private void initial() {
		try {
			transactionWrapper.wrap(() -> nodeDAO.findAll().forEach(node -> nodeManager.setPort(node.getId(), node.getPortSet())));
			nodeManager.updateAvailableUser();
		} catch (Exception e) {
			log.error("Failed to initial port list to node manager [" + e.getClass().getName() + "]: " + e.getMessage());
		}
	}

	@Override
	@Transactional
	public NodeVO create(NodeDTO nodeDTO) {
		Node node = new Node();
		BeanUtils.copyProperties(nodeDTO, node);
		node.setCreatedAt(LocalDateTime.now());
		node = nodeDAO.save(node);
		nodeManager.setPort(node.getId(), Collections.emptySet());
		return new NodeVO(node);
	}

	@Override
	public List<NodeVO> retrieve() {
		return nodeDAO.findAll().stream()
				.map(node -> new NodeVO(node, nodeManager.status(node.getId())))
				.collect(Collectors.toList());
	}

	@Override
	public NodeVO retrieve(int id) {
		Node node = nodeDAO.findById(id);
		ToolKit.assertNotNull(node);
		return new NodeVO(node, nodeManager.status(node.getId()));
	}

	@Override
	@Transactional
	public NodeVO update(int id, NodeDTO nodeDTO) {
		Node node = nodeDAO.findById(id);
		ToolKit.assertNotNull(node);
		BeanUtils.copyProperties(nodeDTO, node);
		return new NodeVO(nodeDAO.save(node), nodeManager.status(id));
	}

	@Override
	@Transactional
	public void delete(int id) {
		Node node = nodeDAO.findById(id);
		ToolKit.assertNotNull(node);
		nodeDAO.delete(node);
		nodeManager.setPort(id, null);
	}

	@Override
	@Transactional
	public NodeVO createPort(int nid, PortDTO portDTO) {
		Node node = nodeDAO.findById(nid);
		ToolKit.assertNotNull(node);
		Set<Port> portSet = new HashSet<>(node.getPortSet());
		Port port = new Port();
		BeanUtils.copyProperties(portDTO, port);
		port.setNode(node);
		portSet.add(portDAO.save(port));
		nodeManager.setPort(nid, portSet);
		NodeVO nodeVO = new NodeVO(node, nodeManager.status(nid));
		nodeVO.setPortSet(portSet.stream().map(PortVO::new).collect(Collectors.toSet()));
		return nodeVO;
	}

	@Override
	@Transactional
	public NodeVO updatePort(int nid, int pid, PortDTO portDTO) {
		Node node = nodeDAO.findById(nid);
		ToolKit.assertNotNull(node);
		Port port = node.getPortSet().stream().filter(p -> p.getId() == pid).findFirst().orElse(null);
		ToolKit.assertNotNull(port);
		BeanUtils.copyProperties(portDTO, port);
		portDAO.save(port);
		nodeManager.setPort(nid, node.getPortSet());
		return new NodeVO(node, nodeManager.status(nid));
	}

	@Override
	public NodeVO deletePort(int nid, int pid) {
		Node node = nodeDAO.findById(nid);
		ToolKit.assertNotNull(node);
		Port port = node.getPortSet().stream().filter(p -> p.getId() == pid).findFirst().orElse(null);
		ToolKit.assertNotNull(port);
		Set<Port> portSet = new HashSet<>(node.getPortSet());
		portDAO.delete(port);
		portSet.removeIf(p -> p.getId() == pid);
		nodeManager.setPort(nid, portSet);
		NodeVO nodeVO = new NodeVO(node, nodeManager.status(nid));
		nodeVO.setPortSet(portSet.stream().map(PortVO::new).collect(Collectors.toSet()));
		return nodeVO;
	}

	@Override
	public void start(int id) {
		nodeManager.start(id);
	}

	@Override
	public void stop(int id) {
		nodeManager.stop(id);
	}

	@Override
	public void restart(int id) {
		nodeManager.restart(id);
	}

	@Override
	public List<NodeVO> available(int userId) {
		return nodeDAO.available(userId).stream()
				.filter(node -> !node.getPortSet().isEmpty())
				.map(node -> new NodeVO(node, nodeManager.status(node.getId())))
				.collect(Collectors.toList());
	}
}
