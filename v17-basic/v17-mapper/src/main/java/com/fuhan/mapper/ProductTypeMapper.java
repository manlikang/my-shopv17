package com.fuhan.mapper;

import com.fuhan.commons.base.IBaseDao;
import com.fuhan.entity.ProductType;

import java.util.List;

public interface ProductTypeMapper extends IBaseDao<ProductType> {

    List<ProductType> getParentTypeList();

    List<ProductType> getSmaillList(int id);

    ProductType selectByProductId(int id);


}