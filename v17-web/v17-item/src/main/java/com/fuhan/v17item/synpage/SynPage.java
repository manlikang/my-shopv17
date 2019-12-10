package com.fuhan.v17item.synpage;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fuhan.commons.pojo.ResultBean;
import com.fuhan.entity.Product;
import com.fuhan.interfaces.IProductService;
import com.fuhan.v17item.mythread.CreateHtmlTask;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/6
 */
@Slf4j
@Component
public class SynPage {

    @Autowired
    private Configuration configuration;
    @Autowired
    private ThreadPoolExecutor pool;
    @Reference
    private IProductService productService;

    public ResultBean<String> synPageByIds(List<Integer> ids) {
        List<Product> products = productService.getListByIds(ids);
        List<Future<ResultBean<Integer>>> list = new ArrayList<>(products.size());
        CountDownLatch latch = new CountDownLatch(products.size());
        for (Product product : products) {
            list.add(pool.submit(new CreateHtmlTask(product, configuration,"item.ftl")));
        }
        latch.countDown();
        List<ResultBean<Integer>> results = new ArrayList<>();
        for (Future<ResultBean<Integer>> future : list) {
            try {
                results.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                log.error(e.getMessage());
            }
        }
        for (ResultBean<Integer> result : results) {
            if ("1".equals(result.getStatusCode())) {
                System.out.println("页面" + result.getData() + ".html生成成功");
            } else {
                System.out.println("页面" + result.getData() + ".html生成失败");
            }
        }
        return new ResultBean<>("200", "success");
    }

    public ResultBean<String> synAllPage() {
        List<Product> products = productService.getList();
        List<Future<ResultBean<Integer>>> list = new ArrayList<>(products.size());
        CountDownLatch latch = new CountDownLatch(products.size());
        for (Product product : products) {
            list.add(pool.submit(new CreateHtmlTask(product, configuration,"item.ftl")));
        }
        latch.countDown();
        List<ResultBean<Integer>> results = new ArrayList<>();
        for (Future<ResultBean<Integer>> future : list) {
            try {
                results.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                log.error(e.getMessage());
            }
        }
        for (ResultBean<Integer> result : results) {
            if ("1".equals(result.getStatusCode())) {
                System.out.println("页面" + result.getData() + ".html生成成功");
            } else {
                System.out.println("页面" + result.getData() + ".html生成失败");
            }
        }
        return new ResultBean<>("200", "success");
    }

    public ResultBean<String> delPageByIds(List<Integer> ids) {
        List<Product> products = productService.getListByIds(ids);
        List<Future<ResultBean<Integer>>> list = new ArrayList<>(products.size());
        CountDownLatch latch = new CountDownLatch(products.size());
        for (Product product : products) {
            list.add(pool.submit(new CreateHtmlTask(product, configuration,"del.ftl")));
        }
        latch.countDown();
        List<ResultBean<Integer>> results = new ArrayList<>();
        for (Future<ResultBean<Integer>> future : list) {
            try {
                results.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                log.error(e.getMessage());
            }
        }
        for (ResultBean<Integer> result : results) {
            if ("1".equals(result.getStatusCode())) {
                System.out.println("页面" + result.getData() + ".html已经变为删除页面");
            } else {
                System.out.println("页面" + result.getData() + ".html失败");
            }
        }
        return new ResultBean<>("200", "success");
    }

}
