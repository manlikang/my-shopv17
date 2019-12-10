package com.fuhan.v17cartservice;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@MapperScan("com.fuhan.mapper")
@EnableDubbo
public class V17CartServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(V17CartServiceApplication.class, args);
    }

}
