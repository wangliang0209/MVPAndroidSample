package com.wl.demo.mvpsample.user.login;

import com.wl.demo.mvpsample.user.login.model.LoginResp;

/**
 * Created by wangliang on 16-10-20.
 */

public interface LoginContact {
    interface View {
        void showProgress();
        void hideProgress();

        void loginSucc(LoginResp data);
        void loginFailed(String error);
    }

    interface Presenter {
        void login(String username, String pwd);
    }
}
