package com.moekr.shadow.panel.web.controller.view;

import com.moekr.shadow.panel.logic.service.UserService;
import com.moekr.shadow.panel.util.ServiceException;
import com.moekr.shadow.panel.util.ToolKit;
import com.moekr.shadow.panel.web.dto.form.RegisterForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@Controller
public class LoginAndRegisterController {
	private final UserService userService;

	@Autowired
	public LoginAndRegisterController(UserService userService) {
		this.userService = userService;
	}

	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	@GetMapping("/login.html")
	public String login(@RequestParam Optional<String> from, Model model) {
		if (ToolKit.hasLogin()) {
			return "redirect:/";
		} else if (StringUtils.equals("logout", from.orElse(null))) {
			model.addAttribute("success", "您已成功退出登录！");
		} else if (StringUtils.equals("login", from.orElse(null))) {
			model.addAttribute("error", "用户不存在或密码错误！");
		}
		return "login";
	}

	@GetMapping("/register.html")
	public String register() {
		if (ToolKit.hasLogin()) {
			return "redirect:/";
		} else {
			return "register";
		}
	}

	@PostMapping("/register.html")
	public String register(@ModelAttribute @Valid RegisterForm form, Errors errors, Model model) {
		if (ToolKit.hasLogin()) {
			return "redirect:/";
		} else if (errors.hasFieldErrors()) {
			model.addAttribute("error", Objects.requireNonNull(errors.getFieldError()).getDefaultMessage());
		} else if (!StringUtils.equals(form.getPassword(), form.getConfirm())) {
			model.addAttribute("error", "两次输入的密码不一致！");
		} else {
			try {
				userService.register(form);
				model.addAttribute("success", "注册成功，页面将在5秒后跳转！");
			} catch (ServiceException e) {
				model.addAttribute("error", e.getMessage());
			}
		}
		return "register";
	}
}
