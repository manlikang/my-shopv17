package com.fuhan.interfaces;

import com.fuhan.commons.base.IBaseService;
import com.fuhan.commons.pojo.ResultBean;
import com.fuhan.entity.User;

import java.util.Map;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/10
 */
public interface IUserService extends IBaseService<User> {
    int isExistPhone(String phone);

    int isExistMail(String email);

    ResultBean<String> activate(String code, String email);

    int isExistName(String username);


    ResultBean<Map<String,String>> login(String username, String password);

    ResultBean<String> checkLoginStatus(String uuid);
}
