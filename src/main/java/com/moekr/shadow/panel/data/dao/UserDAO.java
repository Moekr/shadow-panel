package com.moekr.shadow.panel.data.dao;

import com.moekr.shadow.panel.data.entity.User;

import java.util.List;

public interface UserDAO {
	User save(User user);

	List<User> save(Iterable<User> iterable);

	List<User> findAll();

	User findById(Integer id);

	User findByUsername(String username);

	User findByPort(Integer port);

	void delete(User user);
}
