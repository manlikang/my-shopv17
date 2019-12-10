package com.fuhan.v17miaosha;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;
import java.util.UUID;

@SpringBootTest
public class V17MiaoshaApplicationTests {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Test
    void contextLoads() {
        Set<Object> members = redisTemplate.opsForSet().members("activeId:1");
        int i = 1;
        for (Object member : members) {
            System.out.println("第"+i+"个用户Id为"+member);
            i++;
        }
    }

    @Test
    public void sendTest(){
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());

        rabbitTemplate.convertAndSend("", "ttl-queue", "卧槽你吗啊", correlationId);
    }


}
