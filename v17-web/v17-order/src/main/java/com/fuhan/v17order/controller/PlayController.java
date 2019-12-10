package com.fuhan.v17order.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.fuhan.v17order.Resource.AlipayProperties;
import com.fuhan.v17order.pojo.BizContent;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.alipay.api.AlipayConstants.SIGN_TYPE_RSA2;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/18
 */

@Controller
@RequestMapping("pay")
public class PlayController {

    @Autowired
    private AlipayClient alipayClient;
    @Autowired
    private AlipayProperties alipayProperties;

    @RequestMapping("generate")
    @ResponseBody
    public void generate(HttpServletResponse response, BizContent content) throws IOException {
        //创建API对应的request
        String orderNo = UUID.randomUUID().toString();
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl("http://weilezaigou.natapp1.cc/pay/returnUrl");
        //在公共参数中设置回跳和通知地址
        alipayRequest.setNotifyUrl("http://weilezaigou.natapp1.cc/pay/notifyUrl");
        String s = new Gson().toJson(content);
        System.out.println(s);
        //填充业务参数
        alipayRequest.setBizContent(s);
        String form = "";
        try {
            //调用SDK生成表单
            form = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=utf-8" );
        //直接将完整的表单html输出到页面
        response.getWriter().write(form);
        response.getWriter().flush();
        response.getWriter().close();
    }


    @RequestMapping("notifyUrl")
    @ResponseBody
    public void notifyUrl(HttpServletResponse response, HttpServletRequest request) throws AlipayApiException, IOException {
        //将异步通知中收到的所有参数都存放到 map 中
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String> map =new HashMap<>(parameterMap.size());
        parameterMap.forEach((k,v) -> {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < v.length - 1; i++) {
                builder.append(v[i]+",");
            }
            builder.append(v[v.length-1]);
            map.put(k,builder.toString());
        });
        //调用SDK验证签名
        boolean signVerified = AlipaySignature.rsaCheckV1(map, alipayProperties.getALIPAY_PUBLIC_KEY(), "utf-8", SIGN_TYPE_RSA2);
        if(signVerified){
            System.out.println("验证成功");
            response.getWriter().write("success");
        }else{
            // TODO 验签失败则记录异常日志，并在response中返回failure.
            System.out.println("验证失败");
            response.getWriter().write("failure");
        }
    }

    @RequestMapping("returnUrl")
    @ResponseBody
    public void returnUrl(){
        System.out.println("没有一点点防备");
    }

}
