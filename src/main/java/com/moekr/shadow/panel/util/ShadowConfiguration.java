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
public class ShadowConfiguration {
	private Subscribe subscribe = new Subscribe();

	@Data
	@EqualsAndHashCode
	@ToString
	public class Subscribe {
		private String group = "Shadow-Panel";
	}
}
