package com.moekr.shadow.panel.web.controller.view;

import com.moekr.shadow.panel.logic.service.UserService;
import com.moekr.shadow.panel.util.ServiceException;
import com.moekr.shadow.panel.web.dto.form.LoginForm;
import com.moekr.shadow.panel.web.dto.form.PasswordForm;
import com.moekr.shadow.panel.web.dto.form.RegisterForm;
import com.moekr.shadow.panel.web.security.SecurityConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class LoginController {
	private static final String PREVIOUS_SESSION_KEY = "previous_shadow_username";

	private final UserService userService;
	private final UserDetailsService userDetailsService;

	@Autowired
	public LoginController(UserService userService, UserDetailsService userDetailsService) {
		this.userService = userService;
		this.userDetailsService = userDetailsService;
	}

	@GetMapping("/login.html")
	public String login(Model model, HttpSession session) {
		model.addAttribute("username", session.getAttribute(PREVIOUS_SESSION_KEY));
		return "login";
	}

	@PostMapping("/login.html")
	public String login(@ModelAttribute @Valid LoginForm form, Errors errors, Model model, HttpSession session) {
		if (errors.hasErrors()) {
			model.addAttribute("username", form.getUsername());
			model.addAttribute("message", errors.getFieldError().getDefaultMessage());
			return "login";
		}
		try {
			UserDetails userDetails = userDetailsService.loadUserByUsername(form.getUsername());
			if (StringUtils.equals(DigestUtils.sha256Hex(form.getPassword()), userDetails.getPassword())) {
				session.setAttribute(SecurityConfig.SESSION_KEY, form.getUsername());
				session.setAttribute("remember", form.getRemember() != null);
				return "redirect:" + StringUtils.defaultString(form.getRedirect(), "/");
			}
		} catch (UsernameNotFoundException ignored) {
		}
		model.addAttribute("username", form.getUsername());
		model.addAttribute("message", "用户不存在或密码错误！");
		return "login";
	}

	@GetMapping("/logout.html")
	public String logout(HttpSession session) {
		Object value = session.getAttribute(SecurityConfig.SESSION_KEY);
		session.removeAttribute(SecurityConfig.SESSION_KEY);
		session.setAttribute(PREVIOUS_SESSION_KEY, value);
		return "redirect:/login.html";
	}

	@GetMapping("/register.html")
	public String register(@RequestParam(name = "i", required = false) String invitation, Model model) {
		model.addAttribute("invitation", invitation);
		return "register";
	}

	@PostMapping("/register.html")
	public String register(@ModelAttribute @Valid RegisterForm form, Errors errors, Model model, HttpSession session) {
		boolean hasError = errors.hasErrors();
		String message = hasError ? errors.getFieldError().getDefaultMessage() : null;
		if (!hasError && !StringUtils.equals(form.getPassword(), form.getConfirm())) {
			hasError = true;
			message = "两次输入的密码不一致！";
		}
		if (!hasError) {
			try {
				userService.register(form);
			} catch (ServiceException e) {
				hasError = true;
				message = e.getMessage();
			}
		}
		if (hasError) {
			model.addAttribute("message", message);
			model.addAttribute("invitation", form.getInvitation());
			model.addAttribute("username", form.getUsername());
			return "register";
		}
		session.setAttribute(PREVIOUS_SESSION_KEY, form.getUsername());
		return "redirect:/login.html";
	}

	@GetMapping("/password.html")
	public String password(Model model, HttpSession session) {
		model.addAttribute("username", session.getAttribute(SecurityConfig.SESSION_KEY));
		return "password";
	}

	@PostMapping("/password.html")
	public String password(@ModelAttribute @Valid PasswordForm form, Errors errors, Model model, HttpSession session) {
		if (errors.hasErrors()) {
			model.addAttribute("message", errors.getFieldError().getDefaultMessage());
		} else {
			String username = (String) session.getAttribute(SecurityConfig.SESSION_KEY);
			model.addAttribute("username", username);
			if (username == null) {
				//TODO
				model.addAttribute("success", "功能暂未开放！");
			} else if (StringUtils.equals(username, form.getUsername())) {
				if (StringUtils.equals(form.getPassword(), form.getConfirm())) {
					try {
						userService.password(form);
						model.addAttribute("success", "操作成功！");
					} catch (ServiceException e) {
						model.addAttribute("message", e.getMessage());
					}
				} else {
					model.addAttribute("message", "两次输入的密码不一致！");
				}
			} else {
				model.addAttribute("message", "用户名与当前已登录用户不符！");
			}
		}
		return "password";
	}
}
