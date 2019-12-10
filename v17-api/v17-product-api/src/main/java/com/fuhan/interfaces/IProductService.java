package com.fuhan.interfaces;

import com.fuhan.commons.base.IBaseService;
import com.fuhan.entity.Product;
import com.fuhan.entity.ProductType;
import com.fuhan.vo.ProductVO;

import java.util.List;


public interface IProductService extends IBaseService<Product> {

    Integer add(ProductVO vo);

    ProductVO selectVOById(int id);

    boolean updateByVO(ProductVO vo);

    boolean delProductAndDescVById(int id);

    boolean batchDelById(List<Integer> ids);

    List<ProductType> getParentTypeList();

    List<ProductType> getSmaillList(int id);


    List<Product> getListByIds(List<Integer> ids);
}
