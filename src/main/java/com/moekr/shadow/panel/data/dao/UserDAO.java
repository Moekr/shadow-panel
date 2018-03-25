package com.moekr.shadow.panel.data.dao;

import com.moekr.shadow.panel.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserDAO extends JpaRepository<User, Integer> {
	User findByUsername(String username);

	User findByToken(String token);

	@Modifying
	@Query(value = "ALTER TABLE ENTITY__USER AUTO_INCREMENT=100001", nativeQuery = true)
	void initial();
}
