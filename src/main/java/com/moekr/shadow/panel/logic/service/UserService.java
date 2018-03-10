package com.moekr.shadow.panel.logic.service;

import com.moekr.shadow.panel.logic.vo.UserVO;
import com.moekr.shadow.panel.web.dto.UserDTO;
import com.moekr.shadow.panel.web.dto.form.RegisterForm;

import java.util.List;

public interface UserService {
	UserVO create(UserDTO userDTO);

	List<UserVO> retrieve();

	UserVO retrieve(int id);

	UserVO retrieve(String username);

	UserVO update(int id, UserDTO userDTO);

	void delete(int id);

	void register(RegisterForm form);
}
