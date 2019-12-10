package com.fuhan.v17miaosha2.controller;

import com.fuhan.v17miaosha2.entity.MiaoshaProduct;
import com.fuhan.v17miaosha2.service.IMiaoShaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/20
 */
@Controller
@RequestMapping("miaosha")
public class MiaoShaController {
    @Autowired
    private IMiaoShaService miaoShaService;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @RequestMapping("get")
    @ResponseBody
    public MiaoshaProduct getOne(Long id){
        return miaoShaService.selectByPrimaryKey(id);
    }

    @RequestMapping("kill")
    @ResponseBody
    public void kill(String activeId){
        miaoShaService.kill(activeId);
    }

    @RequestMapping("sqlKill")
    @ResponseBody
    public void sqlKill(String activeId){
        miaoShaService.sqlKill(activeId);
    }

}
