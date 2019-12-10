package com.fuhan.commons.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendBean<T> implements Serializable {
    private String type;
    private String userId;
    private T data;
}
