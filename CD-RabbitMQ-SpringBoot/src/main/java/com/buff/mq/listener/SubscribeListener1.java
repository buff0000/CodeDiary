package com.buff.mq.listener;

import com.buff.mq.springboot.pojo.Mail;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class SubscribeListener1 {
//	@RabbitListener(queues = "queue1")
//	public void subscribe(Mail mail) throws IOException {
//		System.out.println("订阅者1收到消息"+mail.toString());
//	}

	@RabbitListener(queues = "queue1")
	public void subscribe(Mail mail, Channel channel, Message message) throws Exception {
		System.out.println("订阅者1收到消息"+mail.toString());
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
}
