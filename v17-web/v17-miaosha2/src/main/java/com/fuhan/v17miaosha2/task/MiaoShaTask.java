package com.fuhan.v17miaosha2.task;

import com.fuhan.v17miaosha2.entity.MiaoshaProduct;
import com.fuhan.v17miaosha2.mapper.MiaoshaProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/20
 */
@Component
public class MiaoShaTask {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private MiaoshaProductMapper productMapper;

    @Scheduled(cron = "0/5 * * * * ?")
    public void scan(){
        List<MiaoshaProduct> list =  productMapper.getCanMiaoShaProduct();
        if(list != null && !list.isEmpty()){
            for (MiaoshaProduct miaoshaProduct : list) {
                Long[] ids = new Long[miaoshaProduct.getCount()];
                for (Integer i = 0; i < miaoshaProduct.getCount(); i++) {
                    ids[i] = miaoshaProduct.getProductId();
                }
                redisTemplate.opsForList().leftPushAll("miaosha:product:" + miaoshaProduct.getId(),ids);
                redisTemplate.opsForSet().add("miaoshaing",miaoshaProduct.getId());
                miaoshaProduct.setStatus("1");
                productMapper.updateByPrimaryKeySelective(miaoshaProduct);
                System.out.println(miaoshaProduct.getId()+"已添加至redis中，数量为"+miaoshaProduct.getCount());
            }
        }
    }

    @Scheduled(cron = "0/5 * * * * ?")
    public void clean(){
        List<MiaoshaProduct> list =productMapper.getStopMiaoShaProduct();
        if(list != null && !list.isEmpty()){
            for (MiaoshaProduct miaoshaProduct : list) {
                redisTemplate.delete("miaosha:product:" + miaoshaProduct.getId());
                redisTemplate.delete("miaoshaing"+miaoshaProduct.getId());
                miaoshaProduct.setStatus("2");
                productMapper.updateByPrimaryKeySelective(miaoshaProduct);
            }

            System.out.println("清理完成");
        }
    }
}
