package com.moekr.shadow.panel.util;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class ToolKit {
	public static final String BANNER = "Shadow Panel";
	public static final String VERSION = "0.3.2";

	public static Map<String, Object> emptyResponseBody() {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("err", 0);
		return responseBody;
	}

	public static Map<String, Object> assemblyResponseBody(Object res) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("err", 0);
		responseBody.put("res", res);
		return responseBody;
	}

	public static Map<String, Object> assemblyResponseBody(int error, String message) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("err", error);
		responseBody.put("msg", message);
		return responseBody;
	}

	public static HttpStatus httpStatus(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		if (statusCode == null) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
		try {
			return HttpStatus.valueOf(statusCode);
		} catch (Exception e) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}

	public static UserDetails currentUserDetails() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		Object principle = authentication.getPrincipal();
		return (UserDetails) principle;
	}

	public static boolean hasLogin() {
		try {
			return currentUserDetails() != null;
		} catch (Exception e) {
			return false;
		}
	}

	public static Date convert(LocalDateTime localDateTime) {
		return new Date(localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond() * DateUtils.MILLIS_PER_SECOND);
	}

	public static Date convert(LocalDate localDate) {
		return convert(localDate.atStartOfDay());
	}

	public static long timestamp(LocalDateTime localDateTime) {
		return localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
	}

	public static long timestamp(LocalDate localDate) {
		return timestamp(localDate.atStartOfDay());
	}
}
