package com.fuhan.v17miaosha;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.fuhan.v17miaosha.mapper")
public class V17MiaoshaApplication {

    public static void main(String[] args) {
        SpringApplication.run(V17MiaoshaApplication.class, args);
    }

}
