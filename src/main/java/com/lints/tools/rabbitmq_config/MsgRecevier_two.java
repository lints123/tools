package com.lints.tools.rabbitmq_config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.stereotype.Component;

/**
* @Description: 定义一个生产者，多个消费者的情况
* @Author: lints
* @Date: 2018-11-08
*/
@Component
@RabbitListener(queues = RabbitConfig.QUEUE_B)
public class MsgRecevier_two {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitHandler
    public void process(String context){

        logger.info("消费者Two  >>>  接收消息队列里面的消息："+context);

    }

}
