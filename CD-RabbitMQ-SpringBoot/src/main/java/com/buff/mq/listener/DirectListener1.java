package com.buff.mq.listener;

import com.buff.mq.springboot.pojo.Mail;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DirectListener1 {
	@RabbitListener(queues = "directqueue1")
	public void displayMail(Mail mail) throws Exception {
		System.out.println("directqueue1队列监听器1号收到消息"+mail.toString());
	}
}
