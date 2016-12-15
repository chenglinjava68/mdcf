package com.mindai.cf.scheduler.center.rocketmq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindai.cf.scheduler.center.rocketmq.producer.RocketProducerService;

@Service
public class BusinessService {

	@Autowired
	RocketProducerService senderService;
	
	public void sendTest(){
//		senderService.oneWaySender(RocketQueues.Test, "lilieleieiieietestsetsetset");
	}
}
