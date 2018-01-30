package com.moekr.shadow.panel.logic.rpc;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@EqualsAndHashCode
@ToString
@Configuration
@ConfigurationProperties("shadow.rpc")
public class RpcConfiguration {
	private String scheme = "https";
	private Integer port = 443;
	private Integer connectTimeout = 5000;
	private Integer readTimeout = 5000;
	private Authorization authorization = new Authorization();

	@Data
	@EqualsAndHashCode
	@ToString
	public static class Authorization {
		private String username = "admin";
		private String password = "admin";
	}
}
