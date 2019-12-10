package com.fuhan.v17miaosha2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.fuhan.v17miaosha2.mapper")
public class V17Miaosha2Application {

    public static void main(String[] args) {
        SpringApplication.run(V17Miaosha2Application.class, args);
    }

}
