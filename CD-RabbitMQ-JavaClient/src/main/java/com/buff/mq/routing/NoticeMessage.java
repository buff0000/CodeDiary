package com.buff.mq.routing;

import com.buff.mq.ConstantMQInfo;
import com.buff.mq.publicsubscribe.noqueue.NQ_EmitLog;
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
 * @Date: Created in 2019-11-15 0:51
 */
public class NoticeMessage {
    private static Logger logger = LoggerFactory.getLogger(NoticeMessage.class);

    public static void main(String[] args) throws IOException {
        List<String> list = Stream.of("EMAIL-You", "SMS-might", "SMS-have", "EMAIL-noticed", "SMS-that", "SMS-the", "EMAIL-dispatching", "EMAIL-still", "SMS-doesn't", "SMS-work", "EMAIL-exactly", "SMS-as", "SMS-we", "EMAIL-want.", "SMS-For", "SMS-example", "SMS-in", "SMS-a", "SMS-situation", "SMS-with", "SMS-two", "SMS-workers,", "SMS-when", "SMS-all", "EMAIL-odd", "EMAIL-messages", "EMAIL-are", "SMS-heavy", "SMS-and", "SMS-even", "SMS-messages", "SMS-are", "SMS-light")
                .collect(Collectors.toList());
        notice(list);
    }


    public static void notice(List<String> messages) throws IOException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(ConstantMQInfo.EXCHANGE_NAME_2, BuiltinExchangeType.DIRECT);

        messages.stream().forEach(
                message -> {
                    try {
                        String[] arr = message.split("-");
                        channel.basicPublish(ConstantMQInfo.EXCHANGE_NAME_2, arr[0], null, arr[1].getBytes());
                        logger.info("发送消息：" + message.toString());
                    } catch (IOException e) {
                        logger.error("", e);
                    }
                }
        );
    }
}
