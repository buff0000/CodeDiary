package com.buff.mq.config;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: Buff
 * @Description:
 * @Date: Created in 2019-11-16 0:40
 */

@RestController
public class Controller {
    @Resource
    private Producer producer;

    @GetMapping("/confirm-message")
    public void confirmMessage() {
        producer.send("hello confirm message");
    }
}
