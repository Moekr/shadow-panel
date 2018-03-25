package com.moekr.shadow.panel.logic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@EnableScheduling
@Configuration
public class ExecutorConfiguration {
	@Bean
	public ScheduledExecutorService scheduledExecutor() {
		return Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() * 4);
	}
}
