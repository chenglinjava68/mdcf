package com.mindai.cf.scheduler.center.rabbit.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.mindai.cf.scheduler.center.rabbit.model.Bar;
import com.mindai.cf.scheduler.center.rabbit.model.Foo;

@Component
public class ReceiverService {

    @RabbitListener(queues = "queue.foo")
    public void receiveFooQueue(Foo foo) {
        System.out.println("Received Foo<" + foo.getName() + ">");
    }

    @RabbitListener(queues = "queue.bar")
    public void receiveBarQueue(Bar bar) {
        System.out.println("Received Bar<" + bar.getAge() + ">");
    }
    
    @RabbitListener(queues = "SCHEDULER_PAYGATEWAY_WITHHOLDING")
    public void receiveWithQueue(String bar) {
        System.out.println("Received Bar<" + bar + ">");
    }
}