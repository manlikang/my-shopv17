package com.fuhan.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/3
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSolrVO implements Serializable {
    private Integer id;
    private String name;
    private Double price;
    private String salePoint;
    private String images;
}
