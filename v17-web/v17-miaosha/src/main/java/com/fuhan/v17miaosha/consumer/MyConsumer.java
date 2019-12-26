package com.fuhan.v17miaosha.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.shaded.com.google.common.util.concurrent.RateLimiter;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/6
 */
@Slf4j
@Component
public class MyConsumer {

    @Autowired
    private RateLimiter rateLimiter;
    @RabbitHandler
    @RabbitListener(queues = "limit-queue")
    public void process(String rate, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag)  {
        rateLimiter.setRate(Double.parseDouble(rate));
        try {
            channel.basicAck(tag,false);
            System.out.println("消息确认发送成功");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @RabbitHandler
    @RabbitListener(queues = "dlx-queue")
    public void process2(String msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag){
        System.out.println(msg);
        try {
            channel.basicAck(tag,false);
            System.out.println("消息确认发送成功");
        } catch (IOException e) {
            log.error(e.getMessage());
        }

    }




}
