package com.ys.lwctestapp.http.model;

import android.content.Context;

import com.google.gson.Gson;
import com.ys.lwctestapp.http.EngineCallBack;
import com.ys.lwctestapp.http.HttpUtils;

import java.util.Map;

/**
 * 专门处理请求业务逻辑 实现 EngineCallBack
 * <p>
 * Created by lingwancai on
 * 2018/9/30 13:54
 */
public abstract class HttpCallBack<T> implements EngineCallBack {
    @Override
    public void onPreExecute(Context context, Map<String, Object> params) {
        //大大方方的添加公用的参数
        //与项目业务逻辑有关
        //必须包含的参数  项目名称 context ...
     /*   params.put("", "");
        params.put("", "");
        params.put("", "");
        params.put("", "");
        params.put("", "");*/


        onPreExecute();
    }

    //开始执行了
    protected abstract void onPreExecute();


    //返回可以操作的对象
    public abstract void onSuccess(T result);


    @Override
    public void Success(String result) {
        Gson gson = new Gson();
        T resultObject = (T) gson.fromJson(result, HttpUtils.analysisClazzInfo(this));
        onSuccess(resultObject);
    }
}
