package com.mindai.cf.scheduler.center.enums;

public enum JobGroup {

	TRANS_CENTER("tans_center", "交易中心"), PAY_GATEWAY("pay_gateway", "交易网关");

	String code;
	String desc;

	JobGroup(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
