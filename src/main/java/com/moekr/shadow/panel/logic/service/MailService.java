package com.moekr.shadow.panel.logic.service;

import org.springframework.web.servlet.ModelAndView;

public interface MailService {
	void sendMail(String to, String subject, ModelAndView modelAndView);
}
