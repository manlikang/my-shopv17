package com.fuhan.v17searchservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.fuhan.commons.pojo.PageResultBean;
import com.fuhan.commons.pojo.ResultBean;
import com.fuhan.interfaces.ISearchService;
import com.fuhan.mapper.ProductMapper;
import com.fuhan.vo.ProductSolrVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/3
 */
@Service
@Slf4j
public class SearchServiceImpl implements ISearchService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private SolrClient solrClient;

    @Override
    public ResultBean<String> synAllData() {
        List<ProductSolrVO> list = productMapper.getSomeList();
        for (ProductSolrVO product : list) {
            SolrInputDocument document = new SolrInputDocument();
            document.setField("id", product.getId());
            document.setField("product_name", product.getName());
            document.setField("product_price", product.getPrice());
            document.setField("product_sale_point", product.getSalePoint());
            document.setField("product_images", product.getImages());

            try {
                solrClient.add(document);
            } catch (SolrServerException | IOException e) {
                log.error(e.getMessage());
                return new ResultBean<>("500", "同步数据失败！");
            }
        }

        try {
            solrClient.commit();
        } catch (SolrServerException | IOException e) {
            log.error(e.getMessage());
            return new ResultBean<>("500", "同步数据失败！");
        }


        return new ResultBean<>("200", "数据同步成功！");
    }

    @Override
    public ResultBean<String> synById(Integer id) {
        ProductSolrVO product = productMapper.getVOById(id);
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id", product.getId());
        document.setField("product_name", product.getName());
        document.setField("product_price", product.getPrice());
        document.setField("product_sale_point", product.getSalePoint());
        document.setField("product_images", product.getImages());
        try {
            solrClient.add(document);
            solrClient.commit();
        } catch (SolrServerException | IOException e) {
            log.error(e.getMessage());
            return new ResultBean<>("500", "同步数据失败！");
        }
        return new ResultBean<>("200", "数据同步成功！");
    }

    @Override
    public ResultBean<String> delById(Integer id) {
        try {
            solrClient.deleteById(String.valueOf(id));
            solrClient.commit();
        } catch (SolrServerException | IOException e) {
            log.error(e.getMessage());
            return new ResultBean<>("500", "同步数据失败！");
        }

        return new ResultBean<>("200", "数据同步成功！");
    }


    @Override
    public ResultBean<String> batchDel(List<Integer> ids) {
        try {
            for (Integer id : ids) {
                solrClient.deleteById(String.valueOf(id));
                solrClient.commit();
            }
        } catch (SolrServerException | IOException e) {
            log.error(e.getMessage());
            return new ResultBean<>("500", "同步数据失败！");
        }

        return new ResultBean<>("200", "数据同步成功！");
    }

    @Override
    public PageResultBean<ProductSolrVO> queryByKeywordsAndPage(String keywords, int currPage, int pageSize) {
        SolrQuery condition = new SolrQuery();

        int index = pageSize * (currPage - 1);
        int total = 0;
        if (!StringUtils.isAllEmpty(keywords)) {
            condition.setQuery("product_keywords:" + keywords);
        } else {
            condition.setQuery("product_keywords:*");
        }
        //2.设置高亮
        condition.setHighlight(true);
        condition.addHighlightField("product_name");
        condition.setHighlightSimplePre("<font  color='#FF5722'>");
        condition.setHighlightSimplePost("</font>");
        //3.执行查询
        List<ProductSolrVO> results;
        //4.拼装查询结果
        try {
            QueryResponse response = solrClient.query(condition);
            //
            SolrDocumentList list = response.getResults();
            total = list.size();
            //
            condition.setStart(index);
            condition.setRows(pageSize);
            response = solrClient.query(condition);
            list = response.getResults();
            results = new ArrayList<>(list.size());
            //获取高亮信息
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

            for (SolrDocument document : list) {
                ProductSolrVO product = new ProductSolrVO();
                product.setId(Integer.parseInt(document.getFieldValue("id").toString()));
                product.setName(document.getFieldValue("product_name").toString());
                product.setPrice(Double.parseDouble(document.getFieldValue("product_price").toString()));
                product.setImages(document.getFieldValue("product_images").toString());

                //针对高亮做设置
                Map<String, List<String>> map = highlighting.get(document.getFieldValue("id"));
                List<String> productNameHighLighting = map.get("product_name");
                if (productNameHighLighting != null && productNameHighLighting.size() > 0) {
                    product.setName(productNameHighLighting.get(0));
                } else {
                    product.setName(document.getFieldValue("product_name").toString());
                }

                results.add(product);
            }
        } catch (SolrServerException | IOException e) {
            log.error(e.getMessage());
            return null;
        }
        System.out.println(results);
        PageResultBean<ProductSolrVO> page = new PageResultBean<>(results);
        page.setPageNum(currPage);
        page.setPageSize(pageSize);
        page.setTotal(total);
        int pages = (int) (Math.ceil(((double) total) / pageSize));
        page.setPages(pages);
        page.setNavigatePages(pages);
        int[] nav = new int[pages];
        for (int i = 0; i < pages; i++) {
            nav[i] = i + 1;
        }
        page.setNavigatepageNums(nav);
        return page;
    }


}
