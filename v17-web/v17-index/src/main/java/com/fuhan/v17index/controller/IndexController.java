package com.fuhan.v17index.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fuhan.entity.ProductType;
import com.fuhan.interfaces.IProductTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/2
 */

@Controller
@RequestMapping("index")
public class IndexController {
    @Reference
    private IProductTypeService productTypeService;
    @RequestMapping("toIndex")
    public String toIndex(Model model){
        List<ProductType> list = productTypeService.getList();
        model.addAttribute("list",list);
        return "index";
    }


}
