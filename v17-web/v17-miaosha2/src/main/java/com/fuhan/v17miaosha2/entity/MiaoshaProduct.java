package com.fuhan.v17miaosha2.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MiaoshaProduct implements Serializable {
    private Long id;

    private Long productId;

    private String productName;

    private String productImages;

    private Integer productTypeId;

    private String productTypeName;

    private Long productPrice;

    private Long salePrice;

    private Integer count;

    private Date startTime;

    private Date endTime;

    private String status;

    private Boolean check;

    private Boolean flag;

    private Date createTime;

    private Date updateTime;

    private Long createUser;

    private Long updateUser;
}