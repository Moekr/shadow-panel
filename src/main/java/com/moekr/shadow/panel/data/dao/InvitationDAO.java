package com.moekr.shadow.panel.data.dao;

import com.moekr.shadow.panel.data.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationDAO extends JpaRepository<Invitation, Integer> {
	Invitation findByCode(String code);
}
