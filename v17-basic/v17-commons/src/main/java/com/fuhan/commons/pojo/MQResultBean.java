package com.fuhan.commons.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MQResultBean<T> implements Serializable {
    private String msg;
    private T data;
}
