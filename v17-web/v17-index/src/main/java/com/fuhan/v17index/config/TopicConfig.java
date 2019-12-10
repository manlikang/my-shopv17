package com.fuhan.v17index.config;




import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/5
 */

@Configuration
public class TopicConfig {


    @Bean
    public TopicExchange getTopicExchange(){
        return new TopicExchange("v17-topic-exchange");
    }

    @Bean
    public Queue getQuene(){
        return new Queue("v17-topic-queue");
    }

    @Bean
    public Binding bindingExchange(Queue getQueue,TopicExchange getTopicExchange){
        return BindingBuilder.bind(getQueue).to(getTopicExchange).with("product.*");
    }

}
