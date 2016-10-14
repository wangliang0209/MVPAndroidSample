package com.wl.demo.mvpsample.user.detail;

import android.content.Context;

import com.wl.demo.mvpsample.domain.UserInfo;
import com.wl.demo.mvpsample.net.MySubscriber;

/**
 * Created by wangliang on 16-10-14.
 */

public class UserDetailPresenterImpl implements UserDetailContact.Presenter {

    private final UserDetailContact.View mMainView;
    private final UserDetailContact.Interactor mInteractor;

    public UserDetailPresenterImpl(UserDetailContact.View view, Context context) {
        mMainView = view;
        mInteractor = new UserDetailInteractorImpl(context);
    }

    @Override
    public void getData(String uid) {
        mMainView.showProgress();
        mInteractor.getData(uid, new MySubscriber<UserInfo>() { //TODO T替换成返回的对象
            @Override
            public void onSucc(UserInfo t) { //TODO T替换成返回的对象
                mMainView.hideProgress();
                mMainView.getDataSucc(t);
            }

            @Override
            public void onError(String error) {
                mMainView.hideProgress();
                mMainView.getDataFailed(error);
            }
        });
    }
}
