package com.fuhan.v17cartservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.fuhan.api.ICartService;
import com.fuhan.commons.base.BaseServiceImpl;
import com.fuhan.commons.base.IBaseDao;
import com.fuhan.commons.pojo.ResultBean;
import com.fuhan.entity.Cart;
import com.fuhan.entity.Product;
import com.fuhan.mapper.CartMapper;
import com.fuhan.mapper.ProductMapper;
import com.fuhan.vo.CartItem;
import com.fuhan.vo.CartItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/14
 */
@Service
@org.springframework.stereotype.Service
public class CartServiceImpl extends BaseServiceImpl<Cart> implements ICartService {

    @Resource(name = "myStringRedisTemplate")
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CartMapper cartMapper;

    @Override
    public IBaseDao<Cart> getDao() {
        return cartMapper;
    }

    @Override
    public ResultBean<Boolean> add(String token, Integer productId, Integer count) {
        //1.根据token查询到购物车信息
        StringBuilder key = new StringBuilder("user:cart:").append(token);
        Map<Object, Object> cart = redisTemplate.opsForHash().entries(key.toString());
        //2.当前购物已存在，且存在该商品，则需要修改商量的数量即可
        if(cart.size() >0){
            //需要当前的购物车是否已经存在该商品
            if (redisTemplate.opsForHash().hasKey(key.toString(),productId)) {
                //如果存在，直接更改数量即可
                CartItem cartItem =
                        (CartItem) redisTemplate.opsForHash().get(key.toString(), productId);
                //更改数量
                cartItem.setCount(count);
                //更新操作时间
                cartItem.setUpdateTime(new Date());
                //保存变更
                redisTemplate.opsForHash().put(key.toString(),productId,cartItem);
                //设置有效期
                redisTemplate.expire(key.toString(),15, TimeUnit.DAYS);

                //返回
                return new ResultBean<>("200",true);
            }
        }
        //3，其他情况，直接添加商品到购物车即可
        CartItem cartItem = new CartItem();
        cartItem.setProductId(productId);
        cartItem.setCount(count);
        cartItem.setUpdateTime(new Date());
        //添加购物项到购物车中
        redisTemplate.opsForHash().put(key.toString(),productId,cartItem);
        //设置有效期
        redisTemplate.expire(key.toString(),15, TimeUnit.DAYS);
        //返回
        return new ResultBean<>("200",true);
    }

    @Override
    public ResultBean<Boolean> del(String token, Integer productId) {
        //1.根据token查询到购物车信息
        Long delete = redisTemplate.opsForHash().delete("user:cart:"+token, productId);
        return new ResultBean<>("200",true);

    }

    @Override
    public ResultBean<Boolean> update(String token, Integer productId, Integer count) {
        //1.根据token查询到购物车信息
        StringBuilder key = new StringBuilder("user:cart:").append(token);
        Map<Object, Object> cart = redisTemplate.opsForHash().entries(key.toString());
        if(cart.size()>0){
            CartItem cartItem =
                    (CartItem) redisTemplate.opsForHash().get(key.toString(), productId);
            assert cartItem != null;
            cartItem.setCount(count);
            cartItem.setUpdateTime(new Date());
            //添加购物项到购物车中
            redisTemplate.opsForHash().put(key.toString(),productId,cartItem);
            //设置有效期
            redisTemplate.expire(key.toString(),15, TimeUnit.DAYS);
            //返回
            return new ResultBean<>("200",true);

        }
        return new ResultBean<>("404",false);
    }

    @Override
    public List<CartItemVO> query(String token) {
        Map<Object, Object> cart = redisTemplate.opsForHash().entries("user:cart:" + token);
        List<CartItemVO> list = new ArrayList<>();
        cart.forEach((k,v) -> {
            CartItemVO cartItemVO = new CartItemVO();
            Integer productId = (Integer) k;
            CartItem cartItem = (CartItem) v;
            Product product = productMapper.selectByPrimaryKey(productId);
            cartItemVO.setProduct(product);
            cartItemVO.setCount(cartItem.getCount());
            cartItemVO.setUpdateTime(cartItem.getUpdateTime());
            list.add(cartItemVO);
        });

        return list;
    }



    @Override
    public ResultBean<Boolean> cartToUser(String token, Integer userId) {
        Map<Object, Object> cart = redisTemplate.opsForHash().entries("user:cart:" + token);
        if(cart.size()>0) {
            Map<Object, Object> myCart = redisTemplate.opsForHash().entries("user:cart:" + userId);
            if (myCart.size() > 0) {
                myCart.forEach((k, v) -> {
                    CartItem mCartItem = (CartItem) v;
                    if (cart.containsKey(k)) {
                        CartItem cartItem = (CartItem) cart.get(k);
                        cartItem.setCount(cartItem.getCount() + mCartItem.getCount());
                        cartItem.setUpdateTime(new Date());
                        cart.put(k, cartItem);
                    } else {
                        mCartItem.setUpdateTime(new Date());
                        cart.put(k, mCartItem);
                    }
                });
            }
            redisTemplate.opsForHash().putAll("user:cart:" + userId, cart);
            redisTemplate.delete("user:cart:" + token);
        }
        return new ResultBean<>("200",true);
    }

    @Override
    public List<CartItemVO> getCartItemVOList(int parseInt) {
        return  cartMapper.getCartItemVOList(parseInt);

    }


    @Override
    public List<CartItem> getCartItemList(Integer userId) {
        Map<Object, Object> cart = redisTemplate.opsForHash().entries("user:cart:" + userId);
        if(cart.size()>0) {
            List<CartItem> list = new ArrayList<>(cart.size());
            cart.forEach((productId, cartItem) -> {
                list.add((CartItem) cartItem);
            });
            return list;
        }
        return null;
    }

    @Override
    public int batchAdd(List<Cart> cartList) {
        return cartMapper.batchAdd(cartList);
    }

    @Override
    public int delProductByProductId(Integer userId, Integer productId) {
        return  cartMapper.delProductByProductId(userId,productId);
    }

    @Override
    public ResultBean<String> addToRedis(Integer userId,List<Cart> list) {
        for (Cart cart : list) {
            CartItem cartItem = new CartItem();
            cartItem.setProductId(cart.getProductid());
            cartItem.setCount(cart.getCount());
            cartItem.setUpdateTime(cart.getUpdatedate());
            //添加购物项到购物车中
            redisTemplate.opsForHash().put("user:cart:"+userId,cart.getProductid(),cartItem);
            //设置有效期
            redisTemplate.expire("user:cart:"+userId,15, TimeUnit.DAYS);
        }
        return new ResultBean<>("200","合并成功");
    }


}
