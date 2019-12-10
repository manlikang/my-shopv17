package com.fuhan.v17miaosha2.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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
    public Queue searchQueue(){
        return new Queue("order-queue",true,false,false);
    }
    @Bean
    public TopicExchange topicExchange(){
        return  new TopicExchange("order-change",true,false);
    }


    /**
     * 连接工厂
     */
    @Autowired
    private ConnectionFactory connectionFactory;



    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    /**
     * 必须是prototype类型
     */
    public RabbitTemplate rabbitTemplate() {
        return  new RabbitTemplate(connectionFactory);

    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(searchQueue()).to(topicExchange()).with("");
    }

}
