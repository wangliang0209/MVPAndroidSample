package com.wl.demo.mvpsample.utils;

/**
 * Created by wangliang on 16-10-20.
 */

public class CurUserHelper {

    public static void setCurUserId(String uid) {
        SharedPrefUtil.put("uid", uid);
    }

    public static String getCurUserId() {
        return SharedPrefUtil.get("uid", "");
    }

    public static void setCurUserToken(String token) {
        SharedPrefUtil.put("token", token);
    }

    public static String getCurUserToken() {
        return SharedPrefUtil.get("token", "");
    }
}
