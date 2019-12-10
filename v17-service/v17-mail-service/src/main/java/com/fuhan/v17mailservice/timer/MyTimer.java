package com.fuhan.v17mailservice.timer;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import com.fuhan.commons.constant.MessageConstant;
import com.fuhan.commons.utils.ShiroUtils;
import com.fuhan.entity.ErrorEmail;
import com.fuhan.mapper.ErrorEmailMapper;
import com.fuhan.mapper.UserMapper;
import com.fuhana.interfaces.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/12
 */
@Component
public class MyTimer {

    @Autowired
    private ErrorEmailMapper errorEmailMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private IMailService mailService;

    @Scheduled(cron = "0 0 * * * ?")
    public void run(){
        List<ErrorEmail> list =  errorEmailMapper.selectEmailByRetryNum();
        System.out.println(new Date());
        System.out.println(list);
        for (ErrorEmail errorEmail : list) {

            System.out.println(errorEmail.getToEmail()+"重新发送");
            String email = errorEmail.getToEmail();
            int id = userMapper.selectIdByEmail(email);
            String token = ShiroUtils.getSimpleHash(id+"", email);

            redisTemplate.opsForValue().set(id+"",email,30, TimeUnit.MINUTES);
            mailService.sendHtmlMail(email,"激活邮件", MessageConstant.MESSAGE_TEMPLATE_1+id+MessageConstant.MESSAGE_TEMPLATE_2+token+MessageConstant.MESSAGE_TEMPLATE_3);
        }
    }

}