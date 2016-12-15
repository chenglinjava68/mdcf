package com.mindai.cf.scheduler.center.rocketmq.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.OnExceptionContext;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendCallback;
import com.aliyun.openservices.ons.api.SendResult;
import com.mindai.cf.scheduler.center.enums.RocketQueues;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RocketProducerService implements SendCallback {

	@Autowired
	Producer producer;
	
	/**
	 * 单边发送
	 * @param rocket
	 * @param content
	 */
	public void oneWaySender(RocketQueues rocket, String content) {
		Message msg = new Message();
		msg.setTopic(rocket.getTopic());
		msg.setTag(rocket.getTag());
		msg.setBody(content.getBytes());
		producer.sendOneway(msg);
		log.info(rocket.getTopic() + ":" + rocket.getTag() + "//消息队列发送完成------------------------");
	}

	/**
	 * 异步发送 默认回调函数
	 * @param rocket
	 * @param content
	 */
	public void sendAsync(RocketQueues rocket, String content) {

		Message msg = new Message();
		msg.setTopic(rocket.getTopic());
		msg.setTag(rocket.getTag());
		msg.setBody(content.getBytes());
		producer.sendAsync(msg, this);
		log.info(rocket.getTopic() + ":" + rocket.getTag() + "//消息队列发送完成------------------------");
	}
	
	/**
	 * 指定回调处理类
	 * @param rocket
	 * @param content
	 * @param sendCallBack
	 */
	public void sendAsyncWithCallBack(RocketQueues rocket, String content,SendCallback sendCallBack) {

		Message msg = new Message();
		msg.setTopic(rocket.getTopic());
		msg.setTag(rocket.getTag());
		msg.setBody(content.getBytes());
		producer.sendAsync(msg, sendCallBack);
		log.info(rocket.getTopic() + ":" + rocket.getTag() + "//消息队列发送完成------------------------");
	}

	/**
	 * 同步发送
	 * @param rocket
	 * @param content
	 * @return
	 */
	public SendResult synSend(RocketQueues rocket, String content) {

		Message msg = new Message();
		msg.setTopic(rocket.getTopic());
		msg.setTag(rocket.getTag());
		msg.setBody(content.getBytes());
		log.info(rocket.getTopic() + ":" + rocket.getTag() + "//消息队列发送完成------------------------");
		return producer.send(msg);
	}

	@Override
	public void onSuccess(final SendResult sendResult) {
		// 消费发送成功
		log.info("send message success. topic=" + sendResult.getTopic() + ", msgId=" + sendResult.getMessageId());
	}

	@Override
	public void onException(OnExceptionContext context) {
		// 消息发送失败
		log.info("send message failed. topic=" + context.getTopic() + ", msgId=" + context.getMessageId());
	}
}
