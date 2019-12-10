package com.fuhan.v17userservice;


import com.fuhan.entity.User;
import com.fuhan.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class V17UserServiceApplicationTests {


    @Autowired
    private StringRedisTemplate redisTemplate;




}
