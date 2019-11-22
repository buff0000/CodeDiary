package com.buff.mq.topics;

import com.buff.mq.ConstantMQInfo;
import com.buff.mq.util.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: Buff
 * @Description:
 * @Date: Created in 2019-11-15 1:15
 */
public class EmitLog {
    private static Logger logger = LoggerFactory.getLogger(EmitLog.class);

    public static void main(String[] args) throws IOException {
        List<String> list = Stream.of(
                "http_thread.Person.dao-You",
                "http_thread.Person.dao-might",
                "http_thread.Person.dao-have",
                "http_thread.Person.service-noticed",
                "http_thread.Person.service-that",
                "http_thread.Person.controller-the",
                "http_thread.Person.controller-dispatching",
                "http_thread.City.dao-still",
                "http_thread.City.service-doesn't",
                "http_thread.City.service-work",
                "http_thread.City.service-exactly",
                "http_thread.City.service-as",
                "http_thread.City.controller-we",
                "http_thread.City.controller-want.",
                "http_thread.City.controller-For",
                "http_thread.City.controller-example",
                "main_thread.Person.dao-in",
                "main_thread.Person.service-a",
                "main_thread.Person.controller-situation",
                "main_thread.Person.controller-with",
                "main_thread.Person.controller-two",
                "main_thread.Person.controller-workers,",
                "main_thread.City.dao-when",
                "main_thread.City.service-all",
                "main_thread.City.service-odd",
                "main_thread.City.service-messages",
                "main_thread.City.controller-are"
        ).collect(Collectors.toList());
        notice(list);
    }


    public static void notice(List<String> messages) throws IOException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(ConstantMQInfo.EXCHANGE_NAME_3, BuiltinExchangeType.TOPIC);

        messages.stream().forEach(
                message -> {
                    try {
                        String[] arr = message.split("-");
                        channel.basicPublish(ConstantMQInfo.EXCHANGE_NAME_3, arr[0], null, arr[1].getBytes());
                        logger.info("发送消息：" + message.toString());
                    } catch (IOException e) {
                        logger.error("", e);
                    }
                }
        );
    }
}
