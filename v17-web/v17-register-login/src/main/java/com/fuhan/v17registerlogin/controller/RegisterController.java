package com.fuhan.v17registerlogin.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fuhan.commons.constant.MQConstant;
import com.fuhan.commons.pojo.ResultBean;
import com.fuhan.commons.pojo.SendBean;
import com.fuhan.entity.User;
import com.fuhan.interfaces.IShortMessageService;
import com.fuhan.interfaces.IUserService;
import com.fuhan.v17registerlogin.sender.Sender;
import com.fuhana.interfaces.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;




/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/8
 */
@Controller
@RequestMapping("register")
public class RegisterController {

    @Reference
    private IMailService mailService;
    @Reference
    private IUserService userService;
    @Reference
    private IShortMessageService shortMessageService;


    @Autowired
    private Sender sender;

    @RequestMapping("toRegister")
    public String toRegister(){
        return "register";
    }



    @RequestMapping("getMessageCode/{phone}")
    @ResponseBody
    public String getMessageCode(@PathVariable String phone){
        sender.send(MQConstant.QUEUE.MESSAGE_QUEUE,new SendBean<>("1",null,phone));
        return "";
    }

    @RequestMapping("isExistPhone/{phone}")
    @ResponseBody
    public String isExistPhone(@PathVariable String phone){
        int i =userService.isExistPhone(phone);
        if(i>0){
            return "0";
        }
        return "1";

    }
    @RequestMapping("isExistMail/{email}")
    @ResponseBody
    public String isExistMail(@PathVariable String email){
        int i =userService.isExistMail(email);
        if(i>0){
            return "0";
        }
        return "1";

    }
    @RequestMapping("isExistName/{username}")
    @ResponseBody
    public String isExistName(@PathVariable String username){
        int i = userService.isExistName(username);
        if(i>0){
            return "0";
        }
        return "1";
    }


    @RequestMapping("verifPhoneCode/{phone}/{phoneCode}")
    @ResponseBody
    public Boolean verifPhoneCode(@PathVariable String phone,@PathVariable String phoneCode){
        return shortMessageService.verifPhoneCode(phone,phoneCode);
    }
    @RequestMapping("activate")
    @ResponseBody
    public ResultBean<String> verifEmailCode(String userId, String token){
        System.out.println(token);
        return userService.activate(userId, token);
    }
    @RequestMapping("register")
    public String register(User user){
        System.out.println(user);
        int newId= userService.insertSelective(user);
        System.out.println("用户id回填为"+newId);
        sender.send(MQConstant.QUEUE.MAIL_QUEUE,new SendBean<>("1",newId+"",user.getEmail()));
        return "redirect:/login/toLogin";
    }


}
