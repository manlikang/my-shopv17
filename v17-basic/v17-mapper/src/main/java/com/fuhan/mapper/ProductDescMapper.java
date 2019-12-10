package com.fuhan.mapper;

import com.fuhan.commons.base.IBaseDao;
import com.fuhan.entity.ProductDesc;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDescMapper extends IBaseDao<ProductDesc> {

    ProductDesc selectByProductId(int id);

    int updateByProductId(@Param("pid") Integer id, @Param("productDesc") String productDesc);

    int deleteByPid(int id);

    int batchDelByPid(List<Integer> ids);
}