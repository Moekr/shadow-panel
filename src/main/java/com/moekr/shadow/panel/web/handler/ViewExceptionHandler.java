package com.moekr.shadow.panel.web.handler;

import com.moekr.shadow.panel.util.ServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice("com.moekr.shadow.panel.web.controller.view")
public class ViewExceptionHandler {
	private static final int DEFAULT_ERROR_CODE = 500;

	@ExceptionHandler(Exception.class)
	public String handle(HttpServletRequest request, Exception exception) {
		if (exception instanceof ServiceException) {
			request.setAttribute("error", ((ServiceException) exception).getError());
		} else {
			request.setAttribute("error", DEFAULT_ERROR_CODE);
		}
		request.setAttribute("message", exception.getMessage());
		return "err";
	}
}
