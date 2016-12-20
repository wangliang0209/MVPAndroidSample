package com.wl.demo.mvpsample.net;

import com.wl.demo.mvpsample.net.resp.model.UploadResp;
import com.wl.demo.mvpsample.net.resp.model.base.Response;
import com.wl.demo.mvpsample.net.resp.model.UserDetailResp;
import com.wl.demo.mvpsample.net.resp.model.UserListResp;
import com.wl.demo.mvpsample.net.resp.model.LoginResp;

import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wangliang on 16-9-28.
 */

public interface CommonAPIService {

    @GET("api/user/list")
    Observable<Response<UserListResp>> userList(@Query("token") String token);

    @GET("api/user/detail")
    Observable<Response<UserDetailResp>> getUser(@Query("uid") String uid, @Query("token") String token);

    @FormUrlEncoded
    @POST("api/login")
    Observable<Response<LoginResp>> login(@Field("username") String username, @Field("password") String password);

    @Multipart
    @POST("file/upload")
    Observable<Response<UploadResp>> upload(@Part("filename") String filename, @Part("file\"; filename=\"image.png") RequestBody img);
}
