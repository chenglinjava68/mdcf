package com.mindai.cf.scheduler.center.enums;

public enum RabbitQueues {

	SCHEDULER_PAYGATEWAY_PAYPROXY("SCHEDULER_PAYGATEWAY_PAYPROXY", "代付消息队列"), 
	SCHEDULER_PAYGATEWAY_WITHHOLDING("SCHEDULER_PAYGATEWAY_WITHHOLDING","代扣消息队列"), 
	SCHEDULER_PAYGATEWAY_QUICKPAYMENT("SCHEDULER_PAYGATEWAY_QUICKPAYMENT", "快捷支付消息队列"),
	SCHEDULER_BUSINESS_AUTOPAY("SCHEDULER_BUSINESS_AUTOPAY", "自动还款任务调度");

	private final String code;
	private final String routingKey;
	private final String desc;

	RabbitQueues(String code, String desc) {
		this.code = code;
		this.routingKey = code;
		this.desc = desc;
	}

	RabbitQueues(String code, String routingKey, String desc) {
		this.code = code;
		this.routingKey = routingKey;
		this.desc = desc;
	}

	public String getCode() {
		return this.code;
	}

	public String getDesc() {
		return desc;
	}

	public String getRoutingKey() {
		return routingKey;
	}

}
