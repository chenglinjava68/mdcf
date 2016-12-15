package com.mindai.cf.scheduler.center.rocketmq.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "rocketmq",locations = "classpath:rocketmq.properties")
public class RocketMqConfig {
	
	private String AccessKey;
	private String SecretKey;
	private String ProducerId;
	private String ConsumerId;
	private String SendMsgTimeoutMillis;
	private String ONSAddr;
	
	private Map<String, String> topic = new HashMap<>();
//	private String SchedulerPayGateWayTopic;
//	private String SchedulerPayGateWayListener;
//	private String SchedulerBizTopic;
//	private String SchedulerBizListener;
}
