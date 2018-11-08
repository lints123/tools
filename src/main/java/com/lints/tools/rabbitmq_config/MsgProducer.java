package com.lints.tools.rabbitmq_config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * RabbitMQ 消息生产者
 */
@Component
public class MsgProducer implements RabbitTemplate.ConfirmCallback {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 由于rabbitTemplate的scope属性设置为ConfigurableBeanFactory.SCOPE_PROTOTYPE，所以不能自动注入
     */
    private RabbitTemplate rabbitTemplate;

    /**
     * 构造方法注入rabbitTemplate
     */
    @Autowired
    public MsgProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        // rabbitTemplate如果为单例的话，那回调就是最后设置的内容
        rabbitTemplate.setConfirmCallback(this);
    }


    // 发送消息
    public void sendMsg(String content){
        // 定义回调传递的数据
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        // 把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_A,RabbitConfig.ROUTINGKEY_A,content,correlationData);
    }

    // 定义一个生产者多个消费者
    public void sendMsgTo(String content){
        // 把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列B
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_B,RabbitConfig.ROUTINGKEY_B,content);
    }


    // 接收回调
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        // 回调id
        //logger.debug(correlationData.getId());
        if(b){
            //logger.info("信息回调 >>> 消息成功消费");
        }else{
            logger.info("信息回调 >>> 消息消费失败："+s);
        }
    }
}
