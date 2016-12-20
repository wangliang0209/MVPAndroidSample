package com.wl.demo.mvpsample.user.list;

import android.content.Context;

import com.wl.demo.mvpsample.net.CommonRequest;
import com.wl.demo.mvpsample.net.MySubscriber;
import com.wl.demo.mvpsample.net.resp.model.UserListResp;

/**
 * Created by wangliang on 16-10-14.
 */

public class ListPresenterImpl implements ListContact.Presenter {

    private final ListContact.View mMainView;
    private final CommonRequest mCommonRequest;

    public ListPresenterImpl(ListContact.View view, Context context) {
        mMainView = view;
        mCommonRequest = new CommonRequest(context);
    }

    @Override
    public void getData() {
        mMainView.showProgress();
        mCommonRequest.getUserList(new MySubscriber<UserListResp>() {
            @Override
            public void onSucc(UserListResp userListModel) {
                mMainView.hideProgress();
                mMainView.getDataSucc(userListModel);
            }

            @Override
            public void onError(String error) {
                mMainView.hideProgress();
                mMainView.getDataFailed(error);
            }
        });
    }
}
