package com.test.alipay.constants;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author WALKMAN
 * @Description: 支付宝回调返回参数对象
 **/
@Data
@Builder
public class AliPayNotifyParamConstants implements Serializable {

    /**
     * 商户平台ID
     */
    private String appId;

    /**
     * 支付宝交易凭证号
     */
    private String tradeNo;

    /**
     * 原支付请求的商户订单号
     */
    private String outTradeNo;

    /**
     * 买家支付宝账号
     */
    private String buyerLogonId;

    /**
     * 交易状态
     */
    private String tradeStatus;

    /**
     * 交易金额
     */
    private BigDecimal totalAmount;

    /**
     * 商家在交易中实际收到的款项
     */
    private BigDecimal receiptAmount;

    /**
     * 该笔交易创建的时间。格式为yyyy-MM-dd HH:mm:ss
     */
    private Date gmtCreate;

}
