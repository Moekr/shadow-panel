package com.moekr.shadow.panel.web.controller.view;

import com.moekr.shadow.panel.logic.service.*;
import com.moekr.shadow.panel.logic.vo.ClientVO;
import com.moekr.shadow.panel.logic.vo.NodeVO;
import com.moekr.shadow.panel.logic.vo.UserVO;
import com.moekr.shadow.panel.util.ToolKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ViewController {
	private final UserService userService;
	private final NodeService nodeService;
	private final ClientService clientService;
	private final RecordService recordService;
	private final PropertyService propertyService;

	@Autowired
	public ViewController(UserService userService, NodeService nodeService, ClientService clientService,
						  RecordService recordService, PropertyService propertyService) {
		this.userService = userService;
		this.nodeService = nodeService;
		this.clientService = clientService;
		this.recordService = recordService;
		this.propertyService = propertyService;
	}

	@GetMapping({"/", "/index.html"})
	public String index(Map<String, Object> parameterMap) {
		UserVO user = userService.retrieve(ToolKit.currentUserDetails().getUsername());
		parameterMap.put("properties", propertyService.properties());
		parameterMap.put("user", user);
		parameterMap.put("weekChartData", recordService.dayData(user.getId(), 7));
		parameterMap.put("dayChartData", recordService.hourData(user.getId()));
		return "index";
	}

	@GetMapping("/node.html")
	public String node(Map<String, Object> parameterMap) {
		parameterMap.put("properties", propertyService.properties());
		parameterMap.put("user", userService.retrieve(ToolKit.currentUserDetails().getUsername()));
		parameterMap.put("nodes", nodeService.retrieve().stream().collect(Collectors.groupingBy(NodeVO::getRegion)));
		return "node";
	}

	@GetMapping("/statistics.html")
	public String statistics(Map<String, Object> parameterMap) {
		UserVO user = userService.retrieve(ToolKit.currentUserDetails().getUsername());
		parameterMap.put("properties", propertyService.properties());
		parameterMap.put("user", user);
		parameterMap.put("monthChartData", recordService.dayData(user.getId(), 30));
		return "statistics";
	}

	@GetMapping("/client.html")
	public String client(Map<String, Object> parameterMap) {
		parameterMap.put("properties", propertyService.properties());
		parameterMap.put("user", userService.retrieve(ToolKit.currentUserDetails().getUsername()));
		parameterMap.put("clients", clientService.retrieve().stream().collect(Collectors.groupingBy(ClientVO::getPlatform)));
		return "client";
	}
}
