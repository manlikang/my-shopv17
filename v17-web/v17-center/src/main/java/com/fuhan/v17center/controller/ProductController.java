package com.fuhan.v17center.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fuhan.commons.pojo.MQResultBean;
import com.fuhan.entity.Product;
import com.fuhan.entity.ProductType;
import com.fuhan.interfaces.IProductService;
import com.fuhan.interfaces.ISearchService;
import com.fuhan.v17center.sender.Sender;
import com.fuhan.vo.ProductVO;
import com.github.pagehelper.PageInfo;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.google.gson.Gson;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/10/28
 */

@Controller
@RequestMapping("product")
public class ProductController {

    @Reference
    private IProductService productService;
    @Reference
    private ISearchService searchService;
    @Autowired
    private Sender sender;



    @RequestMapping("toIndex")
    public String toIndex(){
        return "product/index";
    }

    @RequestMapping("page/{curr}/{size}")
    public String getListByPage(@PathVariable int curr,@PathVariable int size , Model model){
        PageInfo<Product> page = productService.getListByPage(curr,size);
        model.addAttribute("page",page);
        return "product/show";

    }

    @RequestMapping("toMain")
    public String toMain(){
        return "product/main";
    }

    @RequestMapping("add")
    public String addProduct(ProductVO vo, Model model){
        Integer newId = productService.add(vo);
        sender.send("product.add",new Integer[]{newId});
        model.addAttribute("newId",newId);
        return "redirect:/product/page/1/2";
    }

    @RequestMapping("toUpdate/{id}")
    @ResponseBody
    public ProductVO toUpdate(@PathVariable int id){

        return productService.selectVOById(id);
    }

    @RequestMapping("update")
    public String updateProduct(ProductVO vo, Model model){
        int id = vo.getProduct().getId();
        String images = productService.selectByPrimaryKey(id).getImages();
        boolean flag = productService.updateByVO(vo);
        sender.send("product.update",new Integer[]{id});
        return "redirect:/product/page/1/2";
    }

    @RequestMapping("del/{id}")
    @ResponseBody
    public String delProduct(@PathVariable int id){
       boolean flag= productService.delProductAndDescVById(id);
       if(flag){
           sender.send("product.del",new Integer[]{id});
           return "删除成功";
       }
       return "删除失败";
    }

    @RequestMapping("batchDel")
    @ResponseBody
    public String batchDel(@RequestParam(value = "ids") List<Integer> ids){
        boolean flag = productService.batchDelById(ids);
        if(flag){
            sender.send("product.del",ids.toArray(new Integer[ids.size()]));
            return "删除成功";
        }
        return "删除失败";
    }

    @RequestMapping("getParentTypeList")
    @ResponseBody
    public String getParentTypeList(){
        List<ProductType> list = productService.getParentTypeList();
        return new Gson().toJson(list);
    }

    @RequestMapping("getSmallList/{id}")
    @ResponseBody
    public String getSmallList(@PathVariable int id){
        List<ProductType> list = productService.getSmaillList(id);
        return new Gson().toJson(list);
    }




}
