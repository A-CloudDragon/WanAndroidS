package com.jiyun.wanandroids.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class BaseApp extends Application {
    private static Application mApp;
    private static Map<String, Object> mMap;
    private static SharedPreferences mSp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        mMap = new HashMap<String, Object>();
        mSp = getSharedPreferences("config", MODE_PRIVATE);
    }

    public static Application getApp() {
        return mApp;
    }

    public static Context getContext() {
        return mApp.getApplicationContext();
    }

    public static void setObj(String key, Object obj) {
        if (mMap != null)
            mMap.put(key, obj);
    }

    public static Object getObj(String key) {
        if (mMap != null && mMap.size() > 0)
            return mMap.get(key);
        return null;
    }

    public static void releaseMap() {
        if (mMap != null)
            mMap.clear();
        mMap = null;
    }


    public static SharedPreferences getmSp() {
        if (mSp != null)
            return mSp;
        return null;
    }
}
