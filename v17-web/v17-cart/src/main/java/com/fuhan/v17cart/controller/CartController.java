package com.fuhan.v17cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fuhan.api.ICartService;
import com.fuhan.commons.pojo.ResultBean;
import com.fuhan.v17cart.myTask.AddToCartTask;
import com.fuhan.v17cart.myTask.DelFromCartTask;
import com.fuhan.vo.CartItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/15
 */
@Controller
@RequestMapping("cart")
public class CartController {

    @Reference
    private ICartService cartService;
    @Autowired
    private ThreadPoolExecutor poolExecutor;

    @RequestMapping("addToCart/{productId}/{count}")
    @ResponseBody()
    public ResultBean<String> addToCart(@CookieValue(name = "cart_token",required = false) String cartToken,
            @PathVariable Integer productId, @PathVariable Integer count,
                                        HttpServletRequest request,
                                        HttpServletResponse response){
        String userToken = (String) request.getAttribute("user_token");
        if(userToken != null){
            //已登录
            cartService.add(userToken,productId,count);
            poolExecutor.submit(new AddToCartTask(Integer.parseInt(userToken),productId,count,cartService));
            return new ResultBean<>("200","您已登录");
        }
        //未登录
        System.out.println(cartToken);
        if(cartToken == null){
            cartToken= UUID.randomUUID().toString();
            Cookie cookie = new Cookie("cart_token",cartToken);
            cookie.setMaxAge(15*24*60*60);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
        }
        cartService.add(cartToken,productId,count);
        return new ResultBean<>("200","您未登录");

    }


    @RequestMapping("delFromCart/{productId}")
    @ResponseBody
    public ResultBean<String> delFromCart(@PathVariable Integer productId,
                                          @CookieValue(name = "cart_token",required = false) String cartToken,
                                          HttpServletRequest request){
        String userToken = (String) request.getAttribute("user_token");
        if(userToken != null){

            cartService.del(userToken,productId);
            poolExecutor.submit(new DelFromCartTask(Integer.parseInt(userToken),productId,cartService));
            return new ResultBean<>("200","您已登录");
        }
        cartService.del(cartToken,productId);
        return new ResultBean<>("200","您未登录");
    }

    @RequestMapping("updateFromCart/{productId}/{count}")
    @ResponseBody
    public ResultBean<String> updateFromCart(@PathVariable Integer productId,
                                             @PathVariable Integer count,
                                             @CookieValue(name = "cart_token",required = false) String cartToken,
                                             HttpServletRequest request){
        String userToken = (String) request.getAttribute("user_token");
        if(userToken != null){
            cartService.update(userToken,productId,count);
            poolExecutor.submit(new AddToCartTask(Integer.parseInt(userToken),productId,count,cartService));
            return new ResultBean<>("200","您已登录");
        }
        cartService.update(cartToken,productId,count);
        return new ResultBean<>("200","您未登录");

    }
    @RequestMapping("queryCart")
    @ResponseBody
    public Set<CartItemVO> queryCart(@CookieValue(name = "cart_token",required = false) String cartToken,
                                      HttpServletRequest request){
        String userToken = (String) request.getAttribute("user_token");
        List<CartItemVO> list=new ArrayList<>();
        if(userToken != null){
            list = cartService.query(userToken);
            if(list == null || list.size()==0){
                list = cartService.getCartItemVOList(Integer.parseInt(userToken));
            }
        }
        return new TreeSet<>(list);
    }

}
