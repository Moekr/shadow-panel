package com.moekr.shadow.panel.web.controller.view.dashboard;

import com.moekr.shadow.panel.logic.service.UserService;
import com.moekr.shadow.panel.util.ServiceException;
import com.moekr.shadow.panel.web.dto.form.ChangeRevokeAtFrom;
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
@RequestMapping("/dashboard/user")
public class UserManagementController {
	private final UserService userService;

	@Autowired
	public UserManagementController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping({"/", "/index.html"})
	public String index(Model model) {
		model.addAttribute("userList", userService.findAll());
		return "dashboard/user/index";
	}

	@PostMapping({"/", "/index.html"})
	public String index(@ModelAttribute @Valid ChangeRevokeAtFrom from, Errors errors, Model model) {
		if (errors.hasFieldErrors()) {
			model.addAttribute("error", Objects.requireNonNull(errors.getFieldError()).getDefaultMessage());
		} else {
			try {
				userService.changeRevokeAt(from);
				model.addAttribute("success", "操作成功！");
			} catch (ServiceException e) {
				model.addAttribute("error", e.getMessage());
			}
		}
		model.addAttribute("userList", userService.findAll());
		return "dashboard/user/index";
	}
}
