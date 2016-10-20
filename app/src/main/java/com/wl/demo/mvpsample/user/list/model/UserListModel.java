package com.wl.demo.mvpsample.user.list.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.wl.demo.mvpsample.domain.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangliang on 16-10-14.
 */

public class UserListModel implements Parcelable {
    private List<UserInfo> list;

    public List<UserInfo> getList() {
        return list;
    }

    public void setList(List<UserInfo> list) {
        this.list = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.list);
    }

    public UserListModel() {
    }

    protected UserListModel(Parcel in) {
        this.list = new ArrayList<UserInfo>();
        in.readList(this.list, UserInfo.class.getClassLoader());
    }

    public static final Parcelable.Creator<UserListModel> CREATOR = new Parcelable.Creator<UserListModel>() {
        @Override
        public UserListModel createFromParcel(Parcel source) {
            return new UserListModel(source);
        }

        @Override
        public UserListModel[] newArray(int size) {
            return new UserListModel[size];
        }
    };
}
