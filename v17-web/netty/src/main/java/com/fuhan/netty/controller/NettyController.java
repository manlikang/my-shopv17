package com.fuhan.netty.controller;

import com.fuhan.netty.utils.CodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/27
 */
@Controller
@RequestMapping("netty")
public class NettyController {

    @RequestMapping("To")
    public String to(Model model){
        String messageCode = new CodeUtils().getMessageCode();
        model.addAttribute("userId",messageCode);
        return "websocket";
    }
}
