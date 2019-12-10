package com.fuhan.v17shortmessageservice.service;

import cn.hutool.http.HttpUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.fuhan.interfaces.IShortMessageService;
import com.fuhan.v17shortmessageservice.send.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/10
 */
@Service
@org.springframework.stereotype.Service
public class ShortMessageServiceImpl implements IShortMessageService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void sendCode(String to,String template,String code) {
        try {
            String httpAddr = new SendMessage().getHttpAddr(to, template,code);
            String s = HttpUtil.get(httpAddr);
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean verifPhoneCode(String phone, String phoneCode) {
        String code = redisTemplate.opsForValue().get(phone);
        return phoneCode.equals(code);
    }
}
