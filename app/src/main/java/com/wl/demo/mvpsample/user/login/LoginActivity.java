package com.wl.demo.mvpsample.user.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wl.demo.mvpsample.R;
import com.wl.demo.mvpsample.base.MVPActivity;
import com.wl.demo.mvpsample.net.resp.model.LoginResp;
import com.wl.demo.mvpsample.user.list.ListActivity;

import butterknife.ButterKnife;

/**
 * Created by wangliang on 16-10-20.
 */

public class LoginActivity extends MVPActivity<LoginPresenter> implements LoginContact.View {

    private EditText mAccEt;
    private EditText mPwdEt;
    private Button mLoginBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBarWithBack("登陆");
        setContentView(R.layout.activity_login);


        mAccEt = ButterKnife.findById(this, R.id.account_et);
        mPwdEt = ButterKnife.findById(this, R.id.pwd_et);
        mLoginBtn = ButterKnife.findById(this, R.id.login_btn);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mAccEt.getText().toString();
                String password = mPwdEt.getText().toString();

                presenter.login(username, password);
            }
        });
    }

    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter(this, this, getTagName());
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void loginSucc(LoginResp data) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    @Override
    public void loginFailed(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getTagName() {
        return LoginActivity.class.getSimpleName();
    }
}
