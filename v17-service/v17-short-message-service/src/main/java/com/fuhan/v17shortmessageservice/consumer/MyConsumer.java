package com.fuhan.v17shortmessageservice.consumer;

import com.fuhan.commons.constant.MQConstant;
import com.fuhan.commons.constant.MessageConstant;
import com.fuhan.commons.pojo.SendBean;
import com.fuhan.commons.utils.CodeUtils;
import com.fuhan.interfaces.IShortMessageService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/6
 */
@Slf4j
@Component
@RabbitListener(queues = MQConstant.QUEUE.MESSAGE_QUEUE)
public class MyConsumer {
    @Autowired
    private IShortMessageService shortMessageService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @RabbitHandler
    public void process(SendBean<String> bean, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag)  {
        System.out.println("收到的消息为"+bean);
        String code = new CodeUtils().getMessageCode();
        System.out.println(code);
        if("1".equals(bean.getType())){
            redisTemplate.opsForValue().set(bean.getData(),code,5,TimeUnit.MINUTES);
            shortMessageService.sendCode(bean.getData(), MessageConstant.MessageTemplateCode_CODE,code);
        }

        try {
            channel.basicAck(tag,false);
            System.out.println("消息确认发送成功");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }



}
