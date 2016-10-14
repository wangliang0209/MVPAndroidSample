package com.wl.demo.mvpsample.net;

import com.wl.demo.mvpsample.domain.Response;
import com.wl.demo.mvpsample.domain.UserInfo;
import com.wl.demo.mvpsample.user.list.model.UserListModel;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wangliang on 16-9-28.
 */

public interface CommonAPIService {

    @GET("user/list")
    Observable<Response<UserListModel>> userList();

    @GET("user/detail")
    Observable<Response<UserInfo>> getUser(@Query("uid") String uid);

}
