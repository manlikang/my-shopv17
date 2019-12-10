package com.fuhan.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart implements Serializable {
    private Integer id;

    private Integer userid;

    private Integer productid;

    private Date updatedate;

    private Integer count;
}