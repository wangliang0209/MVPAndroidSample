package com.wl.demo.mvpsample.net.resp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangliang on 16-10-14.
 */

public class UserListResp implements Parcelable {
    private List<UserDetailResp> list;

    public List<UserDetailResp> getList() {
        return list;
    }

    public void setList(List<UserDetailResp> list) {
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

    public UserListResp() {
    }

    protected UserListResp(Parcel in) {
        this.list = new ArrayList<UserDetailResp>();
        in.readList(this.list, UserDetailResp.class.getClassLoader());
    }

    public static final Parcelable.Creator<UserListResp> CREATOR = new Parcelable.Creator<UserListResp>() {
        @Override
        public UserListResp createFromParcel(Parcel source) {
            return new UserListResp(source);
        }

        @Override
        public UserListResp[] newArray(int size) {
            return new UserListResp[size];
        }
    };
}
