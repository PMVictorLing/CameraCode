package com.ys.lwctestapp;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ys.lwctestapp.SXDScore.HttpInterface;
import com.ys.lwctestapp.SXDScore.HttpPayRequest;
import com.ys.lwctestapp.SXDScore.UserInfo;
import com.ys.lwctestapp.fragment.AutoCompleteTextViewFragment;
import com.ys.lwctestapp.tools.FileServerTools;
import com.ys.lwctestapp.tools.PermissionsUtils;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static com.ys.lwctestapp.tools.FileServerTools.getAesKey;
import static com.ys.lwctestapp.tools.FileServerTools.getMachineID;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks,
        EasyPermissions.RationaleCallbacks{

    private static final String TAG = "MainActivity";
    private static final int MSG_PROGRESS_UPDATE = 1000;
    private FrameLayout mFrameLayout;
    private FragmentManager m_fragmentManager;
    private Fragment mQuestionFragment;
    String[] permiss={Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
    private ProgressBar mProgressBarNumer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StringBuilder goodsImageSavePath = new StringBuilder();
        String s = "/mnt/sdcard/TcnFolder/ImageGoods/https://i.ourvend.com/app/Files/UploadFiles";
        goodsImageSavePath.append(s);
        if ((goodsImageSavePath.toString().contains("https://"))){//从https删除
            goodsImageSavePath.delete(goodsImageSavePath.indexOf("https"),goodsImageSavePath.length());
        }
        Log.e("goodsImageSavePath","goodsImageSavePath=>"+goodsImageSavePath.toString());

        findViewById(R.id.test_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHttpLogin("","");
//                hasStoragePermission();
                // Ask for one permission
                EasyPermissions.requestPermissions(MainActivity.this,
                        "文件权限",
                        101,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);

               /* PermissionsUtils.getInstance().chekPermissions(MainActivity.this, permiss, new PermissionsUtils.IPermissionsResult() {
                    @Override
                    public void passPermissons() {

                    }
                    @Override
                    public void forbitPermissons() {
                    }
                });*/


            }
        });
        findViewById(R.id.check_scare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"machineId="+getMachineID()+"\n"+"keys="+getAesKey(),Toast.LENGTH_LONG).show();

            }
        });

        //截取字符串
        String url = "array(2) {       [\"status\"]=>       int(1)       [\"data\"]=>       string(4) \"Eggb\"     }";
        int startindex  = url.indexOf(" string(4)");
        Log.e("token"," startindex="+startindex);
        if (startindex == 0){

        }

        mFrameLayout = findViewById(R.id.frameLayout);
        
        //自定义进度条
        mProgressBarNumer = findViewById(R.id.progressbar_number);
        mHandler.sendEmptyMessage(MSG_PROGRESS_UPDATE);

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int progress = mProgressBarNumer.getProgress();
            mProgressBarNumer.setProgress(++progress);
            if (progress >= 100) {
                mHandler.removeMessages(MSG_PROGRESS_UPDATE);
            }
            mHandler.sendEmptyMessageDelayed(MSG_PROGRESS_UPDATE, 100);
        };
    };


    public void onHttpLogin(String user, String pwd) {
        user = "18635153517";//18635153517
        pwd = "123123";
        HttpPayRequest.getInstall().httpCode(user, pwd, new HttpInterface.HttpResult<UserInfo>() {
            @Override
            public void onSuccess(int result, String msg, final UserInfo t) {

                Log.e("login","UserInfo t=>"+t);
                if (t!=null){
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SPUtils.getInstance(MainActivity.this).setUserId(t.getUid());
                            SPUtils.getInstance(MainActivity.this).setUserKey(t.getKey());
                            getFragmentTransaction();
                            Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }

            @Override
            public void onFail(int result, String msg) {
                Log.e("login","msg =>"+msg+" result=>"+result);
            }
        });
    }

    private FragmentTransaction getFragmentTransaction() {
        m_fragmentManager = this.getFragmentManager();
        FragmentTransaction fragmentTransaction = m_fragmentManager.beginTransaction();
            //token 为空

        //h5
               /* if (mQuestionFragment == null) {
                    mQuestionFragment = new QuestionFragment();
                    fragmentTransaction.add(R.id.frameLayout, mQuestionFragment);
                } else {
                    if (!mQuestionFragment.isAdded()) {
                        fragmentTransaction.remove(mQuestionFragment);
                        mQuestionFragment = new QuestionFragment();
                        fragmentTransaction.add(R.id.frameLayout, mQuestionFragment);
                    }
                }
        fragmentTransaction.show(mQuestionFragment).commit();*/

        if (mQuestionFragment == null) {
            mQuestionFragment = new AutoCompleteTextViewFragment();
            fragmentTransaction.add(R.id.frameLayout, mQuestionFragment);
        } else {
            if (!mQuestionFragment.isAdded()) {
                fragmentTransaction.remove(mQuestionFragment);
                mQuestionFragment = new AutoCompleteTextViewFragment();
                fragmentTransaction.add(R.id.frameLayout, mQuestionFragment);
            }
        }
        fragmentTransaction.show(mQuestionFragment).commit();

        return fragmentTransaction;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
        //存入机器id
        String Mid = "1911260001";
        String PriKey = "E188F579F6BF52B7B382EF56DF07DCF80523E7FD3C578E4BE2F1F1526CCB8A9F";
        String machineInfo = Mid + "|" + PriKey;
        FileServerTools.saveMachineInfo(machineInfo);
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            String yes = "yes";
            String no = "no";
            hasCameraPermission() ;
            hasStoragePermission();
            hasSmsPermission();
            // Do something after user returned from app settings screen, like showing a Toast.
        }
    }


    private boolean hasCameraPermission() {
        return EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA);
    }

    private boolean hasSmsPermission() {
        return EasyPermissions.hasPermissions(this, Manifest.permission.READ_SMS);
    }

    private boolean hasStoragePermission() {
        return EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    public void onRationaleAccepted(int requestCode) {
        Log.d(TAG, "onRationaleAccepted:" + requestCode);

    }

    @Override
    public void onRationaleDenied(int requestCode) {
        Log.d(TAG, "onRationaleDenied:" + requestCode);
    }
}
