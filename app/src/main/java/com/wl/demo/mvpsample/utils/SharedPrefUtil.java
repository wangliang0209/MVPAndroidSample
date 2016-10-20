package com.wl.demo.mvpsample.utils;

import android.content.Context;
import android.text.TextUtils;

/**
 * 偏好工具类
 *
 * @author yingjun fan
 */
public final class SharedPrefUtil {
    public static final String YHT_PREF_FILE_NAME = "mvp_pref";
    private static Context mContext;
    private static int MODE = Context.MODE_MULTI_PROCESS;

    private SharedPrefUtil() {
        //Utility classes should not have a public or default constructor.
    }

    /**
     * 初始化工具类
     *
     * @param context
     */
    public static void init(Context context) {
        mContext = context;
    }

    /**
     * 删除一个偏好键值对
     *
     * @param key
     */
    public static void remove(String key) {
        if (mContext != null) {
            mContext.getSharedPreferences(YHT_PREF_FILE_NAME, MODE).edit().remove(key).commit();
        }
    }

    /**
     * 添加一个偏好键值
     *
     * @param key
     * @param value 布尔型
     */
    public static void put(String key, boolean value) {
        if (mContext != null) {
            mContext.getSharedPreferences(YHT_PREF_FILE_NAME, MODE).edit().putBoolean(key, value).commit();
        }

    }

    /**
     * 获取偏好设置
     *
     * @param key
     * @param defValue
     * @return :true第一次进入
     */
    public static boolean get(String key, boolean defValue) {
        if (mContext != null) {
            return mContext.getSharedPreferences(YHT_PREF_FILE_NAME, MODE).getBoolean(key, defValue);
        }
        return defValue;
    }

    /**
     * 添加一个偏好键值
     *
     * @param key
     * @param value
     */
    public static void put(String key, String value) {
        if (mContext != null) {
            mContext.getSharedPreferences(YHT_PREF_FILE_NAME, MODE).edit().putString(key, value).commit();
        }

    }

    /**
     * 添加一个偏好键值
     *
     * @param key
     * @param value int型
     */
    public static void put(String key, int value) {
        if (mContext != null) {
            mContext.getSharedPreferences(YHT_PREF_FILE_NAME, MODE).edit().putInt(key, value).commit();
        }

    }

    /**
     * 获取偏好设置
     *
     * @param key
     * @param defValue
     */
    public static int get(String key, int defValue) {
        if (mContext != null) {
            return mContext.getSharedPreferences(YHT_PREF_FILE_NAME, MODE).getInt(key, defValue);
        }
        return defValue;
    }

    /**
     * 添加一个偏好键值
     *
     * @param key
     * @param value long型
     */
    public static void put(String key, long value) {
        if (mContext != null) {
            mContext.getSharedPreferences(YHT_PREF_FILE_NAME, MODE).edit().putLong(key, value).commit();
        }

    }

    /**
     * 获取偏好设置
     *
     * @param key
     * @param defValue
     */
    public static long get(String key, long defValue) {
        if (mContext != null) {
            return mContext.getSharedPreferences(YHT_PREF_FILE_NAME, MODE).getLong(key, defValue);
        }
        return defValue;
    }

    /**
     * 获取偏好设置
     *
     * @param key
     * @param defValue
     * @return
     */
    public static String get(String key, String defValue) {
        if (mContext != null) {
            String value = mContext.getSharedPreferences(YHT_PREF_FILE_NAME, MODE).getString(key, defValue);
            if (!TextUtils.isEmpty(value)) {
                return value;
            }
        }
        return defValue;
    }

    /**
     * 提交偏好
     *
     * @param context
     */
    public static void commit(Context context) {
        mContext = null;
        /*if (mSharedPrefMap != null) {
            Editor editor = context.getSharedPreferences(YHT_PREF_FILE_NAME, 0).edit();
            for (Map.Entry<String, String> entry : mSharedPrefMap.entrySet()) {
                editor.putString(entry.getKey(), entry.getValue());
            }
            editor.commit();
        }*/
    }


}
