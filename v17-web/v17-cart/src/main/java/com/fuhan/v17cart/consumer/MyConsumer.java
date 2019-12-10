package com.fuhan.v17cart.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fuhan.api.ICartService;
import com.fuhan.commons.constant.MQConstant;
import com.fuhan.commons.pojo.SendBean;

import com.fuhan.entity.Cart;
import com.fuhan.vo.CartItem;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/6
 */
@Slf4j
@Component
@RabbitListener(queues = MQConstant.QUEUE.CART_MERGE_QUEUE)
public class MyConsumer {
    @Reference
    private ICartService cartService;

    @RabbitHandler
    public void process(SendBean<String> bean, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag)  {
        System.out.println("收到的消息为"+bean);
        Integer userId = Integer.valueOf(bean.getUserId());
        List<CartItem> list =  cartService.getCartItemList(userId);
        if(list!=null) {
            List<Cart> cartList = new ArrayList<>(list.size());
            for (CartItem cartItem : list) {
                Cart cart = new Cart();
                cart.setUserid(userId);
                cart.setProductid(cartItem.getProductId());
                cart.setCount(cartItem.getCount());
                cart.setUpdatedate(cartItem.getUpdateTime());
                cartList.add(cart);
            }
            int i = cartService.batchAdd(cartList);
        }else{
            List<Cart> list1 = cartService.getList();
            if(list1 != null){
                cartService.addToRedis(userId,list1);
            }
        }
        try {
            channel.basicAck(tag,false);
            System.out.println("消息确认发送成功");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }



}
