package com.moekr.shadow.panel.data.dao;

import com.moekr.shadow.panel.data.entity.Invitation;

public interface InvitationDAO {
	Invitation save(Invitation invitation);

	Invitation findByCode(String code);
}
