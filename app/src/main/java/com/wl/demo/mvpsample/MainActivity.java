package com.wl.demo.mvpsample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wl.demo.mvpsample.base.BaseActivity;
import com.wl.demo.mvpsample.net.SubscriptionManager;
import com.wl.demo.mvpsample.upload.UploadActivity;
import com.wl.demo.mvpsample.user.detail.UserDetailActivity;
import com.wl.demo.mvpsample.user.login.LoginActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActionBarWithoutBack("MVPDemo");
    }

    public void test(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void getUserById(View v) {
        Intent intent = new Intent(this, UserDetailActivity.class);
        startActivity(intent);
    }

    public void gotoUploadDemo(View v) {
        Intent intent = new Intent(this, UploadActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SubscriptionManager.getInstance().cancelPendingRequests(this);
    }
}
