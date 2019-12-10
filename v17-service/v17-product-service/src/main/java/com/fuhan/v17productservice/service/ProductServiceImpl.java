package com.fuhan.v17productservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.fuhan.commons.base.BaseServiceImpl;
import com.fuhan.commons.base.IBaseDao;
import com.fuhan.entity.Product;
import com.fuhan.entity.ProductDesc;
import com.fuhan.entity.ProductType;
import com.fuhan.interfaces.IProductService;
import com.fuhan.mapper.ProductDescMapper;
import com.fuhan.mapper.ProductMapper;
import com.fuhan.mapper.ProductTypeMapper;
import com.fuhan.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/10/28
 */
@Service
public class ProductServiceImpl extends BaseServiceImpl<Product> implements IProductService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductDescMapper productDescMapper;
    @Autowired
    private ProductTypeMapper productTypeMapper;

    @Override
    public IBaseDao<Product> getDao() {
        return productMapper;
    }


    @Override
    @Transactional
    public Integer add(ProductVO vo) {
        Integer typeId = vo.getProduct().getMyTypeId();
        ProductType productType = productTypeMapper.selectByPrimaryKey(typeId);
        vo.getProduct().setTypeName(productType.getName());
        productMapper.insertSelective(vo.getProduct());
        ProductDesc productDesc = new ProductDesc();
        productDesc.setProductId(vo.getProduct().getId());
        productDesc.setProductDesc(vo.getProductDesc());
        productDescMapper.insertSelective(productDesc);
        return vo.getProduct().getId();
    }

    @Override
    public ProductVO selectVOById(int id) {
        Product product = productMapper.selectByPrimaryKey(id);
        ProductDesc productDesc = productDescMapper.selectByProductId(id);
        return new ProductVO(product, productDesc.getProductDesc());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateByVO(ProductVO vo) {
        ProductType productType = productTypeMapper.selectByPrimaryKey(vo.getProduct().getMyTypeId());
        System.out.println(vo.getProduct().getId());
        vo.getProduct().setTypeName(productType.getName());
        System.out.println(vo.getProduct().getTypeName());
        int i = productMapper.updateByPrimaryKeySelective(vo.getProduct());
        int j = productDescMapper.updateByProductId(vo.getProduct().getId(), vo.getProductDesc());
        if (i != 0 && j != 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean delProductAndDescVById(int id) {
        int i = productMapper.falseDeleteById(id);
        if (i != 0) {
            return true;
        }

        return false;
    }

    @Override
    public boolean batchDelById(List<Integer> ids) {

        int i = productMapper.falseBatchDelById(ids);
        if (i != 0) {
            return true;
        }

        return false;
    }

    @Override
    public List<ProductType> getParentTypeList() {
        return productTypeMapper.getParentTypeList();
    }

    @Override
    public List<ProductType> getSmaillList(int id) {
        return productTypeMapper.getSmaillList(id);
    }

    @Override
    public List<Product> getListByIds(List<Integer> ids) {
        return productMapper.getListByIds(ids);
    }


}
