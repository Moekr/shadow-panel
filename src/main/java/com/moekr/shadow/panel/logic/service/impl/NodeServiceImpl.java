package com.moekr.shadow.panel.logic.service.impl;

import com.moekr.shadow.panel.data.dao.NodeDAO;
import com.moekr.shadow.panel.data.dao.PropertyDAO;
import com.moekr.shadow.panel.data.dao.UserDAO;
import com.moekr.shadow.panel.data.entity.Node;
import com.moekr.shadow.panel.data.entity.User;
import com.moekr.shadow.panel.logic.rpc.Instance;
import com.moekr.shadow.panel.logic.rpc.NodeManager;
import com.moekr.shadow.panel.logic.rpc.vo.Configuration;
import com.moekr.shadow.panel.logic.service.NodeService;
import com.moekr.shadow.panel.logic.vo.NodeVO;
import com.moekr.shadow.panel.util.ToolKit;
import com.moekr.shadow.panel.util.enums.DefaultProperty;
import com.moekr.shadow.panel.web.dto.NodeDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NodeServiceImpl implements NodeService {
	private final UserDAO userDAO;
	private final NodeDAO nodeDAO;
	private final PropertyDAO propertyDAO;
	private final NodeManager nodeManager;

	@Autowired
	public NodeServiceImpl(UserDAO userDAO, NodeDAO nodeDAO, PropertyDAO propertyDAO, NodeManager nodeManager) {
		this.userDAO = userDAO;
		this.nodeDAO = nodeDAO;
		this.propertyDAO = propertyDAO;
		this.nodeManager = nodeManager;
	}

	@PostConstruct
	private void initial() {
		configure();
		nodeDAO.findAll().forEach(node -> nodeManager.addInstance(node.getId(), node.getAddress()));
	}

	void configure() {
		Configuration configuration = new Configuration();
		configuration.setMethod(propertyDAO.findByName(DefaultProperty.METHOD.getName()).getContent());
		configuration.setProtocol(propertyDAO.findByName(DefaultProperty.PROTOCOL.getName()).getContent());
		configuration.setProtocolParam(propertyDAO.findByName(DefaultProperty.PROTOCOL_PARAM.getName()).getContent());
		configuration.setObfs(propertyDAO.findByName(DefaultProperty.OBFS.getName()).getContent());
		configuration.setObfsParam(propertyDAO.findByName(DefaultProperty.OBFS_PARAM.getName()).getContent());
		Map<Integer, String> portPassword = new HashMap<>();
		for (User user : userDAO.findAll()) {
			portPassword.put(user.getPort(), user.getPassword());
		}
		configuration.setPortPassword(portPassword);
		nodeManager.configure(configuration);
	}

	@Override
	public NodeVO create(NodeDTO nodeDTO) {
		Node node = new Node();
		BeanUtils.copyProperties(nodeDTO, node);
		node.setCreatedAt(LocalDateTime.now());
		node = nodeDAO.save(node);
		nodeManager.addInstance(node.getId(), node.getAddress());
		return new NodeVO(node);
	}

	@Override
	public List<NodeVO> retrieve() {
		return nodeDAO.findAll().stream()
				.map(node -> new NodeVO(node, nodeManager.findInstance(node.getId()).getStatus()))
				.collect(Collectors.toList());
	}

	@Override
	public NodeVO retrieve(int id) {
		Node node = nodeDAO.findById(id);
		ToolKit.assertNotNull(node);
		return new NodeVO(node, nodeManager.findInstance(node.getId()).getStatus());
	}

	@Override
	public NodeVO update(int id, NodeDTO nodeDTO) {
		Node node = nodeDAO.findById(id);
		ToolKit.assertNotNull(node);
		if (!StringUtils.equals(nodeDTO.getAddress(), node.getAddress())) {
			nodeManager.removeInstance(id);
			nodeManager.addInstance(id, nodeDTO.getAddress());
		}
		BeanUtils.copyProperties(nodeDTO, node);
		return new NodeVO(nodeDAO.save(node), nodeManager.findInstance(id).getStatus());
	}

	@Override
	public void delete(int id) {
		Node node = nodeDAO.findById(id);
		ToolKit.assertNotNull(node);
		nodeDAO.delete(node);
	}

	@Override
	public void start(int id) {
		Instance instance = nodeManager.findInstance(id);
		ToolKit.assertNotNull(instance);
		instance.start();
	}

	@Override
	public void stop(int id) {
		Instance instance = nodeManager.findInstance(id);
		ToolKit.assertNotNull(instance);
		instance.stop();
	}

	@Override
	public void restart(int id) {
		Instance instance = nodeManager.findInstance(id);
		ToolKit.assertNotNull(instance);
		instance.restart();
	}
}
