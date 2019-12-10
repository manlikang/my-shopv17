package com.fuhan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductType implements Serializable {
    private Integer id;

    private Integer pid;

    private String name;

    private String flag;
}