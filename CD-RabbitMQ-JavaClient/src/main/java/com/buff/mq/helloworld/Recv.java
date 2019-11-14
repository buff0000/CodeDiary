package com.buff.mq.helloworld;

import com.buff.mq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.buff.mq.ConstantMQInfo.QUEUE_NAME_1;

/**
 * @Author: Buff
 * @Description:
 * @Date: Created in 2019-11-14 15:03
 */
public class Recv {
    private static Logger logger = LoggerFactory.getLogger(Recv.class);

    public static void main(String[] args) throws Exception {
        receive();
    }

    public static void receive() throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME_1, false, false, false, null);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            logger.info("接收消息：" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME_1, true, deliverCallback, consumerTag -> {
        });
    }
}
