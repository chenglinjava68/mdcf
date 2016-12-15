package com.mindai.cf.scheduler.center.util;

import java.util.ArrayList;
import java.util.List;

import com.mindai.cf.scheduler.center.enums.RocketQueues;


public class EnumsUtil {

	public static List<RocketQueues> getRocketQueuesByListener(String listener) {
		List<RocketQueues> list = new ArrayList<>();
		for (RocketQueues rate : RocketQueues.values()) {
			if (listener.equals(rate.getListener())) {
				list.add(rate);
			}
		}
		return list;
	}
	
	public static RocketQueues getRocketQueues (String listener) {
		for (RocketQueues rate : RocketQueues.values()) {
			if (listener.equals(rate.getListener())) {
				return rate;
			}
		}
		return null;
	}
}
