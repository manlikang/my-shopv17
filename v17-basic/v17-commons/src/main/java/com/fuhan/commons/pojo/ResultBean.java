package com.fuhan.commons.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultBean<T> implements Serializable {
    private String statusCode;
    private T data;

}
