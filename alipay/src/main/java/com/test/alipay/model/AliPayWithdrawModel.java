package com.test.alipay.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author WALKMAN
 * @Description:支付宝提现表单
 **/
@Data
@Builder
public class AliPayWithdrawModel {

    /**
     * 平台交易订单号
     */
    private String out_biz_no;

    /**
     * 交易方式
     */
    private String payee_type = "ALIPAY_LOGONID";

    /**
     * 提现金额
     */
    private BigDecimal amount;

    /**
     * 提现账户
     */
    private String payee_account;

    /**
     * 支付宝账户昵称
     */
    private String payer_show_name;

    /**
     * 支付宝真实名称
     */
    private String payee_real_name;

    /**
     * 交易备注
     */
    private String remark;

}
