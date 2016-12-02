package com.mindai.cf.scheduler.center.util;

import java.util.List;

import com.mindai.cf.scheduler.center.enums.RabbitQueues;

public class EnumsUtil {

	public static List<String> rabbitQueuesCode() {
		
		for (RabbitQueues rate : RabbitQueues.values()) {
			System.out.println(rate.getCode() + "/" + rate.getDesc());
		}
		return null;
	}

	public static void main(String[] args) {
		EnumsUtil.rabbitQueuesCode();
	}
}
