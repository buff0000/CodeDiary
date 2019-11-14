package com.buff.mq.util;

import com.buff.mq.ConstantMQInfo;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: Buff
 * @Description:
 * @Date: Created in 2019-11-14 11:16
 */
public class ConnectionUtil {
    private static Logger logger = LoggerFactory.getLogger(ConnectionUtil.class);

    public static Connection getConnection() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ConstantMQInfo.MQ_HOST);
        factory.setPort(ConstantMQInfo.MQ_PORT);
        factory.setVirtualHost(ConstantMQInfo.MQ_VHOST);
        factory.setUsername(ConstantMQInfo.MQ_USERNAME);
        factory.setPassword(ConstantMQInfo.MQ_PASSWORD);
        Connection connection;
        try {
            connection = factory.newConnection();
        } catch (IOException | TimeoutException e) {
            logger.error("", e);
            return null;
        }
        return connection;
    }
}
