package com.moekr.shadow.panel.util;

import com.moekr.shadow.panel.logic.vo.NodeVO;
import com.moekr.shadow.panel.util.enums.NodeStatus;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class ToolKit {
	public static final String VERSION = "0.2.1";

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

	public static void assertNotNull(Object object) {
		assertTrue(object != null, ServiceException.NOT_FOUND);
	}

	public static void assertTrue(boolean condition, int code) {
		assertTrue(condition, code, null);
	}

	public static void assertTrue(boolean condition, int code, String message) {
		if (!condition) {
			if (message == null) {
				throw new ServiceException(code);
			} else {
				throw new ServiceException(code, message);
			}
		}
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
		if (nodeStatus == null) return "info";
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

	public static int rowSpanOf(Collection<NodeVO> nodeSet) {
		return nodeSet.size() * 2 + nodeSet.stream().map(node -> node.getPortSet().size()).reduce((a, b) -> a + b).orElse(0);
	}
}
