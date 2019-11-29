package com.ys.lwctestapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author lwc
 * 2019/6/25
 * 描述：$des$
 */
public class SPUtils {

    private static SPUtils mInstance;
    private final SharedPreferences mSharedPreferences;
    private String ORDER_KEY = "order_key";
    private String ACCESS_TOKEN = "access_token";
    private String USER_ID = "user_id";

    private SPUtils(Context context) {
        mSharedPreferences = context.getSharedPreferences("order_name", Context.MODE_PRIVATE);
    }

    /**
     * 私有的构造函数，公用的获取实例方法
     *
     * @param context
     * @return
     */
    public static SPUtils getInstance(Context context){
        if (mInstance == null){
            synchronized (SPUtils.class){
                if (mInstance == null){
                    mInstance = new SPUtils(context);
                }
            }
        }
        return mInstance;
    }

    public void setUserKey(String key){
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString(ORDER_KEY,key);
        edit.commit();
    }

    public String getUserKey(){
        return mSharedPreferences.getString(ORDER_KEY,"");
    }

    public void setAccessToken(String token){
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString(ACCESS_TOKEN,token);
        edit.commit();
    }

    public String getAccessToken(){
        return mSharedPreferences.getString(ACCESS_TOKEN,"");
    }

    public String getUserId(){
        return mSharedPreferences.getString(USER_ID,"");
    }

    public void setUserId(String uid){
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString(USER_ID,uid);
        edit.commit();
    }



}
