package com.ys.lwctestapp.http;

import android.content.Context;

import java.util.Map;

/**
 * Created by lingwancai on
 * 2018/9/29 14:56
 */
public interface EngineCallBack {
    //开始执行，执行之前会回调的方法
    void onPreExecute(Context context, Map<String, Object> params);

    //错误
    void OnError(Exception e);

    //成功 --- 返回对象会出问题 成功 data对象{"",""}  ;  失败 data : ""
    void Success(String result);


    //默认
    public final EngineCallBack DEFUALT_CALL_BACK = new EngineCallBack() {
        @Override
        public void onPreExecute(Context context, Map<String, Object> params) {

        }

        @Override
        public void OnError(Exception e) {

        }

        @Override
        public void Success(String result) {

        }
    };
}
