package com.lints.tools.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 单个生产者消费者
 * 1、第一种方案：
 * 在启动类中，创建队列和路由器
 * 使用rabbitTemplate中，创建生产者和消费者。
 */

@Component
public class HelloSender1 {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    // 最简单的单个生产者和消费者
    public void send() {
        String sendMsg = "hello1 " + new Date();
        System.out.println("Sender1 : " + sendMsg);
        // 发送消息到队列中，使用默认路由器，指明队列名称
        this.rabbitTemplate.convertAndSend("helloQueue", sendMsg);
    }

}
