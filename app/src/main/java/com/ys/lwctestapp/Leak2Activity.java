package com.ys.lwctestapp;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 *
 * 内存分析
 *
 */

public class Leak2Activity extends AppCompatActivity {

    public static void launch(Context context) {
        Intent intent = new Intent(context, Leak2Activity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak2);
        postHandler();
    }

    private void postHandler() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },999999999);
    }
}
