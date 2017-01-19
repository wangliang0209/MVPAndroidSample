package com.wl.demo.mvpsample.user.detail;

import com.wl.demo.mvpsample.net.MySubscriber;
import com.wl.demo.mvpsample.net.resp.model.UserDetailResp;

/**
 * Created by wangliang on 16-10-14.
 */

public interface UserDetailContact {
    interface View {
        void showProgress();

        void hideProgress();

        void getDataSucc(UserDetailResp userInfo);

        void getDataFailed(String error);
    }

    interface Interactor {
        void getData(String uid, MySubscriber<UserDetailResp> subscriber);
    }

}
