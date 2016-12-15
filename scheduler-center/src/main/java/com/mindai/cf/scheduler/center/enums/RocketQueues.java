package com.mindai.cf.scheduler.center.enums;

public enum RocketQueues {

	SCHEDULER_PAYGATEWAY_PAYPROXY("SCHEDULER_PAYGATEWAY", "PAYPROXY","","代付消息队列"), 
	SCHEDULER_PAYGATEWAY_WITHHOLDING("SCHEDULER_PAYGATEWAY","WITHHOLDING","","代扣消息队列"), 
	SCHEDULER_PAYGATEWAY_QUICKPAYMENT("SCHEDULER_PAYGATEWAY","QUICKPAYMENT","", "快捷支付消息队列"),
	SCHEDULER_PAYGATEWAY_SMALLLOANS("SCHEDULER_PAYGATEWAY","SMALLLOANS","", "小贷贷款结果查询"),
	
	SCHEDULER_BIZ_AUTOPAY("SCHEDULER_BIZ","AUTOPAY","","自动还款任务调度");

	private final String topic;
	private final String tag;
	private final String desc;
	private final String listener;
	
	RocketQueues(String topic, String tag,String listener,String desc) {
		this.topic = topic;
		this.tag = tag;
		this.listener = listener;
		this.desc = desc;
	}

	public String getTopic() {
		return topic;
	}

	public String getTag() {
		return tag;
	}

	public String getDesc() {
		return desc;
	}

	public String getListener() {
		return listener;
	}
}
