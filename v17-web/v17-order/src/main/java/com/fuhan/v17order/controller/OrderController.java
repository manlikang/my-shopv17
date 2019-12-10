package com.fuhan.v17order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/18
 */
@Controller
@RequestMapping("order")
public class OrderController {

    @RequestMapping("toOrder")
    public String toOrder(){
        return "order";
    }
}
