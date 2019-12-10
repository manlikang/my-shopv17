package com.fuhan.commons.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/10/12
 */
public class ShiroUtils {

    public static String getSimpleHash(String userId,String email) {
        // 1.加密算法
        String algorithmName = "MD5";
        // 2.密码
        Object source = email;
        // 3.盐值
        Object salt = ByteSource.Util.bytes(userId); // 盐值一般是用户名或者userId
        // 4.加密次数
        int hashIterations = 1024;

        SimpleHash simpleHash = new SimpleHash(algorithmName, source, salt, hashIterations);
        return simpleHash.toString();
    }



}
