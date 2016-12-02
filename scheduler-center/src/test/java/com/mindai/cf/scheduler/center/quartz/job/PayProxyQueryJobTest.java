package com.mindai.cf.scheduler.center.quartz.job;

import java.util.Random;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mindai.cf.scheduler.center.Bootstrap;
import com.mindai.cf.scheduler.center.enums.RabbitQueues;
import com.mindai.cf.scheduler.center.rabbit.model.Bar;
import com.mindai.cf.scheduler.center.rabbit.model.Foo;
import com.mindai.cf.scheduler.center.rabbit.sender.Sender;
import com.mindai.cf.scheduler.center.rabbit.sender.SenderService;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Bootstrap.class)
public class PayProxyQueryJobTest {

	@Autowired
	private PayProxyQueryJob payProxyQueryJob;
	
	@Test
	public void hello() throws Exception {
		
		payProxyQueryJob.executeInternal(null);

	}

}
