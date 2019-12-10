package com.fuhan.v17registerlogin.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fuhan.api.ICartService;
import com.fuhan.commons.constant.MQConstant;
import com.fuhan.commons.pojo.ResultBean;
import com.fuhan.commons.pojo.SendBean;
import com.fuhan.interfaces.IUserService;
import com.fuhan.v17registerlogin.sender.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/11
 */
@Controller
@RequestMapping("login")
public class LoginController {

    @Reference
    private IUserService userService;
    @Reference
    private ICartService cartService;
    @Autowired
    private Sender sender;


    @RequestMapping("toLogin")
    public String toLogin(HttpServletRequest request, Model model,String referer){
        String myReferer = request.getHeader("Referer");
        System.out.println(myReferer);
        if(myReferer == null || "".equals(myReferer)){
            myReferer=referer;
        }
        System.out.println(myReferer);
        model.addAttribute("referer",myReferer);
        return "login";
    }

    @RequestMapping("login/{username}/{password}")
    @ResponseBody
    public ResultBean<String> login(@CookieValue(name = "cart_token",required = false) String cartToken,
            @PathVariable String username, @PathVariable String password, HttpServletResponse response){
        ResultBean<Map<String,String>> resultBean = userService.login(username, password);
        if("200".equals(resultBean.getStatusCode())){
            String uuid = resultBean.getData().get("token");
            Cookie cookie = new Cookie("user_token", uuid);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(20*60*60);
            response.addCookie(cookie);
            System.out.println("加入cookie");
            if(cartToken != null){
                System.out.println("cartToken不为空");
                cartService.cartToUser(cartToken,Integer.parseInt(resultBean.getData().get("userId")));
            }
            sender.send(MQConstant.QUEUE.CART_MERGE_QUEUE,new SendBean<>("cart_merge",resultBean.getData().get("userId"),null));
            return new ResultBean<>("200","登录成功");
        }
        return new ResultBean<>("500",null);
    }

    @RequestMapping("Logout")
    @ResponseBody
    public ResultBean<String> logout(HttpServletResponse response,@CookieValue(name = "user_token",required = false) String uuid){
        Cookie cookie = new Cookie("user_token", uuid);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return new ResultBean<>("200","登出成功");
    }

    @ResponseBody
    @RequestMapping("checkLoginStatus")
    @CrossOrigin(origins = "*",allowCredentials = "true")
    public ResultBean<String> checkLoginStatus(@CookieValue(name = "user_token",required = false) String uuid){
        if(uuid!=null){
            System.out.println("user_token:"+uuid);
            return userService.checkLoginStatus(uuid);
        }
        return new ResultBean<>("500", null);
    }

}
