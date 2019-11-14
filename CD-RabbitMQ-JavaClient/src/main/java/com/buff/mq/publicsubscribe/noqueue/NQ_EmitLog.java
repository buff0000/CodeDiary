package com.buff.mq.publicsubscribe.noqueue;

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
 * @Date: Created in 2019-11-14 23:39
 */
public class NQ_EmitLog {

    private static Logger logger = LoggerFactory.getLogger(NQ_EmitLog.class);

    public static void main(String[] args) throws IOException {
        List<String> list = Stream.of("You", "might", "have", "noticed", "that", "the", "dispatching", "still", "doesn't", "work", "exactly", "as", "we", "want.", "For", "example", "in", "a", "situation", "with", "two", "workers,", "when", "all", "odd", "messages", "are", "heavy", "and", "even", "messages", "are", "light")
                .collect(Collectors.toList());
        emit(list);
    }


    public static void emit(List<String> messages) throws IOException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(ConstantMQInfo.EXCHANGE_NAME_1, BuiltinExchangeType.FANOUT);

        messages.stream().forEach(
                message->{
                    try {
                        channel.basicPublish(ConstantMQInfo.EXCHANGE_NAME_1, "", null, message.getBytes());
                        logger.info("发送消息：" + message.toString());
                    } catch (IOException e) {
                        logger.error("",e);
                    }
                }
        );
    }

}
