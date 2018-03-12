package com.moekr.shadow.panel;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.moekr.shadow.panel.data.repository")
@DubboComponentScan("com.moekr.shadow.panel.logic.rpc")
@EnableAsync
@EnableScheduling
public class Application extends SpringApplication {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
