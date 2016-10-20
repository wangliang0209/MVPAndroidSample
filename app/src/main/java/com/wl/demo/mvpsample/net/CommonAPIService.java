package com.wl.demo.mvpsample.net;

import com.wl.demo.mvpsample.domain.Response;
import com.wl.demo.mvpsample.domain.UserInfo;
import com.wl.demo.mvpsample.user.list.model.UserListModel;
import com.wl.demo.mvpsample.user.login.model.LoginResp;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wangliang on 16-9-28.
 */

public interface CommonAPIService {

    @GET("api/user/list")
    Observable<Response<UserListModel>> userList(@Query("token") String token);

    @GET("api/user/detail")
    Observable<Response<UserInfo>> getUser(@Query("uid") String uid, @Query("token") String token);

    @FormUrlEncoded
    @POST("api/login")
    Observable<Response<LoginResp>> login(@Field("username") String username, @Field("password") String password);

}
