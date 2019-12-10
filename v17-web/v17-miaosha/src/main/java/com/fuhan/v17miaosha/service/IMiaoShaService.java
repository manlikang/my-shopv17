package com.fuhan.v17miaosha.service;

import com.fuhan.v17miaosha.entity.MiaoshaProduct;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/20
 */
public interface IMiaoShaService  {

    int deleteByPrimaryKey(Long id);

    int insert(MiaoshaProduct record);

    int insertSelective(MiaoshaProduct record);

    MiaoshaProduct selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MiaoshaProduct record);

    int updateByPrimaryKey(MiaoshaProduct record);

    void kill(String activeId);

    void sqlKill(String activeId);

    void addToMiaoShaoQueue(Long productId, Long count);
}
