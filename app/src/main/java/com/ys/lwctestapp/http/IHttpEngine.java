package com.ys.lwctestapp.http;

import android.content.Context;

import java.util.Map;

/**
 * 引擎的规范
 * <p>
 * Created by lingwancai on
 * 2018/9/29 14:53
 */
public interface IHttpEngine {
    //get请求
    void get(Context context, String url, Map<String, Object> params, EngineCallBack callBack);

    //post请求
    void post(Context context, String url, Map<String, Object> params, EngineCallBack callBack);

    //文件下载

    //文件上传

    //https 添加证书
}
