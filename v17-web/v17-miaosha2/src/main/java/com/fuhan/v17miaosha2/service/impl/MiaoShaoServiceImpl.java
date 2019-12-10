package com.fuhan.v17miaosha2.service.impl;


import com.fuhan.v17miaosha2.entity.MiaoshaProduct;
import com.fuhan.v17miaosha2.mapper.MiaoshaProductMapper;
import com.fuhan.v17miaosha2.sender.Sender;
import com.fuhan.v17miaosha2.service.IMiaoShaService;
import com.fuhan.v17miaosha2.utils.CodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/20
 */
@Service
public class MiaoShaoServiceImpl implements IMiaoShaService {
    @Autowired
    private MiaoshaProductMapper miaoshaProductMapper;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private Sender sender;
    private static int count = 0;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return miaoshaProductMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(MiaoshaProduct record) {
        return miaoshaProductMapper.insert(record);
    }

    @Override
    public int insertSelective(MiaoshaProduct record) {
        return miaoshaProductMapper.insertSelective(record);
    }

    @Override
    public MiaoshaProduct selectByPrimaryKey(Long id) {
        return miaoshaProductMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(MiaoshaProduct record) {
        return miaoshaProductMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(MiaoshaProduct record) {
        return miaoshaProductMapper.updateByPrimaryKey(record);
    }

    @Override
    public void kill(String activeId) {
        String userId = new CodeUtils().getRandom(1,500);
        Long add = redisTemplate.opsForSet().add("activeId:" + activeId, userId);
        if(add != 0){
            Object o = redisTemplate.opsForList().leftPop("miaosha:product:" + activeId);
            if(o != null){
                String orderNo = System.currentTimeMillis()+new CodeUtils().getMessageCode()+userId;
                sender.send(orderNo,userId,activeId);
                System.out.println("Id为"+userId+"抢购成功");
            }else{
                redisTemplate.opsForSet().remove("activeId:" + activeId,userId);
                System.out.println("Id为"+userId+"抢购失败");
            }
        }else{
            System.out.println("Id为"+userId+"已经参与过抢购");
        }
    }

    @Override
    @Transactional
    public void sqlKill(String activeId) {
        //TODO
        String userId = new CodeUtils().getRandom(1,500);
        //将数据库的操作逻辑，修改为Redis的操作逻辑
        Long productId = 1L;
        //1.查询当前被秒杀的商品的库存是否充足
        MiaoshaProduct product = miaoshaProductMapper.selectByPrimaryKey(productId);
        //2.如果充足，则扣减库存
        if(product.getCount() > 0){
            product.setCount(product.getCount()-1);
            product.setUpdateTime(new Date());
            miaoshaProductMapper.updateByPrimaryKeySelective(product);
            //productMapper.update(product.getVersion())
            //update t_miaosha_product set count=count-1,version=version+1 where product_id=1 and version=oldVersion
            //TODO 记录谁抢到了 userId---productId
            //TODO 生成订单编号
            System.out.println(userId+"抢购到了.....");
        }
        //3.记录当前用户获得秒杀商品的权限
    }
}
