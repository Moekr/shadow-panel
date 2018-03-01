package com.moekr.shadow.panel.web.interceptor;

import com.moekr.shadow.panel.web.security.SecurityConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		if (StringUtils.startsWithAny(uri, "/api/", "/resource/", "/js/", "/css/")
				|| StringUtils.equalsAny(uri, "/favicon.ico", "/error")) {
			return true;
		}
		HttpSession session = request.getSession();
		Object username = session.getAttribute(SecurityConfig.SESSION_KEY);
		if (username != null) {
			if (StringUtils.equals(uri, "/login.html")) {
				response.sendRedirect("/");
				return false;
			} else {
				return true;
			}
		} else {
			if (StringUtils.equals(uri, "/login.html")) {
				return true;
			} else {
				if (StringUtils.equals(uri, "/")) {
					response.sendRedirect("/login.html");
				} else {
					response.sendRedirect("/login.html?redirect=" + URLEncoder.encode(uri, "UTF-8"));
				}
				return false;
			}
		}
	}
}
