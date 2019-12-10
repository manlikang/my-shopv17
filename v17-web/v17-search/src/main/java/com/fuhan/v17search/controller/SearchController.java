package com.fuhan.v17search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fuhan.commons.pojo.PageResultBean;
import com.fuhan.commons.pojo.ResultBean;
import com.fuhan.interfaces.ISearchService;
import com.fuhan.vo.ProductSolrVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/3
 */
@Controller
@RequestMapping("search")
public class SearchController {
    @Reference
    private ISearchService searchService;


    @RequestMapping("queryByKeyWords4PC/{keywords}/{currPage}/{pageSize}")
    public String queryByKeyWords4PCByPage(@PathVariable int currPage,@PathVariable int pageSize, @PathVariable String keywords, Model model){
        PageResultBean<ProductSolrVO> page = searchService.queryByKeywordsAndPage(keywords,currPage,pageSize);
        model.addAttribute("page",page);
        model.addAttribute("keywords",keywords);
        return "list";
    }
}
