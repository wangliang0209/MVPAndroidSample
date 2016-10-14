package com.wl.demo.mvpsample.user.list;

import android.content.Context;

import com.wl.demo.mvpsample.user.list.model.UserListModel;
import com.wl.demo.mvpsample.net.MySubscriber;

/**
 * Created by wangliang on 16-10-14.
 */

public class ListPresenterImpl implements ListContact.Presenter {

    private final ListContact.View mMainView;
    private final ListContact.Interactor mInteractor;

    public ListPresenterImpl(ListContact.View view, Context context) {
        mMainView = view;
        mInteractor = new ListInteractorImpl(context);
    }

    @Override
    public void getData() {
        mMainView.showProgress();
        mInteractor.getData(new MySubscriber<UserListModel>() {
            @Override
            public void onSucc(UserListModel userListModel) {
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
