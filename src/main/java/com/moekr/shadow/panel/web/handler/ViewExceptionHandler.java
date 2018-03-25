package com.moekr.shadow.panel.web.handler;

import com.moekr.shadow.panel.util.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice("com.moekr.shadow.panel.web.controller.view")
public class ViewExceptionHandler {
	@ExceptionHandler(Exception.class)
	public String handle(HttpServletResponse response, Model model, Exception exception) {
		int error = HttpStatus.INTERNAL_SERVER_ERROR.value();
		if (exception instanceof ServiceException) {
			error = ((ServiceException) exception).getError();
		}
		response.setStatus(error);
		model.addAttribute("error", error);
		model.addAttribute("message", StringUtils.abbreviate(exception.getMessage(), 80));
		return "error";
	}
}
