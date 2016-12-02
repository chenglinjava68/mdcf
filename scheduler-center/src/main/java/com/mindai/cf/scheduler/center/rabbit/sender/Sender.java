package com.mindai.cf.scheduler.center.rabbit.sender;

import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mindai.cf.scheduler.center.enums.SysConstants;

@Component("sender")
public class Sender implements ConfirmCallback{

	private RabbitTemplate rabbitTemplate;

	/**
	 * 构造方法注入
	 */
	@Autowired
	public Sender(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
		rabbitTemplate.setConfirmCallback(this); // rabbitTemplate如果为单例的话，那回调就是最后设置的内容
	}

	public void sendMsg(String routingKey,String content) {
		CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
		rabbitTemplate.convertAndSend(SysConstants.DIRECT_EXCHANGE,routingKey, content, correlationId);
	}

	/**
	 * 回调
	 */
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		
		System.out.println(" 回调id:" + correlationData);
		if (ack) {
			System.out.println("消息成功消费");
		} else {
			System.out.println("消息消费失败:" + cause);
		}
	}
}