package com.moekr.shadow.panel.web.controller.view.dashboard;

import com.moekr.shadow.panel.logic.service.PlanService;
import com.moekr.shadow.panel.util.ServiceException;
import com.moekr.shadow.panel.web.dto.form.CreatePlanForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping("/dashboard/plan")
public class PlanManagementController {
	private final PlanService planService;

	@Autowired
	public PlanManagementController(PlanService planService) {
		this.planService = planService;
	}

	@GetMapping({"/", "/index.html"})
	public String index(Model model) {
		model.addAttribute("planList", planService.findAll());
		return "dashboard/plan/index";
	}

	@GetMapping("/create.html")
	public String create() {
		return "dashboard/plan/create";
	}

	@PostMapping("/create.html")
	public String create(@ModelAttribute @Valid CreatePlanForm form, Errors errors, Model model) {
		if (errors.hasFieldErrors()) {
			model.addAttribute("error", Objects.requireNonNull(errors.getFieldError()).getDefaultMessage());
		} else {
			try {
				planService.create(form);
				model.addAttribute("success", "操作成功，页面将在5秒后跳转！");
			} catch (ServiceException e) {
				model.addAttribute("error", e.getMessage());
			}
		}
		return "dashboard/plan/create";
	}
}
