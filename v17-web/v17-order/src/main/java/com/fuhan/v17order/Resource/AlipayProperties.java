package com.fuhan.v17order.Resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/18
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Component
@ConfigurationProperties(prefix = "alipayproperties")
public class AlipayProperties {

    private String APP_PRIVATE_KEY;
    private String ALIPAY_PUBLIC_KEY;
    private String serverUrl;
    private String appId;
    private String format;
    private String charset;


}
