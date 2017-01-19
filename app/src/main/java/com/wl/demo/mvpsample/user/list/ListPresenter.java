package com.wl.demo.mvpsample.user.list;

import android.content.Context;

import com.wl.demo.mvpsample.base.BasePresenter;
import com.wl.demo.mvpsample.net.CommonRequest;
import com.wl.demo.mvpsample.net.MySubscriber;
import com.wl.demo.mvpsample.net.resp.model.UserListResp;

/**
 * Created by wangliang on 16-10-14.
 */

public class ListPresenter extends BasePresenter<ListContact.View> {

    public ListPresenter(ListContact.View view, Context context, String tagName) {
        super(view, context, tagName);
    }

    public void getData() {
        v.showProgress();
        new CommonRequest(mContext, mTagName).getUserList(new MySubscriber<UserListResp>() {
            @Override
            public void onSucc(UserListResp userListModel) {
                v.hideProgress();
                v.getDataSucc(userListModel);
            }

            @Override
            public void onError(String error) {
                v.hideProgress();
                v.getDataFailed(error);
            }
        });
    }
}
