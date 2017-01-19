package com.wl.demo.mvpsample.net;

import android.content.Context;
import android.text.TextUtils;

import com.wl.demo.mvpsample.net.resp.model.LoginResp;
import com.wl.demo.mvpsample.net.resp.model.UploadResp;
import com.wl.demo.mvpsample.net.resp.model.UserDetailResp;
import com.wl.demo.mvpsample.net.resp.model.UserListResp;
import com.wl.demo.mvpsample.net.resp.model.base.Response;
import com.wl.demo.mvpsample.utils.CurUserHelper;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wangliang on 16-9-28.
 */

public class CommonRequest {

    private Context mContext;
    private String mTag;

    public CommonRequest(Context context, String tagName) {
        mContext = context.getApplicationContext();

        if (TextUtils.isEmpty(tagName)) {
            mTag = "CommonRequest";
        } else {
            mTag = tagName;
        }
    }


    public void getUserList(MySubscriber<UserListResp> subscriber) {
        Observable o = HttpModule.getInstance().getCommonAPIService().userList(CurUserHelper.getCurUserToken());
        doRequest(o, new HttpResultFunc<UserListResp>(), subscriber);
    }

    public void getUserDetail(String uid, MySubscriber<UserDetailResp> subscriber) {
        Observable o = HttpModule.getInstance().getCommonAPIService().getUser(uid, CurUserHelper.getCurUserToken());
        doRequest(o, new HttpResultFunc<UserDetailResp>(), subscriber);
    }

    public void login(String username, String password, MySubscriber<LoginResp> subscriber) {
        Observable o = HttpModule.getInstance().getCommonAPIService().login(username, password);
        doRequest(o, new HttpResultFunc<LoginResp>(), subscriber);
    }

    public void upload(String filepath, MySubscriber<UploadResp> subscriber) {
        File file = new File(filepath);
        if (!file.exists()) {
            if (subscriber != null) {
                subscriber.onError("文件不存在");
                return;
            }
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        Observable o = HttpModule.getInstance().getCommonAPIService().upload("upload", requestBody);
        doRequest(o, new HttpResultFunc<UploadResp>(), subscriber);
    }


    /**
     * 调用请求方法
     *
     * @param func1
     * @param subscriber
     */
    private void doRequest(Observable o, Func1 func1, Subscriber subscriber) {
        Subscription s = o.map(func1)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        SubscriptionManager.getInstance().putReq(mTag, s);
    }


    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class HttpResultFunc<T> implements Func1<Response<T>, T> {

        @Override
        public T call(Response<T> response) {
            if (response.getCode() != 0) {
                throw new ApiException(response);
            }
            return response.getData();
        }
    }

}
