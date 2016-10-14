package com.wl.demo.mvpsample.user.detail;

import com.wl.demo.mvpsample.domain.UserInfo;
import com.wl.demo.mvpsample.net.MySubscriber;

/**
 * Created by wangliang on 16-10-14.
 */

public interface UserDetailContact {
    interface View {
        void showProgress();
        void hideProgress();

        void getDataSucc(UserInfo userInfo);
        void getDataFailed(String error);
    }

    interface Interactor {
        void getData(String uid, MySubscriber<UserInfo> subscriber); //TODO <T>补充
    }

    interface Presenter {
        void getData(String uid);
    }
}
