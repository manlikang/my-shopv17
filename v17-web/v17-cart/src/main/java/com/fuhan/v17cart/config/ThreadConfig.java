package com.fuhan.v17cart.config;

import com.alibaba.dubbo.common.utils.NamedThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/16
 */
@Configuration
public class ThreadConfig {

    @Bean
    public ThreadPoolExecutor getThreadPoolExecutor(){
        int corePoolSize = Runtime.getRuntime().availableProcessors();
        NamedThreadFactory namedThreadFactory = new NamedThreadFactory();
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                corePoolSize,corePoolSize*2,
                0L, TimeUnit.SECONDS,new LinkedBlockingQueue<>(100),namedThreadFactory);
        return pool;
    }
}
