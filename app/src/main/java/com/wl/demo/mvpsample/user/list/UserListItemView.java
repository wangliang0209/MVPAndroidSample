package com.wl.demo.mvpsample.user.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.wl.demo.mvpsample.R;
import com.wl.demo.mvpsample.common.AbsItemView;
import com.wl.demo.mvpsample.net.resp.model.UserDetailResp;

import butterknife.ButterKnife;

/**
 * Created by wangliang on 16-10-14.
 */

public class UserListItemView extends AbsItemView<UserDetailResp> {
    private TextView mNameTv;

    private TextView mAgeTv;

    public UserListItemView(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_view_user_list, this);
        mNameTv = ButterKnife.findById(this, R.id.tv_name);
        mAgeTv = ButterKnife.findById(this, R.id.tv_age);
    }

    @Override
    public void setData(UserDetailResp userInfo) {
        mNameTv.setText(userInfo.getName());
        mAgeTv.setText(String.valueOf(userInfo.getAge()));
    }
}
