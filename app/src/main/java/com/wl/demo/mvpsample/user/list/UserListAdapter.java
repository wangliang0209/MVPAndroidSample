package com.wl.demo.mvpsample.user.list;

import android.content.Context;

import com.wl.demo.mvpsample.common.AbsItemView;
import com.wl.demo.mvpsample.common.MyBaseAdapter;
import com.wl.demo.mvpsample.net.resp.model.UserDetailResp;

/**
 * Created by wangliang on 16-10-14.
 */

public class UserListAdapter extends MyBaseAdapter<UserDetailResp> {

    public UserListAdapter(Context context) {
        super(context);
    }

    @Override
    public AbsItemView generItemView() {
        return new UserListItemView(getContext());
    }


}
