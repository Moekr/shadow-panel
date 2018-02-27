package com.moekr.shadow.panel.logic.service.impl;

import com.moekr.shadow.panel.data.dao.UserDAO;
import com.moekr.shadow.panel.data.entity.Plan;
import com.moekr.shadow.panel.data.entity.User;
import com.moekr.shadow.panel.logic.TransactionWrapper;
import com.moekr.shadow.panel.logic.rpc.NodeManager;
import com.moekr.shadow.panel.logic.service.UserService;
import com.moekr.shadow.panel.logic.vo.UserVO;
import com.moekr.shadow.panel.util.ToolKit;
import com.moekr.shadow.panel.web.dto.UserDTO;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@CommonsLog
@Service
public class UserServiceImpl implements UserService {
	private final UserDAO userDAO;
	private final NodeManager nodeManager;
	private final TransactionWrapper transactionWrapper;

	@Autowired
	public UserServiceImpl(UserDAO userDAO, NodeManager nodeManager, TransactionWrapper transactionWrapper) {
		this.userDAO = userDAO;
		this.nodeManager = nodeManager;
		this.transactionWrapper = transactionWrapper;
	}

	@Override
	@Transactional
	public UserVO create(UserDTO userDTO) {
		User user = new User();
		BeanUtils.copyProperties(userDTO, user);
		user.setBalance(0.0);
		user.setCreatedAt(LocalDateTime.now());
		return new UserVO(userDAO.save(user));
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
			nodeManager.updateAvailableUser();
		}
		return new UserVO(user);
	}

	@Override
	@Transactional
	public void delete(int id) {
		User user = userDAO.findById(id);
		ToolKit.assertNotNull(user);
		userDAO.delete(user);
		nodeManager.updateAvailableUser();
	}

	@Scheduled(cron = "0 0 3 * * *")
	protected void updateBalance() {
		try {
			transactionWrapper.wrap(() -> {
				List<User> userList = userDAO.findAll();
				for (User user : userList) {
					if (user.getBalance() <= 0) continue;
					Plan plan = user.getPlan();
					if (plan == null) continue;
					user.setBalance(user.getBalance() - plan.getPrice());
				}
				userDAO.save(userList);
			});
			nodeManager.updateAvailableUser();
		} catch (Exception e) {
			log.error("Failed to update user balance [" + e.getClass().getName() + "]: " + e.getMessage());
		}
	}
}
