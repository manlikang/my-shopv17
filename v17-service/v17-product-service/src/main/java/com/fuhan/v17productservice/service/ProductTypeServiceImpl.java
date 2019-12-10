package com.fuhan.v17productservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.fuhan.commons.base.BaseServiceImpl;
import com.fuhan.commons.base.IBaseDao;
import com.fuhan.entity.ProductType;
import com.fuhan.interfaces.IProductTypeService;
import com.fuhan.mapper.ProductTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.List;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/2
 */
@Service
public class ProductTypeServiceImpl extends BaseServiceImpl<ProductType> implements IProductTypeService {
    @Autowired
    private ProductTypeMapper productTypeMapper;
    @Autowired
    private RedisTemplate<String,List<ProductType>> redisTemplate;

    @Override
    public IBaseDao<ProductType> getDao() {
        return productTypeMapper;
    }

    @Override
    public List<ProductType> getList() {
        List<ProductType> list= redisTemplate.opsForValue().get("productType-list");
        if(list == null || list.size()==0){
            list = super.getList();
            redisTemplate.opsForValue().set("productType-list",list);
        }
        return list;
    }
}
