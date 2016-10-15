package com.wl.demo.mvpsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wl.demo.mvpsample.user.detail.UserDetailActivity;
import com.wl.demo.mvpsample.user.list.ListActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO command

    }

    protected void test(View v) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    protected void getUserById(View v) {
        Intent intent = new Intent(this, UserDetailActivity.class);
        startActivity(intent);
    }
}
