package com.wl.demo.mvpsample.user.detail;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.wl.demo.mvpsample.R;
import com.wl.demo.mvpsample.base.MVPActivity;
import com.wl.demo.mvpsample.net.resp.model.UserDetailResp;

import butterknife.ButterKnife;

/**
 * Created by wangliang on 16-10-14.
 */

public class UserDetailActivity extends MVPActivity<UserDetailPresenter> implements UserDetailContact.View {

    private TextView mNameTv;
    private TextView mAgeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        initActionBarWithBack("详细");

        mNameTv = ButterKnife.findById(this, R.id.tv_name);
        mAgeTv = ButterKnife.findById(this, R.id.tv_age);


        presenter.getData("111");
    }

    @Override
    protected UserDetailPresenter initPresenter() {
        return new UserDetailPresenter(this, this, getTagName());
    }

    @Override
    public void showProgress() {
        //TODO 显示loading
    }

    @Override
    public void hideProgress() {
        //TODO 隐藏loading
    }

    @Override
    public void getDataSucc(UserDetailResp info) {
        //TODO 页面处理
        mNameTv.setText(info.getName());
        mAgeTv.setText(String.valueOf(info.getAge()));
    }

    @Override
    public void getDataFailed(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public String getTagName() {
        return UserDetailActivity.class.getSimpleName();
    }
}
