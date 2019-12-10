package com.fuhan.interfaces;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/8
 */
public interface IShortMessageService {
    void sendCode(String to,String template,String code);

    Boolean verifPhoneCode(String phone, String phoneCode);
}
