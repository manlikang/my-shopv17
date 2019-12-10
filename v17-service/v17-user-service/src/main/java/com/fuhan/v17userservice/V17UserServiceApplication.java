package com.fuhan.v17userservice;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableDubbo
@MapperScan("com.fuhan.mapper")
public class V17UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(V17UserServiceApplication.class, args);
    }

}
