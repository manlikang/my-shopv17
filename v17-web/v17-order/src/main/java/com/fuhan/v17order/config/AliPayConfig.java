package com.fuhan.v17order.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.fuhan.v17order.Resource.AlipayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static com.alipay.api.AlipayConstants.SIGN_TYPE_RSA2;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/18
 */
@Configuration
public class AliPayConfig {
    @Autowired
    private AlipayProperties alipayProperties;

    @Bean(name = "DefaultAlipayClient")
    public AlipayClient getAlipayClient(){
        return new DefaultAlipayClient(alipayProperties.getServerUrl(), alipayProperties.getAppId(),
                alipayProperties.getAPP_PRIVATE_KEY(), alipayProperties.getFormat(),
                alipayProperties.getCharset(), alipayProperties.getALIPAY_PUBLIC_KEY(),
                SIGN_TYPE_RSA2);
    }
}
