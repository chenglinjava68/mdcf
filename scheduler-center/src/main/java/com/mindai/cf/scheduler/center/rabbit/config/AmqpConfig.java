package com.mindai.cf.scheduler.center.rabbit.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.mindai.cf.scheduler.center.enums.SysConstants;

/**
 * 
 * @author lilei 2016年11月28日
 *
 */
@Configuration
public class AmqpConfig {

	
	@Autowired
	public RabbitMqConfig rabbitMqConfig;
	
	/**
	 * 获取一个连接的工厂
	 * @return
	 */
	@Bean
	public ConnectionFactory connectionFactory() {
		
		System.out.println(rabbitMqConfig.getHost());
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setAddresses(rabbitMqConfig.getHost()+":"+rabbitMqConfig.getPort());
		connectionFactory.setUsername(rabbitMqConfig.getUsername());
		connectionFactory.setPassword(rabbitMqConfig.getPassword());
		connectionFactory.setVirtualHost("/");
		connectionFactory.setPublisherConfirms(true); // 必须要设置 主要是为了执行回调方法
		return connectionFactory;
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)// 必须是prototype类型
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate template = new RabbitTemplate(connectionFactory());
		template.setExchange(SysConstants.DIRECT_EXCHANGE);//交换机
		template.setMessageConverter(messageConverter());//消息对象json转换类
		return template;
	}
	
	/**
	 * 消息对象json转换类
	 * @return
	 */
	@Bean
	public Jackson2JsonMessageConverter messageConverter(){
		return new Jackson2JsonMessageConverter();
	}
}
