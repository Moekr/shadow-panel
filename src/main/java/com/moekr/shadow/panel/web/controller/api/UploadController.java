package com.moekr.shadow.panel.web.controller.api;

import com.moekr.shadow.panel.logic.service.NodeService;
import com.moekr.shadow.panel.logic.service.RecordService;
import com.moekr.shadow.panel.logic.service.UserService;
import com.moekr.shadow.panel.logic.vo.model.NodeModel;
import com.moekr.shadow.panel.logic.vo.model.UserModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UploadController {
	private final UserService userService;
	private final NodeService nodeService;
	private final RecordService recordService;

	@Autowired
	public UploadController(UserService userService, NodeService nodeService, RecordService recordService) {
		this.userService = userService;
		this.nodeService = nodeService;
		this.recordService = recordService;
	}

	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	@PostMapping("/upload")
	public Node upload(@RequestBody String statistics, @RequestParam("n") Optional<Integer> nodeId) {
		if (!nodeId.isPresent()) {
			return new Node();
		}
		try {
			NodeModel node = nodeService.findById(nodeId.get());
			List<UserModel> userList = userService.available(node.getId());
			recordService.record(nodeId.get(), statistics);
			Node n = new Node();
			BeanUtils.copyProperties(node, n);
			n.setObfsParam(node.getServerObfsParam());
			for (UserModel user : userList) {
				User u = new User();
				u.setId(user.getId());
				u.setToken(user.getToken());
				n.getUsers().add(u);
			}
			return n;
		} catch (Exception e) {
			return new Node();
		}
	}

	@Data
	@EqualsAndHashCode
	@ToString
	private class Node {
		private Boolean enable;
		private Integer port;
		private String password;
		private String method;
		private String protocol;
		private String obfs;
		private String obfsParam;
		private List<User> users = new ArrayList<>();

	}

	@Data
	@EqualsAndHashCode
	@ToString
	private class User {
		private Integer id;
		private String token;
	}
}
