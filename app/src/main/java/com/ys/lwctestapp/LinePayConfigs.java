package com.ys.lwctestapp;

/**
 * @author lwc
 * 2019/11/28
 * 描述：$des$
 */
public class LinePayConfigs {
    //测试
    public static final String API_BASE = "https://sandbox-api-pay.line.me";
//
//    正式
//    public static final String API_BASE = "https://apistores.jimutour.com:8443/";

    public static String getBaseApi() {
        return API_BASE;
    }

    // 查询二维码
    public static final String POST_PAYMENTS_ORDER = "/v3/payments/request";

    // 查询支付结果 https://apistores.jimutour.com:8443/apiworks/pay/scanqrcode/query
    public static final String GENERATE_ACANQRCODE_QUERY = getBaseApi() + "tianfupay/query/queryOrder";
}
