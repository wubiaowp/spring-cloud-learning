package com.test.alipay.constants;

/**
 * @author WALKMAN
 * @Description: 微信签名参数
 **/
public class WeChatConstants {
    /**
     * 微信登录签名参数AppID
     */
    public static final String appId = "test";

    /**
     * 微信登录签名参数AppSecret
     */
    public static final String appSecret = "test";

    /**
     * 获取token授权信息
     */
    public static final String GET_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";

    /**
     * 获取用户信息url
     */
    public static final String GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?";

}
