package com.buff.mq.service;


import com.buff.mq.springboot.pojo.Mail;

public interface Producer {
	public void sendMail(String queue, Mail mail);//向队列queue发送消息
}
