package com.mindai;

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
public class HelloRabbitMqTests {

	@Autowired
	private SenderService senderService;
	
	@Autowired
	private Sender sender;
	@Test
	public void hello() throws Exception {
		try {

			Random random = new Random();
			int i=0;
			while (i<100) {
				senderService.sendBar2Rabbitmq(new Bar(random.nextInt()));
				senderService.sendFoo2Rabbitmq(new Foo(UUID.randomUUID().toString()));
				i++;
				sender.sendMsg(RabbitQueues.SCHEDULER_PAYGATEWAY_WITHHOLDING.getCode(), "574646546546446");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
