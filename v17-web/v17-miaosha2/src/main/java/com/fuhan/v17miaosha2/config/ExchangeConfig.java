package com.fuhan.v17miaosha2.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/6
 */
@Configuration
public class ExchangeConfig {

    @Bean
    public TopicExchange topicExchange(){
        return  new TopicExchange("order-change",true,false);
    }


}
