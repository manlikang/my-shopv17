package com.fuhan.mapper;

import com.fuhan.commons.base.IBaseDao;
import com.fuhan.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends IBaseDao<User> {

    int isExistPhone(String phone);

    int isExistMail(String email);

    int updateStatusByEmailTo1(String email);

    int isExistName(String username);

    int selectIdByEmail(String toEmail);

    User login(@Param("username") String username);
}