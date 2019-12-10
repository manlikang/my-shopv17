package com.fuhan.v17cart.config;


import com.fuhan.commons.constant.MQConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

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
        return new Queue(MQConstant.QUEUE.CART_MERGE_QUEUE,true,false,false);
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







}
