package com.jiyun.wanandroids.util;

import com.google.gson.Gson;

public class GsonUtil {

    public static Object str2Entity(String jsonStr, Class clazz) {
        Gson gson = new Gson();
        Object o = gson.fromJson(jsonStr, clazz);
        return o;
    }

}
