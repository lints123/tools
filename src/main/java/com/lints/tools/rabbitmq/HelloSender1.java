package com.lints.tools.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class HelloSender1 {

    @Autowired
    private AmqpTemplate rabbitTemplate;
    // 最简单的单个生产者和消费者
    public void send() {
        for(int i =0;i<10;i++) {
            String sendMsg = "hello1 " + new Date();
            System.out.println("Sender1 : " + sendMsg+"____"+i);
            this.rabbitTemplate.convertAndSend("helloQueue", sendMsg);
        }
    }

}
