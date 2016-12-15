package com.mindai.cf.scheduler.center.rocketmq.config;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.aliyun.openservices.ons.api.Producer;
import com.mindai.cf.scheduler.center.rocketmq.producer.DefaultProducer;

@Configuration
public class RocketProducerConfig {

	/**
	 * 加载rocketMq数据源配置
	 * @throws IOException
	 */
	@Bean(name="producerProperties")
	public Properties producerProperties() throws IOException {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/rocketmq.properties"));
		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}

	@Bean
	public Producer createProducer(@Qualifier("producerProperties") Properties properties) throws Exception {
		DefaultProducer producer = new DefaultProducer();
		producer.setProperties(properties);
		producer.start();
		return producer;
	}
}
