package com.fuhan.v17mailservice;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.core.Ordered;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.xml.crypto.Data;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDubbo
@MapperScan("com.fuhan.mapper")
@EnableAsync(
        mode = AdviceMode.PROXY, proxyTargetClass = false,
        order = Ordered.HIGHEST_PRECEDENCE
)
@EnableScheduling
public class V17MailServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(V17MailServiceApplication.class, args);
    }

}
