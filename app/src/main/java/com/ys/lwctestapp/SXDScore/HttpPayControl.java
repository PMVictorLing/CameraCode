//package com.ys.lwctestapp.SXDScore;
//
//
//import java.text.SimpleDateFormat;
//import java.util.HashMap;
//
//
//public class HttpPayControl {
//
//    private static HttpPayControl httpControl;
//    private long beforeHttpTime;
//    private STATES states;
//    long BLANKINGTIME = 4000;
//    long PAYAllTIME = 90 * 1000;
//    String shipMentOrder;
//    OrderInfo queryInfo;
//    UserInfo userInfo;
//    Map<String, OrderInfo> shipMentMap = new HashMap<>();
//
//    enum STATES {
//        NORMAL, CODE, QUERYING, SHIPMENT, CANCEL, PAYING
//    }
//
//    public static synchronized HttpPayControl getInstall() {
//        if (httpControl == null) {
//            httpControl = new HttpPayControl();
//        }
//        return httpControl;
//    }
//
//    public void httpCode(String user, String pwd) {
//        user = "18635153517";
//        pwd = "123123";
//        HttpPayRequest.getInstall().httpCode(user, pwd, new HttpInterface.HttpResult<UserInfo>() {
//            @Override
//            public void onSuccess(int result, String msg, UserInfo t) {
//
//            }
//
//            @Override
//            public void onFail(int result, String msg) {
//
//            }
//        });
//    }
//
//
//    OrderInfo getOrderInfo(Coil_info coil_info) {
//        OrderInfo orderInfo = new OrderInfo();
//        orderInfo.setGoodsName(coil_info.getPar_name());
//        orderInfo.setGoodsPrice(coil_info.getPar_price());
//        orderInfo.setSlot(coil_info.getCoil_id());
//        orderInfo.setTradeOrder(getOrderData());
//        return orderInfo;
//    }
//
//
//    void logx(String msg) {
//        TcnVendIF.getInstance().LoggerDebug("httpControl", msg);
//    }
//
//    public void toastRefund(final String msg, final int numCount) {
////        CustomToast.getsToastSign(VendApplication.getContext(), msg);
//    }
//
//    void sendCode(int result, String msg) {
//        if (result < 1) {
//            states = STATES.NORMAL;
//        }
//        logx(result + "  " + msg);
//        TcnVendIF.getInstance().sendMsgToUI(PayInfoBean.SENDCODE, result, 1, 1, msg);
//    }
//
//    String getOrderData() {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmss");
//        return simpleDateFormat.format(System.currentTimeMillis()) + TcnShareUseData.getInstance().getMachineID();
//    }
//
//}
