package com.moekr.shadow.panel.logic.service.impl;

import com.moekr.shadow.panel.data.dao.NodeDAO;
import com.moekr.shadow.panel.data.dao.UserDAO;
import com.moekr.shadow.panel.data.entity.Node;
import com.moekr.shadow.panel.data.entity.User;
import com.moekr.shadow.panel.logic.service.NodeService;
import com.moekr.shadow.panel.logic.vo.model.NodeModel;
import com.moekr.shadow.panel.util.Asserts;
import com.moekr.shadow.panel.util.ServiceException;
import com.moekr.shadow.panel.web.dto.form.CreateNodeForm;
import com.moekr.shadow.panel.web.dto.form.NodeActionForm;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@CommonsLog
@Service
public class NodeServiceImpl implements NodeService {
	private final NodeDAO nodeDAO;
	private final UserDAO userDAO;

	@Autowired
	public NodeServiceImpl(NodeDAO nodeDAO, UserDAO userDAO) {
		this.nodeDAO = nodeDAO;
		this.userDAO = userDAO;
	}

	@Override
	public List<NodeModel> findAll() {
		return nodeDAO.findAllByRevokedAtIsNull().stream().map(NodeModel::new).collect(Collectors.toList());
	}

	@Override
	public NodeModel findById(int id) {
		Node node = nodeDAO.findById(id).orElse(null);
		Asserts.isTrue(node != null, "找不到目标节点！");
		return new NodeModel(node);
	}

	@Override
	@Transactional
	public void create(CreateNodeForm form) {
		Node node = new Node();
		BeanUtils.copyProperties(form, node);
		node.setEnable(false);
		node.setCreatedAt(LocalDateTime.now());
		nodeDAO.save(node);
	}

	@Override
	@Transactional
	public void action(NodeActionForm form) {
		Node node = nodeDAO.findById(form.getId()).orElse(null);
		Asserts.isTrue(node != null, "找不到目标节点！");
		switch (form.getAction()) {
			case "enable":
				if (!node.getEnable()) {
					node.setEnable(true);
				}
				break;
			case "disable":
				if (node.getEnable()) {
					node.setEnable(false);
				}
				break;
			case "remove":
				node.setRevokedAt(LocalDateTime.now());
				break;
			default:
				throw new ServiceException("不能识别的目标操作！");
		}
		nodeDAO.save(node);
	}

	@Override
	public List<NodeModel> available(int userId) {
		User user = userDAO.findById(userId).orElse(null);
		Assert.notNull(user, "找不到用户");
		if (user.getPlan() == null || user.getRevokeAt().isBefore(LocalDate.now())) {
			return Collections.emptyList();
		} else {
			return nodeDAO.findAllByRevokedAtIsNullAndLevelLessThan(user.getPlan().getLevel()).stream().map(NodeModel::new).collect(Collectors.toList());
		}
	}
}
