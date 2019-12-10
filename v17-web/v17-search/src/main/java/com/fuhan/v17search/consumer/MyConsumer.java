package com.fuhan.v17search.consumer;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fuhan.commons.constant.MQConstant;
import com.fuhan.commons.pojo.MQResultBean;
import com.fuhan.interfaces.ISearchService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/6
 */
@Slf4j
@Component
@RabbitListener(queues = MQConstant.QUEUE.SEARCH_QUEUE)
public class MyConsumer {
    private static final String ADD_FLAG="product.add";
    private static final String DEL_FLAG="product.del";
    private static final String UPDATE_FLAG="product.update";

    @Reference
    private ISearchService searchService;

    @RabbitHandler
    public void process(MQResultBean<Integer[]> resultBean,Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag)  {
        System.out.println("收到的消息为"+resultBean.toString());
        Integer[] ids = resultBean.getData();
        List<Integer> list =Arrays.asList(ids);
        String type = resultBean.getMsg();
        if(ADD_FLAG.equals(type)){
            searchService.synById(list.get(0));
            System.out.println("商品"+list.get(0)+"已添加至solr搜索数据库中");
        }else if(DEL_FLAG.equals(type)){
            searchService.batchDel(list);
            System.out.println("商品"+list+"已从solr搜索数据库中移除");
        }else if(UPDATE_FLAG.equals(type)){
            searchService.delById(list.get(0));
            searchService.synById(list.get(0));
            System.out.println("商品"+list.get(0)+"已更新至solr搜索数据库中");
        }
        try {
            channel.basicAck(tag,false);
            System.out.println("消息确认发送成功");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }



}
