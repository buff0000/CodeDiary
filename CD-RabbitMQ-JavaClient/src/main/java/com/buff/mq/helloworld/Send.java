package com.buff.mq.helloworld;

import com.buff.mq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Stream;

import static com.buff.mq.ConstantMQInfo.QUEUE_NAME_1;
import static java.util.stream.Collectors.toList;

/**
 * @Author: Buff
 * @Description:
 * @Date: Created in 2019-11-14 11:16
 */
public class Send {
    private static Logger logger = LoggerFactory.getLogger(Send.class);

    public static void main(String[] args) throws Exception {
        List<String> list = Stream.of("hello world!","hello Beijin!","hello Kitty!").collect(toList());
        sendMessage(list);
    }

    public static void sendMessage(List<String> messages) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME_1, false, false, false, null);

        messages.stream().forEach(
                message -> {
                    try {
                        channel.basicPublish("", QUEUE_NAME_1, null, message.getBytes());
                        logger.info("发送消息：" + message.toString());
                    } catch (Exception e) {
                        logger.error("", e);
                    }
                }
        );

        //如果不关闭资源，则线程会阻塞
        channel.close();
        connection.close();
    }
}
