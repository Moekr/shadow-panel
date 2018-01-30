package com.moekr.shadow.panel.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moekr.shadow.panel.util.enums.NodeStatus;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

public abstract class ToolKit {
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

	public static <T> List<T> iterableToList(Iterable<T> iterable) {
		List<T> list = new ArrayList<>();
		iterable.forEach(list::add);
		return list;
	}

	public static <T> List<T> sort(List<T> list, Comparator<T> comparator) {
		list.sort(comparator);
		return list;
	}

	public static void assertNotNull(Object object) {
		if (object == null) {
			throw new ServiceException(ServiceException.NOT_FOUND);
		}
	}

	public static String convertStackTrace(Throwable throwable) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		throwable.printStackTrace(printWriter);
		return stringWriter.toString();
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
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		return (UserDetails) authentication.getPrincipal();
	}

	public static String displayNodeStatus(NodeStatus nodeStatus) {
		switch (nodeStatus) {
			case ONLINE:
				return "success";
			case UNSTABLE:
				return "warning";
			case OFFLINE:
				return "danger";
		}
		return "info";
	}

	public static String displayUsedRate(double rate) {
		if (rate < 60) {
			return "success";
		} else if (rate > 85) {
			return "danger";
		} else {
			return "warning";
		}
	}

	private static ObjectMapper OBJECT_MAPPER;

	public static <T> T parse(String json, Class<T> clazz) throws IOException {
		initialObjectMapper();
		return OBJECT_MAPPER.readValue(json, clazz);
	}

	public static String parse(Object object) throws JsonProcessingException {
		initialObjectMapper();
		return OBJECT_MAPPER.writeValueAsString(object);
	}

	private static void initialObjectMapper() {
		if (OBJECT_MAPPER == null) {
			OBJECT_MAPPER = new ObjectMapper();
			OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		}
	}
}
