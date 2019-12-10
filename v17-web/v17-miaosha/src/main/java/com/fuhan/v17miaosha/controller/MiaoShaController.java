package com.fuhan.v17miaosha.controller;

import com.fuhan.v17miaosha.entity.MiaoshaProduct;
import com.fuhan.v17miaosha.pojo.ResultBean;
import com.fuhan.v17miaosha.sender.Sender;
import com.fuhan.v17miaosha.service.IMiaoShaService;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author : FuHan
 * @description :
 * @date: 2019/11/20
 */
@Controller
@RequestMapping("miaosha")
public class MiaoShaController {
    @Autowired
    private IMiaoShaService miaoShaService;

    @Autowired
    private RateLimiter rateLimiter;
    @Autowired
    private Sender sender;

    @RequestMapping("addToMiaoShaoQueue/{productId}/{count}")
    @ResponseBody
    public ResultBean<String> addToMiaoShaoQueue(@PathVariable Long productId, @PathVariable Long count){
        miaoShaService.addToMiaoShaoQueue(productId,count);
        return new ResultBean<>("200","success");
    }


    @RequestMapping("toUpdate")
    public String toUpdate(Model model){
        double rate = rateLimiter.getRate();
        model.addAttribute("rate",rate);
        return "updateRateLimiter";
    }
    @RequestMapping("update")
    @ResponseBody
    public String update(String rate){
        sender.send(rate);
        return "success";
    }

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
