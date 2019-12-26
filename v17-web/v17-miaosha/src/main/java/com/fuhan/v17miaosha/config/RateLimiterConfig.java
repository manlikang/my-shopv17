package com.fuhan.v17miaosha.config;

import org.apache.curator.shaded.com.google.common.util.concurrent.RateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/22
 */
@Configuration
public class RateLimiterConfig {

    @Bean
    public RateLimiter getRateLimiter(){
        return RateLimiter.create(10.0);
    }
}
