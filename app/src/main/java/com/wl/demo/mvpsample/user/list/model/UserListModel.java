package com.wl.demo.mvpsample.user.list.model;

import com.wl.demo.mvpsample.domain.UserInfo;

import java.util.List;

/**
 * Created by wangliang on 16-10-14.
 */

public class UserListModel {
    private List<UserInfo> list;

    public List<UserInfo> getList() {
        return list;
    }

    public void setList(List<UserInfo> list) {
        this.list = list;
    }
}
