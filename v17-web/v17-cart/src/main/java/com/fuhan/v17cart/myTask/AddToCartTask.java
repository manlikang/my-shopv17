package com.fuhan.v17cart.myTask;

import com.fuhan.api.ICartService;
import com.fuhan.commons.pojo.ResultBean;
import com.fuhan.entity.Cart;
import com.fuhan.vo.CartItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/16
 */

public class AddToCartTask implements Callable<ResultBean<String>> {


    private Integer userId;
    private Integer productId;
    private Integer count;
    private ICartService cartService;

    public AddToCartTask(Integer userId,Integer productId,Integer count,ICartService cartService){
        this.userId=userId;
        this.productId=productId;
        this.count = count;
        this.cartService=cartService;
    }

    @Override
    public ResultBean<String> call() throws Exception {
        Cart cart = new Cart(null,userId,productId,new Date(),count);
        List<Cart> list = new ArrayList<>(1);
        list.add(cart);
        cartService.batchAdd(list);
        return new ResultBean<>("200","处理成功");
    }
}
