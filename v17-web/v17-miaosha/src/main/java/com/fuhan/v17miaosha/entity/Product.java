package com.fuhan.v17miaosha.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Product implements Serializable {
    private Long id;

    private Integer stock;

    private Long productId;
}