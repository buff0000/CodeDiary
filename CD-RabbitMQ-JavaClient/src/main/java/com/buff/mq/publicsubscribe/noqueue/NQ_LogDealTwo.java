package com.buff.mq.publicsubscribe.noqueue;

import com.buff.mq.ConstantMQInfo;
import com.buff.mq.util.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.buff.mq.ConstantMQInfo.QUEUE_NAME_4;

/**
 * @Author: Buff
 * @Description:
 * @Date: Created in 2019-11-14 23:49
 */
public class NQ_LogDealTwo {

    private static Logger logger = LoggerFactory.getLogger(NQ_LogDealTwo.class);

    public static void main(String[] args) throws IOException {
        deal();
    }

    /**
     * 和NQ_LogDealOne的方法内容一样
     * @throws IOException
     */
    public static void deal() throws IOException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(ConstantMQInfo.EXCHANGE_NAME_1, BuiltinExchangeType.FANOUT);
        //该方法是创建一个临时队列，一但当前连接断开，则队列被MQ删除。
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, ConstantMQInfo.EXCHANGE_NAME_1, "");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            logger.info("接收消息：" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME_4, true, deliverCallback, consumerTag -> {
        });
    }
}
