时间：2018-11-05
开发目的：新建项目Tools，主要做工具项目Demo，打算集成RabbitMQ和Kafka

1、不使用路由的情况下，指定队列直接发送请求
使用SpringBoot 提供的AmqpTemplate模板。
调用 rabbitTemplate.convertAndSend("helloQueue", sendMsg);
【表示发送一个消息到队列名字为 helloQueue的队列中，这里需要注意一个点：队列这里需要先在RabbitMQ中先创建，不然发送消息过去会报错】
使用HelloReceiver1，作为监听队列类，指定监听队列名称（@RabbitListener(queues = "helloQueue")），之后获取到队列信息（@RabbitHandler）

2018-11-07
rabbitmq：配置文件
https://blog.csdn.net/qq_38455201/article/details/80308771





