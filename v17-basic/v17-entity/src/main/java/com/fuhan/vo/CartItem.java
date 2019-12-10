package com.fuhan.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/14
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem implements Serializable,Comparable<CartItem> {
    private Integer productId;
    private Integer count;
    private Date updateTime;

    public int compareTo(CartItem o) {
        int result = (int) (o.getUpdateTime().getTime() - this.getUpdateTime().getTime());
        if(result == 0){
            result =1;
        }
        return result;
    }
}
