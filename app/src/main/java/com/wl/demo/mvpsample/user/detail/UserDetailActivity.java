package com.wl.demo.mvpsample.user.detail;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.wl.demo.mvpsample.R;
import com.wl.demo.mvpsample.base.BaseActivity;
import com.wl.demo.mvpsample.domain.UserInfo;

import butterknife.ButterKnife;

/**
 * Created by wangliang on 16-10-14.
 */

public class UserDetailActivity extends BaseActivity implements UserDetailContact.View {

    private UserDetailContact.Presenter mPresenter;

    private TextView mNameTv;
    private TextView mAgeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        initActionBarWithBack("详细");

        mNameTv = ButterKnife.findById(this, R.id.tv_name);
        mAgeTv = ButterKnife.findById(this, R.id.tv_age);

        mPresenter = new UserDetailPresenterImpl(this, this);

        mPresenter.getData("111");
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
    public void getDataSucc(UserInfo info) {
        //TODO 页面处理
        mNameTv.setText(info.getName());
        mAgeTv.setText(String.valueOf(info.getAge()));
    }

    @Override
    public void getDataFailed(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }
}
