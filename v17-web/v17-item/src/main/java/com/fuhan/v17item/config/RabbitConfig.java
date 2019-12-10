package com.fuhan.v17item.config;


import com.fuhan.commons.constant.MQConstant;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.nio.charset.StandardCharsets;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/5
 */
@Slf4j
@Configuration
public class RabbitConfig {


    @Bean
    public Queue Queue(){
        return new Queue(MQConstant.QUEUE.ITEM_QUEUE,true,false,false);
    }
    @Bean
    public TopicExchange topicExchange(){
        return  new TopicExchange(MQConstant.EXCHANGE.PRODUCT_EXCHANGE,true,false);
    }


    /**
     * 连接工厂
     */
    @Autowired
    private ConnectionFactory connectionFactory;

    /**
     * 必须是prototype类型
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        return  new RabbitTemplate(connectionFactory);

    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(Queue()).to(topicExchange()).with(MQConstant.ROUNTING.PRODUCT_KEY);
    }





}
