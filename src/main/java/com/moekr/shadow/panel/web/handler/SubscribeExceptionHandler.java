package com.moekr.shadow.panel.web.handler;

import com.moekr.shadow.panel.util.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice("com.moekr.shadow.panel.web.controller.subscribe")
public class SubscribeExceptionHandler {
	@ExceptionHandler(Exception.class)
	public String handle(HttpServletResponse response, Exception exception) {
		int error = HttpStatus.INTERNAL_SERVER_ERROR.value();
		if (exception instanceof ServiceException) {
			error = ((ServiceException) exception).getError();
		}
		response.setStatus(error);
		return "";
	}
}
