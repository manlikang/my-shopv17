package com.fuhan.v17mailservice;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fuhan.entity.ErrorEmail;
import com.fuhan.mapper.ErrorEmailMapper;
import com.fuhan.mapper.UserMapper;
import com.fuhana.interfaces.IMailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class V17MailServiceApplicationTests {

    @Autowired
    private IMailService service;
    @Autowired
    private ErrorEmailMapper errorEmailMapper;
    @Autowired
    private UserMapper userMapper;

    @Test
    void sendSimpleTextMailTest() {
        service.sendSimpleTextMail("2624327119@qq.com","你好","及你太美");
    }

    @Test
    void sendHtmlMailTest() {
        service.sendHtmlMail("1309873863@qq.com","你好","<font color='red'>这是红字</font>");
    }

    @Test
    void Test(){
        service.sendAttachmentMail("1270358406@qq.com","滴滴","哈哈","D:\\123.jpg");
    }

    @Test
    void insertTest(){
        errorEmailMapper.insertSelective(new ErrorEmail(null,"1358724404@qq.com","验证码",null,null,1));
    }

    @Test
    void selectIdByEmailTest(){
        int i = userMapper.selectIdByEmail("1309873863@qq.com");
        System.out.println(i);
    }
}
