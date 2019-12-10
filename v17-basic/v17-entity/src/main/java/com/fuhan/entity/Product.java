package com.fuhan.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
    private Integer id;

    private String name;

    private Double price;

    private Double salePrice;

    private String images;

    private String salePoint;

    private String flag;

    private Date createTime;

    private Date updateTime;

    private Integer typeId;

    private Integer pid;

    private Integer stock;

    private String typeName;

    private Integer myTypeId;
}