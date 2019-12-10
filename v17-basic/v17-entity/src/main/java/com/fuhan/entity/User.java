package com.fuhan.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private Integer id;

    private String username;

    private String phone;

    private String email;

    private String password;

    private Date createDate;

    private Date updateDate;

    private Integer flag;
}