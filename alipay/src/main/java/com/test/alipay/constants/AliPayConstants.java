package com.test.alipay.constants;

/**
 * @author WALKMAN
 * @Description: 支付宝支付参数
 **/
public class AliPayConstants {

    /**
     * 支付宝公钥
     */
    public static final String RAS_PUBLIC_KEY = "test";

    /**
     * 商户私钥
     */
    public static final String BUS_RAS_PRIVATE_KET = "test";

    /**
     * APPID
     */
    public static final String OPEN_ID = "test";

    /**
     * 支付宝沙箱测试环境，应用未上线，沙箱环境测试有效
     */
    public static final String REQUEST_URL = "https://openapi.alipaydev.com/gateway.do";

    /**
     * 编码格式
     */
    public static String CHARSET = "UTF-8";

    /**
     * 参数格式
     */
    public static String FORMAT = "json";

    /**
     * 加密方式
     */
    public static String SIGNTYPE = "RSA2";

    /**
     * 支付类型-提现(固定)
     */
    public static String PAY_TYPE = "ALIPAY_LOGONID";

    /**
     * 支付宝回调-交易成功、不能对订单信息进行操作
     */
    public static String TRADE_FINISHED = "TRADE_FINISHED";

    /**
     * 支付宝回调-交易成功、处理后续逻辑如退款
     */
    public static String TRADE_SUCCESS = "TRADE_SUCCESS";

    /**
     * 平台和支付宝签约属性-固定值
     */
    public static String PRODUCT_CODE = "QUICK_WAP_WAY";

    /**
     * 支付宝提现成功状态
     */
    public static String SUCCESS_CODE = "10000";

}
