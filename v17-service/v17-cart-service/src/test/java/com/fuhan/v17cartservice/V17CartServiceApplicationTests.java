package com.fuhan.v17cartservice;

import com.fuhan.api.ICartService;
import com.fuhan.entity.Cart;
import com.fuhan.vo.CartItemVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class V17CartServiceApplicationTests {
    @Autowired
    private ICartService cartService;
    @Test
    void addTest() {
        System.out.println(cartService.add("abcd", 21, 6));

    }

    @Test
    void delTest() {
        System.out.println(cartService.del("abcd", 17));

    }


    @Test
    void updateTest() {
        cartService.update("abcd",16,16);
    }
    @Test
    void queryTest(){
    }

    @Test
    void cartToUser(){
        cartService.cartToUser("abcd",10);
    }

    @Test
    void getVoTest(){

    }

    @Test
    void batchAddTest(){
        List<Cart> list = new ArrayList<>();
        Cart cart = new Cart(null,40,17,new Date(System.currentTimeMillis()+10000),6);
        Cart cart1 = new Cart(null,40,18,new Date(System.currentTimeMillis()+20000),7);
        Cart cart2 = new Cart(null,40,19,new Date(System.currentTimeMillis()+30000),8);
        Cart cart3 = new Cart(null,40,21,new Date(System.currentTimeMillis()+40000),9);
        list.add(cart);
        list.add(cart1);
        list.add(cart2);
        list.add(cart3);
        System.out.println(cartService.batchAdd(list));

    }

}
