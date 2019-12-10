package com.fuhan.interfaces;

import com.fuhan.commons.pojo.PageResultBean;
import com.fuhan.commons.pojo.ResultBean;
import com.fuhan.entity.Product;
import com.fuhan.vo.ProductSolrVO;

import java.util.List;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/2
 */
public interface ISearchService {

    /**
     * 做全量同步
     * 在数据初始化时候使用
     * @return
     */
    ResultBean<String> synAllData();

    //mysql 1000+1

    /**
     * 增量同步
     * @param id
     * @return
     */
    ResultBean<String> synById(Integer id);

    /**
     * 删除
     * @param id
     * @return
     */
    ResultBean<String> delById(Integer id);



    ResultBean<String> batchDel(List<Integer> ids);


    /**
     *  分页查询搜索结果
     * @param keywords 关键字
     * @param currPage 当前页
     * @param pageSize 页大小
     * @return 分页查询结果集
     */
    PageResultBean<ProductSolrVO> queryByKeywordsAndPage(String keywords, int currPage, int pageSize);
}
