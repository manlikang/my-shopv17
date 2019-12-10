package com.fuhan.v17userservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.fuhan.commons.base.BaseServiceImpl;
import com.fuhan.commons.base.IBaseDao;
import com.fuhan.commons.pojo.ResultBean;
import com.fuhan.commons.utils.ShiroUtils;
import com.fuhan.entity.User;
import com.fuhan.interfaces.IUserService;
import com.fuhan.mapper.UserMapper;
import com.fuhan.v17userservice.util.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;


/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/10
 */
@Service
@Slf4j
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public IBaseDao<User> getDao() {
        return userMapper;
    }

    @Override
    public int insertSelective(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        int i = super.insertSelective(user);
        return user.getId();
    }

    @Override
    public int isExistPhone(String phone) {
        return userMapper.isExistPhone(phone);
    }

    @Override
    public int isExistMail(String email) {
        return userMapper.isExistMail(email);
    }

    @Override
    public ResultBean<String> activate(String userId,String token) {
        System.out.println(userId+"::"+token);
        String email = redisTemplate.opsForValue().get(userId);
        System.out.println(email);
        if(email != null) {
            String mytoken = ShiroUtils.getSimpleHash(userId, email);
            if (mytoken.equals(token)) {
                System.out.println("进入");
                int id = Integer.parseInt(userId);
                User user = new User(id, null, null, null, null, null, null, 1);
                userMapper.updateByPrimaryKeySelective(user);
                return new ResultBean<>("200", "验证成功");
            }
        }
        return new ResultBean<>("500","验证失败");
    }

    @Override
    public int isExistName(String username) {
        return userMapper.isExistName(username);
    }

    @Override
    public ResultBean<Map<String,String>> login(String username, String password) {
        User user= userMapper.login(username);
        if(user!=null && passwordEncoder.matches(password, user.getPassword())){
            JwtUtils jwtUtils = new JwtUtils("fuhan666",24*60*60*1000L);
            String jwtToken = jwtUtils.createJwtToken(user.getId() + "", user.getId()+"");
            Map<String,String> map = new HashMap<>(2);
            map.put("token",jwtToken);
            map.put("userId",user.getId()+"");
            return new ResultBean<>("200",map);
        }
        return new ResultBean<>("500",null);
    }

    @Override
    public ResultBean<String> checkLoginStatus(String uuid) {
        JwtUtils jwtUtils = new JwtUtils("fuhan666");
        System.out.println("验证登录状态的user_token:"+uuid);
        try{
            Claims claims = jwtUtils.parseJwtToken(uuid);
            String userId = claims.getSubject();
            return new ResultBean<>("200",userId);
        }catch (JwtException e){
            log.error(e.getMessage());
            return new ResultBean<>("500",null);
        }

    }
}
