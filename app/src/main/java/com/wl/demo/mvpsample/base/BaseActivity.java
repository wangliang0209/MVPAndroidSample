package com.wl.demo.mvpsample.base;

import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by wangliang on 16-10-19.
 */

public class BaseActivity extends AppCompatActivity {

    public void initActionBarWithoutBack(int rid) {
        initActionBarWithoutBack(getString(rid));
    }

    public void initActionBarWithoutBack(String title) {
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    public void initActionBarWithBack(int rid) {
        initActionBarWithBack(getString(rid));
    }

    public void initActionBarWithBack(String title) {
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            optionsItemHomeMenuClick();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void optionsItemHomeMenuClick() {
        finish();
    }
}
