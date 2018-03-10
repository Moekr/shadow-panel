package com.moekr.shadow.panel.data.repository;

import com.moekr.shadow.panel.data.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, Integer> {
	Invitation findByCode(String code);
}
