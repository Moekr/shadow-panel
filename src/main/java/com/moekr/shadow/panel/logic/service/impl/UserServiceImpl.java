package com.moekr.shadow.panel.logic.service.impl;

import com.moekr.shadow.panel.data.dao.UserDAO;
import com.moekr.shadow.panel.data.entity.User;
import com.moekr.shadow.panel.logic.service.UserService;
import com.moekr.shadow.panel.logic.vo.UserVO;
import com.moekr.shadow.panel.util.ToolKit;
import com.moekr.shadow.panel.web.dto.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
	private final UserDAO userDAO;
	private final NodeServiceImpl nodeService;

	@Autowired
	public UserServiceImpl(UserDAO userDAO, NodeServiceImpl nodeService) {
		this.userDAO = userDAO;
		this.nodeService = nodeService;
	}

	@Override
	@Transactional
	public UserVO create(UserDTO userDTO) {
		User user = new User();
		BeanUtils.copyProperties(userDTO, user);
		user.setCreatedAt(LocalDateTime.now());
		user = userDAO.save(user);
		nodeService.configure();
		return new UserVO(user);
	}

	@Override
	public List<UserVO> retrieve() {
		return userDAO.findAll().stream().map(UserVO::new).collect(Collectors.toList());
	}

	@Override
	public UserVO retrieve(int id) {
		User user = userDAO.findById(id);
		ToolKit.assertNotNull(user);
		return new UserVO(user);
	}

	@Override
	public UserVO retrieve(String username) {
		User user = userDAO.findByUsername(username);
		ToolKit.assertNotNull(user);
		return new UserVO(user);
	}

	@Override
	@Transactional
	public UserVO update(int id, UserDTO userDTO) {
		User user = userDAO.findById(id);
		ToolKit.assertNotNull(user);
		boolean configure = !userDTO.getPassword().equals(user.getPassword()) || !userDTO.getPort().equals(user.getPort());
		BeanUtils.copyProperties(userDTO, user);
		user = userDAO.save(user);
		if (configure) {
			nodeService.configure();
		}
		return new UserVO(user);
	}

	@Override
	@Transactional
	public void delete(int id) {
		User user = userDAO.findById(id);
		ToolKit.assertNotNull(user);
		userDAO.delete(user);
		nodeService.configure();
	}
}
