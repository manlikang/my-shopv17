package com.fuhan.v17miaosha.mapper;

import com.fuhan.v17miaosha.entity.MiaoshaProduct;import java.util.List;

public interface MiaoshaProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MiaoshaProduct record);

    int insertSelective(MiaoshaProduct record);

    MiaoshaProduct selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MiaoshaProduct record);

    int updateByPrimaryKey(MiaoshaProduct record);

    List<MiaoshaProduct> getCanMiaoShaProduct();
}