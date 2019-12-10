package com.fuhan.v17item.config;

import com.alibaba.dubbo.common.utils.NamedThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/5
 */

@Configuration
public class CommonConfig {

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
