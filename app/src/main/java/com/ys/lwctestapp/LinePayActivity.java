package com.ys.lwctestapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ys.lwctestapp.http.HttpUtils;
import com.ys.lwctestapp.http.model.HttpCallBack;

import java.util.HashMap;

public class LinePayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_pay);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String,Object> mParm = new HashMap<>();

                HttpUtils.with(LinePayActivity.this).Url(LinePayConfigs.getBaseApi()+LinePayConfigs.POST_PAYMENTS_ORDER).addParam(mParm)
                        .post().execute(new HttpCallBack<String>() {
                    @Override
                    public void OnError(Exception e) {
                        Log.e("TAG","OnError e=>"+e.getMessage());

                    }

                    @Override
                    protected void onPreExecute() {

                    }

                    @Override
                    public void onSuccess(String result) {
                        Log.e("TAG","onSuccess result=>"+result);
                    }
                });

            }
        });


        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
