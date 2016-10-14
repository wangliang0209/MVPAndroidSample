package com.wl.demo.mvpsample.user.list;

import com.wl.demo.mvpsample.user.list.model.UserListModel;
import com.wl.demo.mvpsample.net.MySubscriber;

/**
 * Created by wangliang on 16-10-14.
 */

public interface ListContact {
    interface View {
        void showProgress();
        void hideProgress();

        void getDataSucc(UserListModel data);
        void getDataFailed(String error);
    }

    interface Interactor {
        void getData(MySubscriber<UserListModel> subscriber);
    }

    interface Presenter {
        void getData();
    }

    interface GetDataListener {
        void onSuccess();
        void onFailed(String error);
    }
}
