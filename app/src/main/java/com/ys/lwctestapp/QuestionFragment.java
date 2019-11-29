package com.ys.lwctestapp;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 * <p>
 * 答题
 */
public class QuestionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //考试地址
    private String loadUrl = "";


    public QuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionFragment newInstance(String param1, String param2) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.app_add_question, container, false);
        WebView webView = view.findViewById(R.id.webView);
        initWebview(webView);
        return view;
    }

    private void initWebview(WebView webView) {
        WebSettings wSettings = webView.getSettings();
        // 启用触控缩放
        wSettings.setBuiltInZoomControls(true);
        // 启用支持视窗meta标记（可实现双击缩放）
        wSettings.setUseWideViewPort(true);
        // 以缩略图模式加载页面
        wSettings.setLoadWithOverviewMode(true);
        // 启用JavaScript支持
        wSettings.setJavaScriptEnabled(true);
        wSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        if (Build.VERSION.SDK_INT >= 19) {
            wSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        }
        // 设置将接收各种通知和请求的WebViewClient（在WebView加载所有的链接）
        wSettings.setDisplayZoomControls(false);


        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);

                try {
                    if (url.startsWith("weixin://") //微信
                            || url.startsWith("alipays://") //支付宝
                            || url.startsWith("mailto://") //邮件
                            || url.startsWith("tel://")//电话
                            || url.startsWith("dianping://")//大众点评
                            || url.startsWith("tbopen://")//淘宝
                            || url.startsWith("openapp.jdmobile://")//淘宝
                            || url.startsWith("tmast://")//淘宝

                        //其他自定义的scheme
                            ) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        if (QuestionFragment.this.getActivity() != null)
                            QuestionFragment.this.getActivity().startActivity(intent);
                        return true;
                    }
                } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return true;//没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
                }


                //重定向处理 1
                WebView.HitTestResult hitTestResult = view.getHitTestResult();
                int hitType = hitTestResult.getType();
                if (hitType != WebView.HitTestResult.UNKNOWN_TYPE) {
                    Log.e("WebViewManger", "没有进行重定向操作 url=>" + url);
                    //这里执行自定义的操作
                    view.loadUrl(url);
                    return true;
                } else {
                    Log.e("WebViewManger", "进行了重定向操作 url=>" + url);
                    //重定向时hitType为0 ,执行默认的操作
                    return false;
                }

               /* //重定向处理 2
                if (startUrl != null && startUrl.equals(url)) {
                    Log.e("WebViewManger","没有进行重定向操作");
                    view.loadUrl(url);
                } else {
                    //交给系统处理
                    Log.e("WebViewManger","进行了重定向操作");
                    return super.shouldOverrideUrlLoading(view, url);
                }
                return true;*/
            }

            public void onPageFinished(WebView view, String url) {
                Log.e("WebViewManger", "onPageFinished url=>" + url);
                super.onPageFinished(view, url);
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                startUrl = url;
                Log.e("WebViewManger", "onPageStarted url=>" + url);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //handler.cancel(); // Android默认的处理方式
                handler.proceed();  // 接受所有网站的证书
                //handleMessage(Message msg); // 进行其他处理
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.e("onReceivedError", "" + " getDescription=>" + error.getDescription().toString() + " getErrorCode=>" + error.getErrorCode());
                if (error != null) {
                    if (error.getErrorCode() == -2) {
//                        showErrorPage();
                    }
                }
            }
        });
        //http://gaoyans.cross.echosite.cn/hamburger/index
        //http://gaoyans.cross.echosite.cn/hamburger/Category
        //http://gaoyans.cross.echosite.cn/hamburger/index
//        webView.loadUrl("http://gaoyans.cross.echosite.cn/hamburger/index");

        // http://gaoyans.cross.echosite.cn/hamburger/index?url=UserCenter
        loadUrl = "https://wx.sxledao.com/app/index.php?i=2&c=entry&do=index&m=online_testck163" + "&uid=" + SPUtils.getInstance(this.getActivity()).getUserId() + "&mechine=1";
        Log.e("webview url", "url==>" + loadUrl);

        //第一个参数把自身传给js 第二个参数是this的一个名字
        //这个方法用于让H5调用android方法
        webView.addJavascriptInterface(new InJavaScript(), "injs");
        //添加
        //设置可以访问文件
        wSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
//        wSettings.setBlockNetworkImage(true);//设置这个图片显示就有问题
        wSettings.setAllowFileAccess(true);
        wSettings.setAppCacheEnabled(true);
        wSettings.setSaveFormData(false);
        wSettings.setLoadsImagesAutomatically(true);
        wSettings.setSupportZoom(false);
        wSettings.setDomStorageEnabled(true);

        //设置http和https混合模式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {// MIXED_CONTENT_COMPATIBILITY_MODE
            wSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        //设置cookie
        ArrayList<String> cookieList = new ArrayList<>();
        cookieList.add("key=" + SPUtils.getInstance(QuestionFragment.this.getActivity()).getUserKey());
        syncCookie(loadUrl, cookieList);

        webView.loadUrl(loadUrl);
//        webView.loadUrl("file:///android_asset/image_url.html");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * h5调用
     */
    private class InJavaScript {

        /**
         * 获取用户key
         *
         * @return
         */
        @JavascriptInterface
        public String onGetUserKeys() {
            return SPUtils.getInstance(QuestionFragment.this.getActivity()).getUserKey();
        }
    }


    /**
     * 同步cookie
     *
     * @param url        地址
     * @param cookieList 需要添加的Cookie值,以键值对的方式:key=value
     */
    private void syncCookie(String url, ArrayList<String> cookieList) {
        CookieSyncManager.createInstance(this.getActivity());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        if (cookieList != null && cookieList.size() > 0) {
            for (String cookie : cookieList) {
                cookieManager.setCookie(url, cookie);
            }
        }
//        cookieManager.setCookie(url, "Domain=.zyb.com");
        cookieManager.setCookie(url, "Path=/");
        String cookies = cookieManager.getCookie(url);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.flush();
        } else {
            CookieSyncManager.getInstance().sync();
        }
    }

}
