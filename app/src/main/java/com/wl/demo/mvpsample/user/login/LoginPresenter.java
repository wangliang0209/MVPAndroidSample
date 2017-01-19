package com.wl.demo.mvpsample.user.login;

import android.content.Context;

import com.wl.demo.mvpsample.base.BasePresenter;
import com.wl.demo.mvpsample.net.CommonRequest;
import com.wl.demo.mvpsample.net.MySubscriber;
import com.wl.demo.mvpsample.net.resp.model.LoginResp;
import com.wl.demo.mvpsample.utils.CurUserHelper;

/**
 * Created by wangliang on 16-10-20.
 */

public class LoginPresenter extends BasePresenter<LoginContact.View> {


    public LoginPresenter(LoginContact.View view, Context context, String tagName) {
        super(view, context, tagName);
    }

    public void login(String username, String pwd) {
        v.showProgress();
        new CommonRequest(mContext, mTagName).login(username, pwd, new MySubscriber<LoginResp>() {
            @Override
            public void onSucc(LoginResp loginResp) {
                v.hideProgress();
                CurUserHelper.setCurUserId(loginResp.getUid());
                CurUserHelper.setCurUserToken(loginResp.getToken());
                v.loginSucc(loginResp);
            }

            @Override
            public void onError(String error) {
                v.hideProgress();
                v.loginFailed(error);
            }
        });
    }
}
