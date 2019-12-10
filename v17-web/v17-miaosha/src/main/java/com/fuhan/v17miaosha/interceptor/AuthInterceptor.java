package com.fuhan.v17miaosha.interceptor;


import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author huangguizhao
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private RateLimiter rateLimiter;
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
        //1.客户端从令牌桶获取令牌，如果500毫秒内没有获取到令牌，则降级处理
        return rateLimiter.tryAcquire(500, TimeUnit.MILLISECONDS);


    }
}
