package com.moekr.shadow.panel.logic.service.impl;

import com.moekr.shadow.panel.data.TransactionWrapper;
import com.moekr.shadow.panel.data.TransactionWrapper.SafeMethod;
import com.moekr.shadow.panel.data.dao.InvitationDAO;
import com.moekr.shadow.panel.data.dao.NodeDAO;
import com.moekr.shadow.panel.data.dao.UserDAO;
import com.moekr.shadow.panel.data.entity.Invitation;
import com.moekr.shadow.panel.data.entity.Node;
import com.moekr.shadow.panel.data.entity.User;
import com.moekr.shadow.panel.logic.service.UserService;
import com.moekr.shadow.panel.logic.vo.model.UserModel;
import com.moekr.shadow.panel.util.Asserts;
import com.moekr.shadow.panel.web.dto.form.ChangePasswordForm;
import com.moekr.shadow.panel.web.dto.form.ChangeRevokeAtFrom;
import com.moekr.shadow.panel.web.dto.form.RegisterForm;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@CommonsLog
@Service
public class UserServiceImpl implements UserService {
	private final UserDAO userDAO;
	private final NodeDAO nodeDAO;
	private final InvitationDAO invitationDAO;
	private final TransactionWrapper transactionWrapper;

	@Autowired
	public UserServiceImpl(UserDAO userDAO, NodeDAO nodeDAO, InvitationDAO invitationDAO, TransactionWrapper transactionWrapper) {
		this.userDAO = userDAO;
		this.nodeDAO = nodeDAO;
		this.invitationDAO = invitationDAO;
		this.transactionWrapper = transactionWrapper;
	}

	@PostConstruct
	private void initial() {
		if (userDAO.count() == 0) {
			transactionWrapper.wrap((SafeMethod) userDAO::initial);
		}
	}

	@Override
	public List<UserModel> findAll() {
		return userDAO.findAll().stream().map(UserModel::new).collect(Collectors.toList());
	}

	@Override
	public UserModel findByUsername(String username) {
		User user = userDAO.findByUsername(username);
		Asserts.isTrue(user != null, HttpStatus.NOT_FOUND.value());
		return new UserModel(user);
	}

	@Override
	public UserModel findByToken(String token) {
		User user = userDAO.findByToken(token);
		Asserts.isTrue(user != null, HttpStatus.NOT_FOUND.value());
		return new UserModel(user);
	}

	@Override
	public List<UserModel> available(int nodeId) {
		Node node = nodeDAO.findById(nodeId).orElse(null);
		Assert.notNull(node, "找不到节点");
		return userDAO.findAll().stream()
				.filter(u -> !u.getRevokeAt().isBefore(LocalDate.now()))
				.filter(u -> u.getPlan().getLevel() > node.getLevel())
				.map(UserModel::new)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void register(RegisterForm form) {
		Invitation invitation = invitationDAO.findByCode(form.getInvitation());
		Asserts.isTrue(invitation != null && !invitation.getUsed(), "邀请码无效或已被使用！");
		User user = userDAO.findByUsername(form.getUsername());
		Asserts.isTrue(user == null, "用户名已被使用！");
		user = new User();
		BeanUtils.copyProperties(form, user, "password");
		user.setPassword(DigestUtils.sha256Hex(form.getPassword()));
		user.setToken(RandomStringUtils.randomAlphanumeric(12));
		LocalDateTime now = LocalDateTime.now();
		user.setCreatedAt(now);
		user.setRevokeAt(now.toLocalDate());
		user.setPlan(invitation.getPlan());
		user = userDAO.save(user);
		invitation.setUsed(true);
		invitation.setUser(user);
		invitationDAO.save(invitation);
	}

	@Override
	@Transactional
	public void changePassword(String username, ChangePasswordForm form) {
		User user = userDAO.findByUsername(username);
		Assert.notNull(user, "找不到用户");
		Asserts.isTrue(StringUtils.equals(DigestUtils.sha256Hex(form.getOrigin()), user.getPassword()), "密码不正确！");
		user.setPassword(DigestUtils.sha256Hex(form.getPassword()));
		userDAO.save(user);
	}

	@Override
	@Transactional
	public void changeRevokeAt(ChangeRevokeAtFrom from) {
		User user = userDAO.findById(from.getId()).orElse(null);
		Assert.notNull(user, "找不到用户");
		LocalDate revokeAt = LocalDate.parse(from.getRevokeAt().replace('/', '-'));
		user.setRevokeAt(revokeAt);
		userDAO.save(user);
	}
}
