package com.wl.demo.mvpsample;

import android.app.Application;

import com.wl.demo.mvpsample.utils.SharedPrefUtil;

/**
 * Created by wangliang on 16-10-20.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPrefUtil.init(this);
    }
}
