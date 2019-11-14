package com.buff.mq.workqueues;

import com.buff.mq.helloworld.Recv;
import com.buff.mq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.buff.mq.ConstantMQInfo.QUEUE_NAME_1;
import static com.buff.mq.ConstantMQInfo.QUEUE_NAME_2;

/**
 * @Author: Buff
 * @Description:
 * @Date: Created in 2019-11-14 17:19
 */
public class Task {
    private static Logger logger = LoggerFactory.getLogger(Task.class);

    public static void main(String[] args) throws IOException {
        List<String> list = Stream.of("You", "might", "have", "noticed", "that", "the", "dispatching", "still", "doesn't", "work", "exactly", "as", "we", "want.", "For", "example", "in", "a", "situation", "with", "two", "workers,", "when", "all", "odd", "messages", "are", "heavy", "and", "even", "messages", "are", "light")
                .collect(Collectors.toList());
        send(list);
    }

    public static void send(List<String> messages) throws IOException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME_2, true, false, false, null);

        messages.stream().forEach(
                message -> {
                    try {
                        channel.basicPublish("", QUEUE_NAME_2, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
                        logger.info("发送消息：" + message.toString());
                    } catch (Exception e) {
                        logger.error("", e);
                    }
                }
        );
    }
}
