package com.moekr.shadow.panel.logic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
public class ScheduledExecutorConfiguration {

	@Bean
	public ScheduledExecutorService scheduledExecutor() {
		return Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
	}
}
