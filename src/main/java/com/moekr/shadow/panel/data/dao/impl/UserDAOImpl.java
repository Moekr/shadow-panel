package com.moekr.shadow.panel.data.dao.impl;

import com.moekr.shadow.panel.data.dao.AbstractDAO;
import com.moekr.shadow.panel.data.dao.UserDAO;
import com.moekr.shadow.panel.data.entity.User;
import com.moekr.shadow.panel.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserDAOImpl extends AbstractDAO<User, Integer> implements UserDAO {
	private final UserRepository repository;

	@Autowired
	public UserDAOImpl(UserRepository repository) {
		super(repository);
		this.repository = repository;
	}

	@Override
	public List<User> findAll() {
		return super.findAll().stream().filter(user -> user.getRevokedAt() == null).collect(Collectors.toList());
	}

	@Override
	public User findById(Integer entityId) {
		User user = super.findById(entityId);
		return user != null && user.getRevokedAt() == null ? user : null;
	}

	@Override
	public User findByUsername(String username) {
		User user = repository.findByUsername(username);
		return user != null && user.getRevokedAt() == null ? user : null;
	}

	@Override
	public User findByPort(Integer port) {
		User user = repository.findByPort(port);
		return user != null && user.getRevokedAt() == null ? user : null;
	}

	@Override
	public void delete(User user) {
		user.setPort(null);
		user.setRevokedAt(LocalDateTime.now());
		repository.save(user);
	}
}
