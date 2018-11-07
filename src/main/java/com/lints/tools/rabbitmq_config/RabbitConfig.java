package com.lints.tools.rabbitmq_config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


/**
 * 定义RabbitMQ的配置文件
 * 定义队列，路由，mq模板
 */

@Configuration
public class RabbitConfig {

    // 定义日志记录
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int  port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    // 定义路由的名称
    public static final String EXCHANGE_A = "my_mq_exchange_A";
    public static final String EXCHANGE_B = "my_mq_exchange_B";
    public static final String EXCHANGE_C = "my_mq_exchange_C";

    // 定义队列的名称
    public static final String QUEUE_A = "QUEUE_A";
    public static final String QUEUE_B = "QUEUE_B";
    public static final String QUEUE_C = "QUEUE_C";

    // 定义routing key的名称，路由关键字,exchange根据这个关键字进行消息投递。
    public static final String ROUTINGKEY_A = "spring_boot_routing_key_A";
    public static final String ROUTINGKEY_B = "spring_boot_routing_key_B";
    public static final String ROUTINGKEY_C = "spring_boot_routing_key_C";


    // 创建rabbitMQ 连接工厂
    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host,port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        // 指定RabbitMQ服务器的虚拟主机
        connectionFactory.setVirtualHost("/");
        // 设置为true，才能进行消息的回调。
        /*connectionFactory.setPublisherConfirms(true);*/
        return connectionFactory;
    }


    /**
     * 返回rabbit模板
     *  ConfigurableBeanFactory.SCOPE_PROTOTYPE 每次注入的时候回自动创建一个新的bean实例
     * @return template
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate template  = new RabbitTemplate(connectionFactory());
        return template ;
    }

    /**
     * 针对消费者配置
     * 1. 设置交换机类型
     * 2. 将队列绑定到交换机
     DirectExchange:    按照routingkey分发到指定队列
     FanoutExchange:    将消息分发到所有的绑定队列，无routingkey的概念
     TopicExchange:     多关键字匹配
     HeadersExchange ： 通过添加属性key-value匹配(少用)
     */
    @Bean
    public DirectExchange defaultExchange(){
        return new DirectExchange(EXCHANGE_A);
    }

    /**
     * 创建队列
     * @return
     */
    @Bean
    public Queue queueA(){
        // true表示队列持久
        return new Queue(QUEUE_A,true);
    }
    @Bean
    public Queue queueB(){
        // true表示队列持久
        return new Queue(QUEUE_B,true);
    }

    /**
     * 队列与交换器的绑定关系便是由Binding来表示的
     * @return
     */
    public Binding binding(){
        // 主要的意思是：在配置时更加快速的创建绑定关系。实例化Binding语法：
        return BindingBuilder.bind(queueA()).to(defaultExchange()).with(RabbitConfig.ROUTINGKEY_A);
    }
    // 一个交换机可以绑定多个消息队列，也就是消息通过一个交换机，可以分发到不同的队列当中去。
    @Bean
    public Binding bindingB(){
        return BindingBuilder.bind(queueB()).to(defaultExchange()).with(RabbitConfig.ROUTINGKEY_B);
    }


}
