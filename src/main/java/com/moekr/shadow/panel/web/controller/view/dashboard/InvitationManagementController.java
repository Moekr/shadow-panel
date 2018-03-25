package com.moekr.shadow.panel.web.controller.view.dashboard;

import com.moekr.shadow.panel.logic.service.InvitationService;
import com.moekr.shadow.panel.logic.service.PlanService;
import com.moekr.shadow.panel.util.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/dashboard/invitation")
public class InvitationManagementController {
	private final PlanService planService;
	private final InvitationService invitationService;

	@Autowired
	public InvitationManagementController(PlanService planService, InvitationService invitationService) {
		this.planService = planService;
		this.invitationService = invitationService;
	}

	@GetMapping({"/", "/index.html"})
	public String index(Model model) {
		model.addAttribute("invitationList", invitationService.findAll());
		return "dashboard/invitation/index";
	}

	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	@GetMapping("/create.html")
	public String createGet(@RequestParam("p") Optional<Integer> planId, Model model) {
		if (!planId.isPresent()) {
			return "redirect:/dashboard/invitation/";
		}
		model.addAttribute("plan", planService.findById(planId.get()));
		return "dashboard/invitation/create";
	}

	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	@PostMapping("/create.html")
	public String createPost(@RequestParam("p") Optional<Integer> planId, Model model) {
		if (!planId.isPresent()) {
			return "redirect:/dashboard/invitation/";
		}
		try {
			invitationService.create(planId.get());
			model.addAttribute("success", "创建成功，页面将在5秒后跳转！");
		} catch (ServiceException e) {
			model.addAttribute("error", e.getMessage());
		}
		return "dashboard/invitation/create";
	}
}
