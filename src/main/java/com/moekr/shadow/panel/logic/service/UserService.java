package com.moekr.shadow.panel.logic.service;

import com.moekr.shadow.panel.logic.vo.model.UserModel;
import com.moekr.shadow.panel.web.dto.form.ChangePasswordForm;
import com.moekr.shadow.panel.web.dto.form.ChangeRevokeAtFrom;
import com.moekr.shadow.panel.web.dto.form.RegisterForm;

import java.util.List;

public interface UserService {
	List<UserModel> findAll();

	UserModel findByUsername(String username);

	UserModel findByToken(String token);

	List<UserModel> available(int nodeId);

	void register(RegisterForm form);

	void changePassword(String username, ChangePasswordForm form);

	void changeRevokeAt(ChangeRevokeAtFrom from);
}
