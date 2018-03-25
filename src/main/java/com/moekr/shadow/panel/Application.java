package com.moekr.shadow.panel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.ZoneId;
import java.util.TimeZone;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.moekr.shadow.panel.data.dao")
public class Application extends SpringApplication {
	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("GMT+8")));
		SpringApplication.run(Application.class, args);
	}
}
