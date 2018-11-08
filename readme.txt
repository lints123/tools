时间：2018-11-05
开发目的：新建项目Tools，主要做工具项目Demo，打算集成RabbitMQ和Kafka

1、不使用路由的情况下，指定队列直接发送请求
使用SpringBoot 提供的AmqpTemplate模板。
调用 rabbitTemplate.convertAndSend("helloQueue", sendMsg);
【表示发送一个消息到队列名字为 helloQueue的队列中，这里需要注意一个点：队列这里需要先在RabbitMQ中先创建，不然发送消息过去会报错】
使用HelloReceiver1，作为监听队列类，指定监听队列名称（@RabbitListener(queues = "helloQueue")），之后获取到队列信息（@RabbitHandler）

2018-11-07
rabbitmq：配置文件
【遇到的问题：】
单个生产者单个消费者的问题
没有使用Bean注入，导致发送消息时，队列没有与路由绑定，回调的时候一直提示消费成功，但是消费者并没有获取到数据，后查发现是少写了 Bean注解
加上，生产者和消费者正常
@Bean
public Binding bindingA(){
    // 主要的意思是：在配置时更加快速的创建绑定关系。实例化Binding语法：
    return BindingBuilder.bind(queueA()).to(defaultExchange()).with(RabbitConfig.ROUTINGKEY_A);
}
类：MsgProducer和类MsgReceiver定义的是单个生产者单个消费者的关系

定义单个生产者多个消费者
使用DirectExchange，按照指定的Routing Key指定队列
使用的类：
RabbitConfig.quereB() 创建队列、defaultExchangeB() 创建指定路由 、bindingB() 绑定队列与路由之间的关系，根据Routing Key
MsgProducer.sendMsgTo(); 作为生产者
MsgRecevier_one.process(); 作为消费者1
MsgRecevier_two.process(); 作为消费者2

参考网站：
https://blog.csdn.net/qq_38455201/article/details/80308771 Spring Boot整合RabbitMQ详细教程






