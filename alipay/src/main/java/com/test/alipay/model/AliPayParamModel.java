package com.test.alipay.model;

import lombok.Data;

/**
 * @author WALKMAN
 * @Description: 支付宝订单参数
 **/
@Data
public class AliPayParamModel {

    /**
     * 系统交易订单号
     */
    private String out_trade_no;

    /**
     * 订单金额
     */
    private String total_amount;

    /**
     * 标题
     */
    private String subject;

    /**
     * 商品标签-固定值
     */
    private String product_code;


}
