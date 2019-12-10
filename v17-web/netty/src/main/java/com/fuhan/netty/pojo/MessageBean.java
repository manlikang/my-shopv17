package com.fuhan.netty.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageBean<T> {
    private String msgType;
    private T data;
}
