package com.fuhan.v17order.pojo;

import com.fuhan.commons.utils.CodeUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BizContent implements Serializable {
    private String out_trade_no = System.currentTimeMillis()+new CodeUtils().getMessageCode();
    private String product_code="FAST_INSTANT_TRADE_PAY";
    private String total_amount;
    private String subject;
    private String body;
}
