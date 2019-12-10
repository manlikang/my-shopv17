package com.fuhan.mapper;

import com.fuhan.commons.base.IBaseDao;
import com.fuhan.entity.Cart;
import com.fuhan.vo.CartItemVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper extends IBaseDao<Cart> {
    List<CartItemVO> getCartItemVOList(Integer userId);

    int batchAdd(List<Cart> cartList);

    int delProductByProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);
}