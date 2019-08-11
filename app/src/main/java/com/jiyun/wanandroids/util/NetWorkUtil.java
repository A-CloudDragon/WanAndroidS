package com.jiyun.wanandroids.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWorkUtil {

    public static NetworkInfo getActiveNetWorkInfo(Context conext) {
        try {
            ConnectivityManager manager = (ConnectivityManager) conext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
            return activeNetworkInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //判断是否有网
    public static boolean isConnected(Context context) {
        NetworkInfo activeNetWorkInfo = getActiveNetWorkInfo(context);
        if (activeNetWorkInfo != null) {
            return activeNetWorkInfo.isConnected();
        } else
            return false;
    }

    //判断是否是WIFI连接
    public static boolean isWIFI(Context context) {
        NetworkInfo activeNetWorkInfo = getActiveNetWorkInfo(context);
        if (activeNetWorkInfo != null) {
            int type = activeNetWorkInfo.getType();
            if (type == ConnectivityManager.TYPE_WIFI)
                return true;
            else
                return false;
        } else
            return false;
    }
}
