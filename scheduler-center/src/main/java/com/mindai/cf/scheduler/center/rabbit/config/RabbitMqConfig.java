package com.mindai.cf.scheduler.center.rabbit.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
@ConfigurationProperties(prefix = "spring.rabbitmq")
@Component
@Data
public class RabbitMqConfig {
	
    //代付
	private String host;
	private String port;
	private String username;
	private String password;
}
