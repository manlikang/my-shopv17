package com.fuhan.v17searchservice;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fuhan.commons.pojo.PageResultBean;
import com.fuhan.commons.pojo.ResultBean;
import com.fuhan.interfaces.ISearchService;
import com.fuhan.vo.ProductSolrVO;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@SpringBootTest
public class V17SearchServiceApplicationTests {

    @Autowired
    private SolrClient solrClient;

    @Reference
    private ISearchService searchService;

    @Test
    void contextLoads() {

    }

    @Test
    public void synAllDataTest() {
        ResultBean resultBean = searchService.synAllData();
        System.out.println(resultBean.getData());
    }

    @Test
    public void synByIdTest() {
        ResultBean resultBean = searchService.synById(17);
        System.out.println(resultBean.getData());
    }


    @Test
    public void addOrUpdateTest() throws IOException, SolrServerException {
        //1.创建一个document对象
        SolrInputDocument document = new SolrInputDocument();
        //2.设置相关的属性值
        document.setField("id", 15);
        document.setField("product_name", "小米MIX4代");
        document.setField("product_price", 19999);
        document.setField("product_sale_point", "全球最高像素的手机");
        document.setField("product_images", "123");
        //3.保存
        solrClient.add(document);
        solrClient.commit();
    }

    @Test
    public void queryTest() throws IOException, SolrServerException {
        //1.组装查询条件
        SolrQuery queryCondition = new SolrQuery();
        //小米MIX4代 会分词
        queryCondition.setQuery("product_name:小米MIX4代");
        //2.执行查询
        QueryResponse response = solrClient.query(queryCondition);
        //3.得到结果
        SolrDocumentList solrDocuments = response.getResults();
        for (SolrDocument document : solrDocuments) {
            System.out.println(document.getFieldValue("product_name") + "->" + document.getFieldValue("product_price"));
        }
    }

    @Test
    public void delTest() throws IOException, SolrServerException {
        //solrClient.deleteByQuery("product_name:小米MIX4代");
        SolrQuery queryCondition = new SolrQuery();
        //小米MIX4代 会分词
        queryCondition.setQuery("product_name:*");
        //2.执行查询
        QueryResponse response = solrClient.query(queryCondition);
        //3.得到结果
        SolrDocumentList solrDocuments = response.getResults();
        for (SolrDocument document : solrDocuments) {
            System.out.println((String) document.getFieldValue("id"));
            solrClient.deleteById((String) document.getFieldValue("id"));
            solrClient.commit();
        }

    }

    @Test
    public void queryByPageTest() {
        PageResultBean<ProductSolrVO> page = searchService.queryByKeywordsAndPage("小米", 2, 2);
//        for (List<ProductSolrVO> productSolrVOS : page.getList()) {
//            System.out.println(productSolrVOS);
//        }
        System.out.println(page.getList());
    }

}
