package com.fuhan.vo;

import com.fuhan.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemVO implements Serializable,Comparable<CartItemVO> {
    private Product product;

    private Integer count;

    private Date updateTime;

    public int compareTo(CartItemVO o) {
        int result = o.getUpdateTime().compareTo(this.updateTime);
        if(result == 0){
            result = 1;
        }
        return result;
    }

    public static void main(String[] args) throws InterruptedException {
        Set<CartItemVO> set = new TreeSet<CartItemVO>();
        for(int i=0;i<5;i++){
            CartItemVO cartItemVO = new CartItemVO(new Product(), 1, new Date());
            Thread.sleep(1000);
            set.add(cartItemVO);
        }
        for (CartItemVO cartItemVO : set) {
            System.out.println(cartItemVO.getUpdateTime().getTime());
        }
    }
}
