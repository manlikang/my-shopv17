package com.fuhan.v17productservice.config;

import com.fuhan.entity.ProductType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/11
 */
@Configuration
public class RedisConfig {
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, List<ProductType>> getRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, List<ProductType>> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }




}
