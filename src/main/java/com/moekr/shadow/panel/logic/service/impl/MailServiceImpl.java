package com.moekr.shadow.panel.logic.service.impl;

import com.moekr.shadow.panel.logic.service.MailService;
import com.moekr.shadow.panel.util.ShadowProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

@Component
public class MailServiceImpl implements MailService {
	private final TemplateEngine templateEngine;
	private final JavaMailSender mailSender;
	private final ShadowProperties properties;

	@Autowired
	public MailServiceImpl(TemplateEngine templateEngine, JavaMailSender mailSender, ShadowProperties properties) {
		this.templateEngine = templateEngine;
		this.mailSender = mailSender;
		this.properties = properties;
	}

	@Override
	@Async
	public void sendMail(String to, String subject, ModelAndView modelAndView) {
		if (StringUtils.isBlank(properties.getMail().getFrom())) return;
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			try {
				helper.setFrom(properties.getMail().getFrom(), properties.getMail().getPersonal());
			} catch (UnsupportedEncodingException e) {
				helper.setFrom(properties.getMail().getFrom());
			}
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(templateEngine.process(modelAndView.getViewName(), new Context(Locale.CHINA, modelAndView.getModel())), true);
		} catch (MessagingException e) {
			return;
		}
		mailSender.send(message);
	}
}
