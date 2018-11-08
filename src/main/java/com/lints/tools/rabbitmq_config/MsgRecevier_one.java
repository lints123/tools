package com.lints.tools.rabbitmq_config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


/**
* @Description: 定义一个生产者，多个消费者
* @Author: lints
* @Date: 2018-11-08
*/
@Component
@RabbitListener(queues = RabbitConfig.QUEUE_B)
public class MsgRecevier_one {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitHandler
    public void process(String content){
        logger.info("消费者One >>> 接收消息队列里面的消息："+content);
    }


}
