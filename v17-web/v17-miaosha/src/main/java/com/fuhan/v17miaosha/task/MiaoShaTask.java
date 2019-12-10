package com.fuhan.v17miaosha.task;

import com.fuhan.v17miaosha.entity.MiaoshaProduct;
import com.fuhan.v17miaosha.mapper.MiaoshaProductMapper;
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
                StringBuilder key = new StringBuilder("miaosha:product:").append(miaoshaProduct.getId());
                for (Integer i = 0; i < miaoshaProduct.getCount(); i++) {
                    ids[i] = miaoshaProduct.getProductId();
                }
                redisTemplate.opsForList().leftPushAll(key.toString(),ids);
                miaoshaProduct.setStatus("1");
                productMapper.updateByPrimaryKeySelective(miaoshaProduct);
                System.out.println(miaoshaProduct.getId()+"已添加至redis中，数量为"+miaoshaProduct.getCount());
            }
        }
    }
}
