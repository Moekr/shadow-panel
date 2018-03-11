package com.moekr.shadow.panel.data.repository;

import com.moekr.shadow.panel.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUsername(String username);

	User findByPort(Integer port);

	User findByToken(String token);
}
