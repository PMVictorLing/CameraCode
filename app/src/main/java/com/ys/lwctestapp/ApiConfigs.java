package com.ys.lwctestapp;

/**
 * @author lwc
 * 2019/6/24
 * 描述：$des$
 */
public class ApiConfigs {

    //     1.服务商号：1202836343 特约商户号：1202839779
    //     2.商户秘钥：23a247d596c24512aeeba7432ab94c66

    //测试 https://e.etest.tf.cn:4443/
    public static final String API_BASE = "https://e.etest.tf.cn:4443/";
//
//    正式
//    public static final String API_BASE = "https://apistores.jimutour.com:8443/";

    public static String getBaseApi() {
        return API_BASE;
    }

    //服务商号
    public static final String PARTNER_ID = "1202836343";

    //特约商户号
    public static final String SUBPARTNER_ID = "1202839779";

    //特约商户号
    public static final String PARTNER_KEY = "23a247d596c24512aeeba7432ab94c66";

    // Unified Order  查询二维码
    public static final String GENERATE_UNIFIED_ORDER = getBaseApi() + "tianfupay/trans/DynamicPayPreCreate";

    // 查询支付结果 https://apistores.jimutour.com:8443/apiworks/pay/scanqrcode/query
    public static final String GENERATE_ACANQRCODE_QUERY = getBaseApi() + "tianfupay/query/queryOrder";

    //关闭订单 https://apistores.jimutour.com:8443/apiworks/pay/scanqrcode/close
    public static final String PAY_SCANQRCODE_CLOSE = getBaseApi() + "";

    //退款 https://apistores.jimutour.com:8443/apiworks/pay/scanqrcode/refund
    public static final String PAY_REFUND = getBaseApi() + "tianfupay/pay/frontRefund";


}
