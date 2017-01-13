package com.wl.demo.mvpsample.user.list;

import com.wl.demo.mvpsample.net.resp.model.UserListResp;

/**
 * Created by wangliang on 16-10-14.
 */

public interface ListContact {
    interface View {
        void showProgress();
        void hideProgress();

        void getDataSucc(UserListResp data);
        void getDataFailed(String error);
    }

    interface Presenter {
        void getData();
    }

}
