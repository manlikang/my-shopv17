package com.fuhan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDesc implements Serializable {
    private Integer id;

    private Integer productId;

    private String productDesc;
}