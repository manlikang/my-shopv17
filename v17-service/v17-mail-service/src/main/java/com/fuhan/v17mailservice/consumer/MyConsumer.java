package com.fuhan.v17mailservice.consumer;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import com.fuhan.commons.constant.MQConstant;
import com.fuhan.commons.constant.MessageConstant;
import com.fuhan.commons.pojo.SendBean;
import com.fuhan.commons.utils.ShiroUtils;
import com.fuhana.interfaces.IMailService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;


/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/6
 */
@Slf4j
@Component
@RabbitListener(queues = MQConstant.QUEUE.MAIL_QUEUE)
public class MyConsumer {
    @Autowired
    private IMailService mailService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @RabbitHandler
    public void process(SendBean<String> bean, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag)  {
        System.out.println("收到的消息为"+bean);
        String userId = bean.getUserId();
        String email = bean.getData();
        String type = bean.getType();
        if("1".equals(type)){
            System.out.println(userId+":"+email);
            String token = ShiroUtils.getSimpleHash(userId, email);
            redisTemplate.opsForValue().set(userId,email,30,TimeUnit.MINUTES);
            String content =MessageConstant.MESSAGE_TEMPLATE_1+userId+MessageConstant.MESSAGE_TEMPLATE_2+token+MessageConstant.MESSAGE_TEMPLATE_3;
            mailService.sendHtmlMail(email,"激活邮件", content);
        }

        try {
            channel.basicAck(tag,false);
            System.out.println("消息确认发送成功");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }



}
