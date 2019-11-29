package com.ys.lwctestapp.SXDScore;


import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HttpPayRequest {
    static HttpPayRequest httpPayRequest;
    private static OkHttpClient client;

    String path = "http://maslog.com.my/nugateway/getqrcode.aspx";
    private String mac;
    int x = 0;

    public static synchronized HttpPayRequest getInstall() {
        if (httpPayRequest == null) {
            httpPayRequest = new HttpPayRequest();
            client = new OkHttpClient().newBuilder()
                    .connectTimeout(8000, TimeUnit.MILLISECONDS)
                    .readTimeout(8000, TimeUnit.MILLISECONDS)
                    .sslSocketFactory(createSSLSocketFactory())
                    .hostnameVerifier(new TrustAllHostnameVerifier())
                    .build();
        }
        return httpPayRequest;
    }

    public void httpLogin(String token, String user, String pwd, final HttpInterface.HttpResult<UserInfo> httpInterface) {
        final String method="httpLogin";
        final String url = "https://wx.sxledao.com/app/index.php?i=2&c=entry&do=login&m=online_testck163";
        logx("httpLogin url：" + url + "  " + token);
        FormBody.Builder builder = new FormBody.Builder().add
                ("username", user)
                .add("password", pwd)
                .add("do_submit", "1")
                .add("token", token)
                .add("mechine", "1");
        Request request = new Request.Builder()
                .url(url)
                .addHeader("name", "dsfas")
                .post(builder.build())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                httpInterface.onFail(-1, BeanMsg.ERROR_NET);
                e.printStackTrace();
                logx(method," onFailure 错误: " + e.toString());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String res = response.body().string();
                    logx(method, " res: " + res);
                    if (res.toLowerCase().startsWith("string(")) {
                        int x = res.indexOf("{");
                        int y = res.lastIndexOf("}");
                        String json = res.substring(x , y+1);
                        logx(method, " onResponse: " + json);
                        UserInfo userInfo = new Gson().fromJson(json, UserInfo.class);
                        httpInterface.onSuccess(1,"",userInfo);
                    }
                } catch (Exception e) {
                    httpInterface.onFail(-1, BeanMsg.ERROR_DATA);
                    e.printStackTrace();
                    logx(method," onResponse 错误: " + e.toString());
                }

//                httpScore();
            }
        });

    }


    public void httpScore(String user,String pwd,HttpInterface<UserInfo> httpInterface) {
//        https://wx.sxledao.com/app/index.php?i=2&c=entry&do=member&m=online_testck163
        final String url = "https://wx.sxledao.com/app/index.php?i=2&c=entry&do=member&m=online_testck163&&mechine=1&op=delcredit1&uid=1&credit1=10";
        logx("httpScore url：" + url);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                logx("HttpPayRequest", "httpScore onResponse: " + res);
                if (res.toLowerCase().startsWith("string(")) {
                    int x = res.indexOf("{");
                    int y = res.lastIndexOf("}");
                    String json = res.substring(x , y+1);
                    logx("HttpPayRequest", "httpScore onResponse: " + json);
                    UserInfo userInfo = new Gson().fromJson(json, UserInfo.class);
                    logx("HttpPayRequest", "userInfo: "+userInfo.getName());
                }
            }
        });

    }


    /**
     * 请求token
     *
     *
     * @param user
     * @param pwd
     * @param httpInterface
     */
    public void httpCode(final String user, final String pwd, final HttpInterface.HttpResult<UserInfo> httpInterface) {
        final String url = "https://wx.sxledao.com/app/index.php?i=2&c=entry&do=token&m=online_testck163";
        logx("httpCode url：" + url);
        Request request = new Request.Builder()
                .url(url).get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                httpInterface.onFail(-1, BeanMsg.ERROR_NET);
                e.printStackTrace();
                logx("httpCode onFailure 错误: " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String rse = response.body().string();
                    logx("httpCode onResponse: " + rse+" res = "+rse.length());
                    int i = rse.indexOf(" string(4)");
                    logx("httpCode onResponse: " + rse+" i = "+i);

                    if (i > 0 && i + 16 < rse.length()) {
                        String token = rse.substring(i + 12, i + 16);
                        logx("httpCode: " + token);
                        httpLogin(token, user, pwd, httpInterface);
                    } else {
                        logx("httpCode onResponse 错误: i > 0 && i + 16 < rse.length()" );
                        httpInterface.onFail(-1, BeanMsg.ERROR_DATA);
                    }
                } catch (Exception e) {
                    httpInterface.onFail(-1, BeanMsg.ERROR_DATA);
                    e.printStackTrace();
                    logx("httpCode onResponse 错误: " + e.toString());
                }


            }
        });

    }


    void logx(String s) {
        Log.e("login","token=>"+s);
//        TcnVendIF.getInstance().LoggerDebug("HttpPayRequest", s);
    }


    void logx(String fun, String msg) {
        Log.e(fun,"msg=>"+msg);
//        TcnVendIF.getInstance().LoggerDebug("httpPayRequest", fun + ": " + msg);

    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return ssfFactory;
    }

    private static class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

    }
}
