package com.moekr.shadow.panel.web.handler;

import com.moekr.shadow.panel.util.ServiceException;
import com.moekr.shadow.panel.util.ToolKit;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestControllerAdvice("com.moekr.shadow.panel.web.controller.api")
public class ApiExceptionHandler {
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Map<String, Object> handle(HttpServletResponse response, Exception exception) {
		int error = HttpStatus.INTERNAL_SERVER_ERROR.value();
		if (exception instanceof ServiceException) {
			error = ((ServiceException) exception).getError();
		}
		response.setStatus(error);
		return ToolKit.assemblyResponseBody(error, exception.getMessage());
	}
}
