package com.buff.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Buff
 * @Description:
 * @Date: Created in 2019-11-15 23:27
 */
@Configuration
public class MQConfiguration {
    /*
    声明队列
     */
    @Bean
    public Queue confirmQueue() {
        return new Queue("springboot-confirm.queue");
    }

    /*
    声明交换机
     */
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("springboot-exchange");
    }

    /*
    将队列绑定到交换机
     */
    @Bean
    public Binding bindMessage() {
        return BindingBuilder.bind(confirmQueue()).to(exchange()).with("springboot-confirm.message");
    }
}
