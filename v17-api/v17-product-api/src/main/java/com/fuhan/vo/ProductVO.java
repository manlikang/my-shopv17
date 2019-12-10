package com.fuhan.vo;

import com.fuhan.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/10/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVO implements Serializable {
    private Product product;
    private String productDesc;

}
