package com.wl.demo.mvpsample.user.list;

import android.content.Context;

import com.wl.demo.mvpsample.user.list.model.UserListModel;
import com.wl.demo.mvpsample.net.CommonRequest;
import com.wl.demo.mvpsample.net.MySubscriber;

/**
 * Created by wangliang on 16-10-14.
 */

public class ListInteractorImpl implements ListContact.Interactor {
    private final CommonRequest mRequest;

    public ListInteractorImpl(Context context) {
        mRequest = new CommonRequest(context);
    }

    @Override
    public void getData(MySubscriber<UserListModel> subscriber) {
        mRequest.getUserList(subscriber);
    }

}
