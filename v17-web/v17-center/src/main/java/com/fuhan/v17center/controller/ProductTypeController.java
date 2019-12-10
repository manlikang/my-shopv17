package com.fuhan.v17center.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fuhan.interfaces.IProductTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/4
 */
@Controller
@RequestMapping("productType")
public class ProductTypeController {

    @Reference
    private IProductTypeService productTypeService;

    @RequestMapping("toManage")
    public String toManage(){
        System.out.println("进入");
        return "productType/list";
    }
}
