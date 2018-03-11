package com.moekr.shadow.panel.web.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.moekr.shadow.panel.web.controller.subscribe")
public class SubscribeExceptionHandler {

	@ExceptionHandler(Exception.class)
	public String handle() {
		return "";
	}
}
