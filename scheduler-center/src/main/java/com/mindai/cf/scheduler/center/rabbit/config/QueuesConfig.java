package com.mindai.cf.scheduler.center.rabbit.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mindai.cf.scheduler.center.enums.RabbitQueues;

@Configuration
public class QueuesConfig {

	@Bean(name="queueWithHolding")
	public Queue queueWithHolding(RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(RabbitQueues.SCHEDULER_PAYGATEWAY_WITHHOLDING.getCode(), true);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}

	@Bean
	public Binding bindingWithHolding(@Qualifier("queueWithHolding") Queue queueWithHolding, DirectExchange directExchange, RabbitAdmin rabbitAdmin) {

		Binding binding = BindingBuilder.bind(queueWithHolding).to(directExchange)
				.with(RabbitQueues.SCHEDULER_PAYGATEWAY_WITHHOLDING.getCode());
		rabbitAdmin.declareBinding(binding);
		return binding;
	}

	
	
	@Bean(name="queuePayProxy")
	public Queue queuePayProxy(RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(RabbitQueues.SCHEDULER_PAYGATEWAY_PAYPROXY.getCode(), true);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}

	@Bean
	public Binding bindingPayProxy(@Qualifier("queuePayProxy") Queue queuePayProxy, DirectExchange directExchange, RabbitAdmin rabbitAdmin) {

		Binding binding = BindingBuilder.bind(queuePayProxy).to(directExchange)
				.with(RabbitQueues.SCHEDULER_PAYGATEWAY_PAYPROXY.getCode());
		rabbitAdmin.declareBinding(binding);
		return binding;
	}
	
	
	@Bean(name="queueQuickPayMent")
	public Queue queueQuickPayMent(RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(RabbitQueues.SCHEDULER_PAYGATEWAY_PAYPROXY.getCode(), true);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}

	@Bean
	public Binding bindingQuickPayMent(@Qualifier("queueQuickPayMent") Queue queueQuickPayMent, DirectExchange directExchange, RabbitAdmin rabbitAdmin) {

		Binding binding = BindingBuilder.bind(queueQuickPayMent).to(directExchange)
				.with(RabbitQueues.SCHEDULER_PAYGATEWAY_PAYPROXY.getCode());
		rabbitAdmin.declareBinding(binding);
		return binding;
	}
	
	@Bean
	Queue queueFoo(RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue("queue.foo", true);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}

	@Bean
	Binding bindingExchangeFoo(Queue queueFoo, DirectExchange directExchange, RabbitAdmin rabbitAdmin) {
		Binding binding = BindingBuilder.bind(queueFoo).to(directExchange).with("queue.foo");
		rabbitAdmin.declareBinding(binding);
		return binding;
	}

	@Bean
	Queue queueBar(RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue("queue.bar", true);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}

	@Bean
	Binding bindingExchangeBar(Queue queueBar, DirectExchange directExchange, RabbitAdmin rabbitAdmin) {
		Binding binding = BindingBuilder.bind(queueBar).to(directExchange).with("queue.bar");
		rabbitAdmin.declareBinding(binding);
		return binding;
	}

}
