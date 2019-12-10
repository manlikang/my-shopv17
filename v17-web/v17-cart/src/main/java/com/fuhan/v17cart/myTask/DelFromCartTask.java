package com.fuhan.v17cart.myTask;

import com.fuhan.api.ICartService;
import com.fuhan.commons.pojo.ResultBean;

import java.util.concurrent.Callable;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/16
 */
public class DelFromCartTask implements Callable<ResultBean<String>> {

    private Integer userId;
    private Integer productId;
    private ICartService cartService;
    public DelFromCartTask(Integer userId,Integer productId,ICartService cartService){
        this.userId = userId;
        this.productId = productId;
        this.cartService = cartService;
    }

    @Override
    public ResultBean<String> call() throws Exception {

        int i =cartService.delProductByProductId(userId,productId);
        return null;
    }
}
