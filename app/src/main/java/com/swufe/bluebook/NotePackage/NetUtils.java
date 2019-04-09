package com.swufe.bluebook.NotePackage;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

public class NetUtils {
    public static boolean checkNetworkInfo(Context context) {

        ConnectivityManager conMan = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressWarnings("deprecation")
        NetworkInfo mobileInfo = conMan
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        @SuppressWarnings("deprecation")
        NetworkInfo wifiInfo = conMan
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mobileInfo == null) {
            State wifiState = wifiInfo.getState();
            if (wifiState == State.CONNECTED || wifiState == State.CONNECTING) {
                return true;
            } else {
                return false;
            }
        }

        State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .getState();
        State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState();
        if ((mobile == State.CONNECTED || mobile == State.CONNECTING
                || wifi == State.CONNECTED || wifi == State.CONNECTING)) {
            return true;
        } else {
            return false;
        }
    }



    /**
     * 检查当前网络是否可用
     *
     */
    @SuppressWarnings("deprecation")
    public static boolean isNetworkAvailable(Context activity) {
        //得到应用上下文
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）  notificationManager /alarmManager
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
