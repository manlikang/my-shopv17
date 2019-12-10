package com.fuhan.v17search.config;


import com.fuhan.commons.constant.MQConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
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
        return new Queue(MQConstant.QUEUE.SEARCH_QUEUE,true,false,false);
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
        return BindingBuilder.bind(searchQueue()).to(topicExchange()).with(MQConstant.ROUNTING.PRODUCT_KEY);
    }

}
