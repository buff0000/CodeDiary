package com.buff.mq.service.impl;

import com.buff.mq.springboot.pojo.Mail;
import com.buff.mq.service.Producer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("producer")
public class ProducerImpl implements Producer {
	@Autowired
    RabbitTemplate rabbitTemplate;
	public void sendMail(String queue, Mail mail) {
		rabbitTemplate.setQueue(queue);
		rabbitTemplate.convertAndSend(queue,mail);
	}

}
