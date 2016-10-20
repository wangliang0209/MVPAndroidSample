package com.wl.demo.mvpsample.user.login;

import android.content.Context;

import com.wl.demo.mvpsample.net.CommonRequest;
import com.wl.demo.mvpsample.net.MySubscriber;
import com.wl.demo.mvpsample.user.login.model.LoginResp;
import com.wl.demo.mvpsample.utils.CurUserHelper;
import com.wl.demo.mvpsample.utils.SharedPrefUtil;

/**
 * Created by wangliang on 16-10-20.
 */

public class LoginPresenterImpl implements LoginContact.Presenter {

    private LoginContact.View mMainView;
    private CommonRequest mRequest;

    public LoginPresenterImpl(LoginContact.View view, Context context) {
        mMainView = view;
        mRequest = new CommonRequest(context);
    }

    @Override
    public void login(String username, String pwd) {
        mMainView.showProgress();
        mRequest.login(username, pwd, new MySubscriber<LoginResp>() {
            @Override
            public void onSucc(LoginResp loginResp) {
                mMainView.hideProgress();
                CurUserHelper.setCurUserId(loginResp.getUid());
                CurUserHelper.setCurUserToken(loginResp.getToken());
                mMainView.loginSucc(loginResp);
            }

            @Override
            public void onError(String error) {
                mMainView.hideProgress();
                mMainView.loginFailed(error);
            }
        });
    }
}
