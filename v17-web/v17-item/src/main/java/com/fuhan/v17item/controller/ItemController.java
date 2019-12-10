package com.fuhan.v17item.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.fuhan.commons.pojo.ResultBean;
import com.fuhan.interfaces.IProductService;
import com.fuhan.v17item.synpage.SynPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.ArrayList;
import java.util.List;


/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/4
 */
@Slf4j
@Controller
@RequestMapping("item")
public class ItemController {

    @Reference
    private IProductService productService;
    @Autowired
    private SynPage synPage;

    @RequestMapping("getPage/{id}")
    @ResponseBody
    public ResultBean<String> getPage(@PathVariable int id){
        List<Integer> list = new ArrayList<>(1);
        list.add(id);
        return synPage.synPageByIds(list);
    }

    @RequestMapping("batchGetPage")
    @ResponseBody
    public ResultBean<String> batchAllGetPage(){
        return synPage.synAllPage();
    }

    @RequestMapping("batchGetPageByIds")
    @ResponseBody
    public ResultBean<String> batchGetPage(@RequestParam("ids") List<Integer> ids){
        return synPage.synPageByIds(ids);
    }




}
