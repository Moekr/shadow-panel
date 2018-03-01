package com.moekr.shadow.panel.web.controller.view;

import com.moekr.shadow.panel.web.security.SecurityConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Controller
public class LoginController {
	private static final String PREVIOUS_SESSION_KEY = "previous_shadow_username";

	private final UserDetailsService userDetailsService;

	@Autowired
	public LoginController(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@GetMapping("/login.html")
	public String login(Map<String, Object> parameterMap, HttpSession session) {
		parameterMap.put("username", session.getAttribute(PREVIOUS_SESSION_KEY));
		return "login";
	}

	@PostMapping("/login.html")
	public String login(@RequestBody MultiValueMap<String, String> form, HttpServletResponse response, Map<String, Object> parameterMap, HttpSession session) throws IOException {
		String username = form.getFirst("username");
		String password = form.getFirst("password");
		String redirect = StringUtils.defaultString(form.getFirst("redirect"), "/");
		if (username != null) {
			try {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				if (StringUtils.equals(password, userDetails.getPassword())) {
					session.setAttribute(SecurityConfig.SESSION_KEY, username);
					response.sendRedirect(redirect);
				}
			} catch (UsernameNotFoundException ignored) {
			}
		}
		parameterMap.put("username", username);
		parameterMap.put("message", "用户不存在或密码错误！");
		return "login";
	}

	@GetMapping("/logout.html")
	public String logout(HttpServletResponse response, HttpSession session) throws IOException {
		Object value = session.getAttribute(SecurityConfig.SESSION_KEY);
		session.removeAttribute(SecurityConfig.SESSION_KEY);
		session.setAttribute(PREVIOUS_SESSION_KEY, value);
		response.sendRedirect("/login.html");
		return "login";
	}
}
