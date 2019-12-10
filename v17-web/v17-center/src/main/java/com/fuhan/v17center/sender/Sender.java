package com.fuhan.v17center.sender;

import com.fuhan.commons.constant.MQConstant;
import com.fuhan.commons.pojo.MQResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.UUID;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/6
 */

@Slf4j
@Component
public class Sender implements RabbitTemplate.ConfirmCallback{

    /**由于rabbitTemplate的scope属性设置为ConfigurableBeanFactory.SCOPE_PROTOTYPE，所以不能自动注入
     *
     */
    private RabbitTemplate rabbitTemplate;
    /**
     * 构造方法注入rabbitTemplate
     */
    @Autowired
    public Sender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        /**
         *  rabbitTemplate如果为单例的话，那回调就是最后设置的内容
         */
        rabbitTemplate.setConfirmCallback(this);
    }

    public void send(String content,Integer[] ids) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        MQResultBean<Integer[]> resultBean = new MQResultBean<>();
        resultBean.setMsg(content);
        resultBean.setData(ids);
        rabbitTemplate.convertAndSend(MQConstant.EXCHANGE.PRODUCT_EXCHANGE, content, resultBean, correlationId);
    }

    public void send2(String str,String queue){
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("",queue,str,correlationId);
    }
    /**
     * 回调
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println(" 回调id:" + correlationData);
        if (ack) {
            System.out.println("消息成功消费");
        } else {
            System.out.println("消息消费失败:" + cause);
        }
    }



}
