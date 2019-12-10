package com.fuhan.v17item.mythread;

import com.fuhan.commons.pojo.ResultBean;
import com.fuhan.entity.Product;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/4
 */
@Slf4j
public class CreateHtmlTask implements Callable<ResultBean<Integer>> {

    private Product product;
    private String templates;
    private Configuration configuration;
    public CreateHtmlTask(Product product, Configuration configuration,String templates){
        this.product = product;
        this.configuration=configuration;
        this.templates=templates;
    }

    @Override
    public ResultBean<Integer> call()  {
        //生成静态页面
        //1.根据id获取数据
        try {
            //2.数据结合模板生成静态页
            Template template = configuration.getTemplate(templates);
            Map<String,Object> data = new HashMap<>();
            data.put("product",product);
            String serverpath= ResourceUtils.getURL("classpath:static").getPath();
            StringBuilder builder = new StringBuilder(serverpath).append(File.separator+product.getId()+".html");
            FileWriter writer = new FileWriter(builder.toString());
            template.process(data,writer);
        } catch (IOException | TemplateException e) {
            log.error(e.getMessage());
            return new ResultBean<>("0",product.getId());
        }
        return new ResultBean<>("1",product.getId());
    }
}
