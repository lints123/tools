package com.lints.tools.rabbitmq_config.controller;

import com.lints.tools.rabbitmq_config.MsgProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbitConfig")
public class RabbitController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MsgProducer msgProducer;

    @PostMapping("/sendMsg")
    public void sendMag(){
        // 发送消息
        logger.info("Controller >>> 调用生产者生成消息");
            msgProducer.sendMsg("这是我发送的第一个消息");
    }


    @PostMapping("/sendMsgTo")
    public void sendMsgTo(){
        // 发送消息
        logger.info("Controller >>> 调用生产者生成消息");
        for (int i = 0;i<10;i++) {
            msgProducer.sendMsgTo("这是我发送的第"+i+"个消息");
        }
    }
}
