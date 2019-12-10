package com.fuhan.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorEmail implements Serializable {
    private Integer id;

    private String toEmail;

    private String params;

    private Date createTime;

    private Integer retryNum;

    private Integer typeId;
}