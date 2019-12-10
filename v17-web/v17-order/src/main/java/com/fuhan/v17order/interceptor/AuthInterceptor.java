package com.fuhan.v17order.interceptor;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fuhan.commons.pojo.ResultBean;
import com.fuhan.interfaces.IUserService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author huangguizhao
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Reference
    private IUserService userService;

    /**
     * 前置拦截
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前是否处于登录状态
        //如果是登录状态，则将当前用户信息保存到request中
        //登录了才可以放行
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if("user_token".equals(cookie.getName())){
                    String token = cookie.getValue();
                    ResultBean resultBean = userService.checkLoginStatus(token);
                    if("200".equals(resultBean.getStatusCode())){
                        //说明，当前用户已登录
                        System.out.println("已登录");
                        request.setAttribute("user",resultBean.getData());
                        return true;
                    }
                }
            }
        }
        System.out.println("未登录");
        //未登录，不放行，跳转到登录页面
        //如果需要实现从哪来回哪去的效果，那么此处需要传递一个url地址
        StringBuffer requestURL = request.getRequestURL();
        response.sendRedirect("http://localhost:10084/login/toLogin?referer="+requestURL);
        return false;
    }
}
