package com.fuhan.v17center.resource;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/10/21
 */

@Component
@ConfigurationProperties(prefix = "resource")
@Data
public class Resource {
    private String imageServer;
}
