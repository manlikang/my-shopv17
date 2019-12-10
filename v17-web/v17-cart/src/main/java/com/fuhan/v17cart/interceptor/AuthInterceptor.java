package com.fuhan.v17cart.interceptor;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.fuhan.commons.pojo.ResultBean;
import com.fuhan.interfaces.IUserService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/10/21
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Reference
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if("user_token".equals(cookie.getName())){
                    ResultBean<String> resultBean = userService.checkLoginStatus(cookie.getValue());
                    request.setAttribute("user_token",resultBean.getData());
                    System.out.println(resultBean.getData());
                    return true;
                }
            }
        }
        System.out.println("未登录");
        return true;
    }
}
