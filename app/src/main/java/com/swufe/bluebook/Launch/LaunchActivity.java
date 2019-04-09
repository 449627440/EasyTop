package com.swufe.bluebook.Launch;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.swufe.bluebook.Backstage.User;
import com.swufe.bluebook.CityChooseSlide.AddressActivity;
import com.swufe.bluebook.LoginOrRegister.LoginActivity;
import com.swufe.bluebook.MainActivity;
import com.swufe.bluebook.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class LaunchActivity extends Activity implements OnRequestPermissionsResultCallback{

    private static String TAG = "LaunchActivity";
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);//设置开机图

        Bmob.initialize(this, "9b90c2d2f64466ed4e8c1bef6c11099a");

        User bmobUser = BmobUser.getCurrentUser(User.class);
        handler=new Handler();
        if(bmobUser != null){
            // 允许用户使用

            if(BmobUser.getObjectByKey("city")==null){
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(LaunchActivity.this, AddressActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },5000);

            }else {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },5000);
            }


        }else{
            //缓存用户对象为空时， 可打开用户注册界面…
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(LaunchActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            },5000);

        }
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    permissionList.toArray(new String[permissionList.size()]), 1002);
        } else {
            Toast.makeText(this, "多个权限你都有了，不用再次申请", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1002:
                if (grantResults.length > 0) {
                    // 因为是多个权限，所以需要一个循环获取每个权限的获取情况
                    for (int i = 0; i < grantResults.length; i++) {
                        // PERMISSION_DENIED 这个值代表是没有授权，我们可以把被拒绝授权的权限显示出来
                        if (grantResults[i] == PackageManager.PERMISSION_DENIED){
                            Toast.makeText(LaunchActivity.this, permissions[i] + "权限被拒绝了", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

}

