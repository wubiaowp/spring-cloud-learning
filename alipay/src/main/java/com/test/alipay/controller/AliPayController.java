package com.test.alipay.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.test.alipay.constants.AliPayConstants;
import com.test.alipay.constants.AliPayNotifyParamConstants;
import com.test.alipay.model.AliPayParamModel;
import com.test.alipay.model.AliPayWithdrawModel;
import com.test.alipay.util.AliPayOrderUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 支付宝支付处理--暂不做同步处理、回调方式使用异步
 */
@Slf4j
@RestController
@RequestMapping("/api/aliPay")
public class AliPayController {

    /**
     * 初始化客户端
     */
    private static AlipayClient alipayClient = new DefaultAlipayClient(AliPayConstants.REQUEST_URL,
            AliPayConstants.OPEN_ID, AliPayConstants.BUS_RAS_PRIVATE_KET, AliPayConstants.FORMAT,
            AliPayConstants.CHARSET, AliPayConstants.RAS_PUBLIC_KEY, AliPayConstants.SIGNTYPE);

    /**
     * 创建线程池处理业务逻辑
     */
    private ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().build();
    private ExecutorService singleThreadPool = new ThreadPoolExecutor(30, 100,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    /**
     * 支付宝网页支付
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping("/payment")
    public void aliPay(HttpServletResponse response) throws IOException {
        //封装请求参数
        AlipayTradeWapPayRequest aliPayRequest = new AlipayTradeWapPayRequest();
        AliPayParamModel aliPayParamModel = new AliPayParamModel();
        aliPayParamModel.setOut_trade_no("2018120514559696060");
        aliPayParamModel.setSubject("支付测试");
        aliPayParamModel.setTotal_amount("0.01");
        aliPayParamModel.setProduct_code(AliPayConstants.PRODUCT_CODE);
        // TODO 同步处理业务-涉及到商户自定义页面跳转
        aliPayRequest.setReturnUrl("http://ip:port/api/aliPay/return");
        // TODO 异步处理业务-修改订单状态、校验签名是否正确
        aliPayRequest.setNotifyUrl("http://ip:port/api/aliPay/notify");
        aliPayRequest.setBizContent(JSON.toJSONString(aliPayParamModel));
        try {
            // 调用SDK生成表单
            String form = alipayClient.pageExecute(aliPayRequest).getBody();
            response.setContentType("text/html;charset=" + AliPayConstants.CHARSET);
            response.getWriter().write(form);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (AlipayApiException e) {
            log.info("支付宝支付异常，异常信息为:" + e.getErrMsg());
            e.printStackTrace();
        }
    }

    /**
     * 支付宝提现
     */
    @RequestMapping("/withdraw")
    public void withdraw() {
        val aliPayWithdrawModel = AliPayWithdrawModel.builder()
                .out_biz_no("20181206145569697")
                .amount(new BigDecimal(0.2))
                .payee_account("支付宝账号")
                .payee_real_name("真实姓名")
                .payee_type(AliPayConstants.PAY_TYPE)
                .remark("测试支付宝提现")
                .build();
        String json = JSON.toJSONString(aliPayWithdrawModel);
        //实例化连接对象
        AlipayFundTransToaccountTransferRequest withdrawRequest = new AlipayFundTransToaccountTransferRequest();
        withdrawRequest.setBizContent(json);
        try {
            AlipayFundTransToaccountTransferResponse response = alipayClient.execute(withdrawRequest);
            if (AliPayConstants.SUCCESS_CODE.equalsIgnoreCase(response.getCode())) {
                // TODO 处理业务逻辑
            }
        } catch (AlipayApiException e) {
            log.info("零钱提现异常原因:" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 支付宝异步通知地址
     *
     * @param request
     * @return
     */
    @RequestMapping("notify")
    @Transactional(rollbackFor = Exception.class)
    public void returnUrl(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = AliPayOrderUtil.convertRequestParamsToMap(request);
        String result = "";
        //调用SDK验证签名
        boolean signVerified = false;
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, AliPayConstants.RAS_PUBLIC_KEY,
                    AliPayConstants.CHARSET, AliPayConstants.SIGNTYPE);
        } catch (AlipayApiException e) {
            log.info("支付宝回调验签异常：" + e.getMessage());
            e.printStackTrace();
        }
        if (signVerified) {
            this.check(params);
            singleThreadPool.execute(() -> {
                AliPayNotifyParamConstants param = AliPayOrderUtil.buildAliPayNotifyParam(params);
                String tradeStatus = param.getTradeStatus();
                //支付成功
                if (tradeStatus.equalsIgnoreCase(AliPayConstants.TRADE_SUCCESS)) {
                    // TODO 处理业务逻辑
                }
            });
            result = "success";
        } else {
            result = "failure";
        }
        try {
            BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            outputStream.write(result.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            log.info("支付宝返回异常，异常信息为：" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 校验支付宝支付返回的订单信息是否正确
     *
     * @param params
     */
    private void check(Map<String, String> params) {
        // TODO 判断支付订单号是否是同一个
        String outTradeNo = params.get("out_trade_no");
        // 订单支付金额是否正确
        BigDecimal totalAmount = new BigDecimal(params.get("total_amount"));
        Assert.isTrue(!totalAmount.equals(new BigDecimal(0.2)), "支付金额错误");
        // 判断支付的商户信息是否一致
        Assert.isTrue(!params.get("app_id").equals(AliPayConstants.OPEN_ID), "支付的商户信息不正确");
    }

}
