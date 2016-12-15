package com.mindai.cf.scheduler.center.rocketmq.producer;

import java.util.Properties;

import javax.annotation.PreDestroy;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendCallback;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.exception.ONSClientException;

public class DefaultProducer implements Producer {
	
	private Properties properties;

	private Producer producer;

	@Override
	public void start() {
		if (null == this.properties) {
			throw new ONSClientException("properties not set");
		}

		this.producer = ONSFactory.createProducer(this.properties);
		this.producer.start();
	}

	@PreDestroy
	@Override
	public void shutdown() {
		if (this.producer != null) {
			this.producer.shutdown();
		}
	}

	@Override
	public SendResult send(Message message) {
		return this.producer.send(message);
	}

	@Override
	public void sendOneway(Message message) {
		this.producer.sendOneway(message);
	}

	@Override
	public void sendAsync(Message message, SendCallback sendCallback) {
		this.producer.sendAsync(message, sendCallback);
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	@Override
	public boolean isStarted() {
		return this.producer.isStarted();
	}

	@Override
	public boolean isClosed() {
		return this.producer.isClosed();
	}
}
