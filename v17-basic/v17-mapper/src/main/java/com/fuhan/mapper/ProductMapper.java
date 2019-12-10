package com.fuhan.mapper;

import com.fuhan.commons.base.IBaseDao;
import com.fuhan.entity.Product;import com.fuhan.vo.ProductSolrVO;import java.util.List;

public interface ProductMapper extends IBaseDao<Product> {

    int falseDeleteById(int id);

    int falseBatchDelById(List<Integer> ids);

    List<ProductSolrVO> getSomeList();

    ProductSolrVO getVOById(Integer id);

    List<Product> getListByIds(List<Integer> ids);
}