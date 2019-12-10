package com.fuhan.v17shortmessageservice.config;


import com.fuhan.commons.constant.MQConstant;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/5
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue searchQueue(){
        return new Queue(MQConstant.QUEUE.MESSAGE_QUEUE,true,false,false);
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


}
