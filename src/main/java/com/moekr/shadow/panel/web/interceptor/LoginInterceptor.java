package com.moekr.shadow.panel.web.interceptor;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.moekr.shadow.panel.util.ToolKit;
import com.moekr.shadow.panel.web.security.SecurityConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	private static final String REMEMBER_COOKIE = "REMEMBER";
	private static final int REMEMBER_DAYS = 7;

	private final Cache<String, String> rememberCache = CacheBuilder.newBuilder().expireAfterAccess(7, TimeUnit.DAYS).build();

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
			if (StringUtils.equalsAny(uri, "/login.html", "/register.html")) {
				response.sendRedirect("/");
				return false;
			} else {
				return true;
			}
		} else {
			String uuid = request.getCookies() == null ? null : Arrays.stream(request.getCookies())
					.filter(cookie -> StringUtils.equals(cookie.getName(), REMEMBER_COOKIE))
					.map(Cookie::getValue)
					.findFirst().orElse(null);
			String remember = uuid != null ? rememberCache.getIfPresent(uuid) : null;
			if (remember != null) {
				request.getSession().setAttribute(SecurityConfig.SESSION_KEY, remember);
				if (StringUtils.equalsAny(uri, "/login.html", "/register.html")) {
					response.sendRedirect("/");
					return false;
				} else {
					return true;
				}
			}
			if (StringUtils.equalsAny(uri, "/login.html", "/register.html")) {
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

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
		String uri = request.getRequestURI();
		String method = request.getMethod();
		if (StringUtils.equals(uri, "/login.html") && StringUtils.equals(method, HttpMethod.POST.name())) {
			Object remember = request.getSession().getAttribute("remember");
			request.getSession().removeAttribute("remember");
			if (remember != null && remember instanceof Boolean && ((Boolean) remember)) {
				String uuid = ToolKit.randomUUID();
				Cookie cookie = new Cookie(REMEMBER_COOKIE, uuid);
				cookie.setMaxAge((int) (REMEMBER_DAYS * DateUtils.MILLIS_PER_DAY / DateUtils.MILLIS_PER_SECOND));
				response.addCookie(cookie);
				rememberCache.put(uuid, (String) request.getSession().getAttribute(SecurityConfig.SESSION_KEY));
			}
		} else if (StringUtils.equals(uri, "/logout.html")) {
			String uuid = request.getCookies() == null ? null : Arrays.stream(request.getCookies())
					.filter(cookie -> StringUtils.equals(cookie.getName(), REMEMBER_COOKIE))
					.map(Cookie::getValue)
					.findFirst().orElse(null);
			if (uuid != null) {
				rememberCache.invalidate(uuid);
			}
		}
	}
}
