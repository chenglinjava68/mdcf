package com.mindai.cf.scheduler.center.rabbit.sender;

import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mindai.cf.scheduler.center.enums.SysConstants;
import com.mindai.cf.scheduler.center.rabbit.model.Bar;
import com.mindai.cf.scheduler.center.rabbit.model.Foo;

@Component
public class SenderService {

    @Autowired
    private RabbitMessagingTemplate rabbitMessagingTemplate;

    public void sendFoo2Rabbitmq(final Foo foo) {
        this.rabbitMessagingTemplate.convertAndSend(SysConstants.DIRECT_EXCHANGE, "queue.foo", foo);
    }

    public void sendBar2Rabbitmq(final Bar bar){
        this.rabbitMessagingTemplate.convertAndSend(SysConstants.DIRECT_EXCHANGE, "queue.bar", bar);
    }
}