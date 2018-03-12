package com.moekr.shadow.panel.util;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@EqualsAndHashCode
@ToString
@Configuration
@ConfigurationProperties("shadow")
public class ShadowProperties {
	private Subscribe subscribe = new Subscribe();
	private Mail mail = new Mail();

	@Data
	@EqualsAndHashCode
	@ToString
	public class Subscribe {
		private String group = "Shadow-Panel";
	}

	@Data
	@EqualsAndHashCode
	@ToString
	public class Mail {
		private String from;
		private String personal = "Shadow Panel";
	}
}
