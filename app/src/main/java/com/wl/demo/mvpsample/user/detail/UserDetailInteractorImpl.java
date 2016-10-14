package com.wl.demo.mvpsample.user.detail;

import android.content.Context;

import com.wl.demo.mvpsample.domain.UserInfo;
import com.wl.demo.mvpsample.net.CommonRequest;
import com.wl.demo.mvpsample.net.MySubscriber;

/**
 * Created by wangliang on 16-10-14.
 */

public class UserDetailInteractorImpl implements UserDetailContact.Interactor {
    private final CommonRequest mRequest;

    public UserDetailInteractorImpl(Context context) {
        mRequest = new CommonRequest(context);
    }

   
    @Override
    public void getData(String uid, MySubscriber<UserInfo> subscriber) {
        // 用mRequest获取数据
        mRequest.getUserDetail(uid, subscriber);
    }

   
}
