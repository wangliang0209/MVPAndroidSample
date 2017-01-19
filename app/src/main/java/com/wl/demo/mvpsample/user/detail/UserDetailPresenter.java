package com.wl.demo.mvpsample.user.detail;

import android.content.Context;

import com.wl.demo.mvpsample.base.BasePresenter;
import com.wl.demo.mvpsample.net.CommonRequest;
import com.wl.demo.mvpsample.net.MySubscriber;
import com.wl.demo.mvpsample.net.resp.model.UserDetailResp;

/**
 * Created by wangliang on 16-10-14.
 */

public class UserDetailPresenter extends BasePresenter<UserDetailContact.View> {

    public UserDetailPresenter(UserDetailContact.View view, Context context, String tname) {
        super(view, context, tname);
    }

    public void getData(String uid) {
        v.showProgress();
        new CommonRequest(mContext, mTagName).getUserDetail(uid, new MySubscriber<UserDetailResp>() { //TODO T替换成返回的对象
            @Override
            public void onSucc(UserDetailResp t) { //TODO T替换成返回的对象
                v.hideProgress();
                v.getDataSucc(t);
            }

            @Override
            public void onError(String error) {
                v.hideProgress();
                v.getDataFailed(error);
            }
        });
    }
}
