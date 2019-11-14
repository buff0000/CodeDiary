package com.buff.mq.routing;

import com.buff.mq.ConstantMQInfo;
import com.buff.mq.util.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @Author: Buff
 * @Description:
 * @Date: Created in 2019-11-15 0:58
 */
public class ReceiveSMS {

    private static Logger logger = LoggerFactory.getLogger(ReceiveSMS.class);

    public static void main(String[] args) throws IOException {
        recv();
    }
    public static void recv() throws IOException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(ConstantMQInfo.EXCHANGE_NAME_2, BuiltinExchangeType.DIRECT);
        String queueName = channel.queueDeclare().getQueue();

        channel.queueBind(queueName, ConstantMQInfo.EXCHANGE_NAME_2, "SMS");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            logger.info("接收消息：" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag ->{});
    }
}
