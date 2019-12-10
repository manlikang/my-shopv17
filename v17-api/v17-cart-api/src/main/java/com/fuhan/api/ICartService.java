package com.fuhan.api;

import com.fuhan.commons.base.IBaseService;
import com.fuhan.commons.pojo.ResultBean;
import com.fuhan.entity.Cart;
import com.fuhan.vo.CartItem;
import com.fuhan.vo.CartItemVO;

import java.util.List;
import java.util.Set;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/14
 */
public interface ICartService extends IBaseService<Cart> {

    ResultBean<Boolean> add(String token, Integer productId, Integer count);

    ResultBean<Boolean> del(String token,Integer productId);

    ResultBean<Boolean> update(String token,Integer productId,Integer count);

    List<CartItemVO> query(String token);


    ResultBean<Boolean> cartToUser(String token,Integer userId);

    List<CartItemVO> getCartItemVOList(int parseInt);


    List<CartItem> getCartItemList(Integer userId);

    int batchAdd(List<Cart> cartList);

    int delProductByProductId(Integer userId, Integer productId);

    ResultBean<String> addToRedis(Integer userId,List<Cart> list);
}
