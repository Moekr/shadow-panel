package com.moekr.shadow.panel.web.controller.view;

import com.moekr.shadow.panel.logic.service.*;
import com.moekr.shadow.panel.logic.vo.model.ClientModel;
import com.moekr.shadow.panel.logic.vo.model.NodeModel;
import com.moekr.shadow.panel.logic.vo.model.UserModel;
import com.moekr.shadow.panel.util.ToolKit;
import com.moekr.shadow.panel.web.security.WebSecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.stream.Collectors;

@Controller
public class ViewController {
	private final UserService userService;
	private final NodeService nodeService;
	private final ClientService clientService;
	private final RecordService recordService;
	private final PropertyService propertyService;

	@Autowired
	public ViewController(UserService userService, NodeService nodeService, ClientService clientService, RecordService recordService, PropertyService propertyService) {
		this.userService = userService;
		this.nodeService = nodeService;
		this.clientService = clientService;
		this.recordService = recordService;
		this.propertyService = propertyService;
	}

	@GetMapping({"/", "/index.html"})
	public String index(Model model) {
		UserDetails userDetails = ToolKit.currentUserDetails();
		if (userDetails.getAuthorities().contains(WebSecurityConfiguration.ADMIN_AUTHORITY)) {
			return "redirect:/dashboard/";
		}
		model.addAttribute("properties", propertyService.properties());
		model.addAttribute("user", userService.findByUsername(userDetails.getUsername()));
		return "view/index";
	}

	@GetMapping("/node.html")
	public String node(Model model) {
		UserModel user = userService.findByUsername(ToolKit.currentUserDetails().getUsername());
		model.addAttribute("properties", propertyService.properties());
		model.addAttribute("user", user);
		model.addAttribute("nodes", nodeService.available(user.getId()).stream().collect(Collectors.groupingBy(NodeModel::getRegion)));
		return "view/node";
	}

	@GetMapping("/statistics.html")
	public String statistics(Model model) {
		UserModel user = userService.findByUsername(ToolKit.currentUserDetails().getUsername());
		model.addAttribute("properties", propertyService.properties());
		model.addAttribute("user", user);
		model.addAttribute("dayHourChartData", recordService.hourData(user.getId(), 1));
		model.addAttribute("monthDayChartData", recordService.dayData(user.getId(), 30));
		model.addAttribute("monthHourChartData", recordService.hourData(user.getId(), 30));
		return "view/statistics";
	}

	@GetMapping("/client.html")
	public String client(Model model) {
		model.addAttribute("properties", propertyService.properties());
		model.addAttribute("user", userService.findByUsername(ToolKit.currentUserDetails().getUsername()));
		model.addAttribute("clients", clientService.findAll().stream().collect(Collectors.groupingBy(ClientModel::getPlatform)));
		return "view/client";
	}

	@GetMapping("/help.html")
	public String help(Model model) {
		model.addAttribute("properties", propertyService.properties());
		model.addAttribute("user", userService.findByUsername(ToolKit.currentUserDetails().getUsername()));
		return "view/help";
	}
}
