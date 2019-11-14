package com.buff.mq.topics;

import com.buff.mq.ConstantMQInfo;
import com.buff.mq.routing.ReceiveSMS;
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
 * @Date: Created in 2019-11-15 1:30
 */
public class FilterCity {
    private static Logger logger = LoggerFactory.getLogger(FilterCity.class);

    public static void main(String[] args) throws IOException {
        filter();
    }
    public static void filter() throws IOException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(ConstantMQInfo.EXCHANGE_NAME_3, BuiltinExchangeType.TOPIC);
        String queueName = channel.queueDeclare().getQueue();

//        * (star) can substitute for exactly one word.
//        # (hash) can substitute for zero or more words.

                channel.queueBind(queueName, ConstantMQInfo.EXCHANGE_NAME_3, "*.City.*");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            logger.info("过滤规则："+delivery.getEnvelope().getRoutingKey()+",接收消息：" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag ->{});
    }
}
