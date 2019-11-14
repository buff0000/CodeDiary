package com.buff.mq.workqueues;

import com.buff.mq.helloworld.Recv;
import com.buff.mq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.buff.mq.ConstantMQInfo.QUEUE_NAME_2;

/**
 * @Author: Buff
 * @Description:
 * @Date: Created in 2019-11-14 16:52
 */
public class Worker {
    private static Logger logger = LoggerFactory.getLogger(Recv.class);

    public static void main(String[] args) throws Exception {
       receive();
    }

    public static void receive() throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        //durable 是否持久化
        channel.queueDeclare(QUEUE_NAME_2, true, false, false, null);
        // 每个队列同时最多可以处理几个消息，需要注意的是一旦消息进入队列，Qos则不能修改了，即使再启动一个worker增大Qos，也不会起作用。
        channel.basicQos(3);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            logger.info("接收消息：" + message + "'");
            try {
                doWork(message);
            } finally {
                //发送ACK,由于basicConsume方法中指定的是false，所以要在接收完消息后发送ACK
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };

        channel.basicConsume(QUEUE_NAME_2, false, deliverCallback, consumerTag -> {
        });
    }

    private static void doWork(String task) {
        logger.info("开始处理消息"+task);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException _ignored) {
            Thread.currentThread().interrupt();
        }
        logger.info("完成处理消息"+task);
    }
}
