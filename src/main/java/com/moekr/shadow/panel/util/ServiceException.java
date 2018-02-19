package com.moekr.shadow.panel.util;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ServiceException extends RuntimeException {
	private static final Map<Integer, String> ERROR_MESSAGE_MAP = new HashMap<>();
	private static final String UNKNOWN_ERROR_MESSAGE = "Unknown error";

	public static final int NOT_FOUND = 100;

	static {
		ERROR_MESSAGE_MAP.put(NOT_FOUND, "Not found");
	}

	private int error;

	public ServiceException(int error) {
		this(error, ERROR_MESSAGE_MAP.getOrDefault(error, UNKNOWN_ERROR_MESSAGE));
	}

	public ServiceException(int error, String message) {
		super(message);
		this.error = error;
	}

	@Override
	public String getMessage() {
		String message = super.getMessage();
		if (message == null) {
			message = super.getCause().getMessage();
		}
		if (message == null) {
			message = super.getCause().getClass().getName();
		}
		return message;
	}
}
