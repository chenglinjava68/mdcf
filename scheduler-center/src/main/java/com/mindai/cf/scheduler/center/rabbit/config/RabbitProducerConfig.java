package com.mindai.cf.scheduler.center.rabbit.config;


import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

import com.mindai.cf.scheduler.center.enums.SysConstants;

@Configuration
public class RabbitProducerConfig {

    @Bean
    RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    /**
	 * 针对消费者配置 1. 设置交换机类型 2. 将队列绑定到交换机
	 * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念 
	 * HeadersExchange 通过添加属性key-value匹配 DirectExchange:按照routingkey分发到指定队列
	 * TopicExchange:多关键字匹配
	 */
    @Bean
    public DirectExchange directExchange(RabbitAdmin rabbitAdmin) {
		/**
		 * rabbit:direct-exchange：定义exchange模式为direct，意思就是消息与一个特定的路由键完全匹配，才会转发。
		 * rabbit:binding：设置消息queue匹配的key durable:是否持久化
		 * exclusive: 仅创建者可以使用的私有队列，断开后自动删除
		 * auto_delete: 当所有消费客户端连接断开后，是否自动删除队列
		 */
    	DirectExchange directExchange = new DirectExchange(SysConstants.DIRECT_EXCHANGE, true, false);// 在Spring
        rabbitAdmin.declareExchange(directExchange);
        return directExchange;
    }
    
    @Bean
    public TopicExchange topicExchange(RabbitAdmin rabbitAdmin) {
        TopicExchange topicExchange = new TopicExchange(SysConstants.TOPIC_EXCHANGE);
        rabbitAdmin.declareExchange(topicExchange);
        return topicExchange;
    }
    
    /**
     * 生产者用
     * @return
     */
    @Bean
    public RabbitMessagingTemplate rabbitMessagingTemplate(RabbitTemplate rabbitTemplate) {
        RabbitMessagingTemplate rabbitMessagingTemplate = new RabbitMessagingTemplate();
        rabbitMessagingTemplate.setMessageConverter(jackson2Converter());
        rabbitMessagingTemplate.setRabbitTemplate(rabbitTemplate);
        return rabbitMessagingTemplate;
    }

    @Bean
    public MappingJackson2MessageConverter jackson2Converter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        return converter;
    }
}