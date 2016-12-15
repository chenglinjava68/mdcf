package com.mindai.cf.scheduler.center.rocketmq.consumer;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.impl.util.MsgConvertUtil;
import com.mindai.common.annotation.RocketMqListener;

@RocketMqListener
public class SchedulerPayGateWayListener implements DefaultMessageListener {
	
	public Action consume(Message message, ConsumeContext context) {
		System.out.println(MsgConvertUtil.bytes2String(message.getBody(), "UTF-8") + "///--SchedulerPayGateWayListener---Receive: " + message.getTopic()+"//"+message.getTag());
		return Action.CommitMessage;
	}
}
