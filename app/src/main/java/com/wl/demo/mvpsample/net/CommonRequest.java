package com.wl.demo.mvpsample.net;

import android.content.Context;
import android.util.Log;

import com.wl.demo.mvpsample.domain.Response;
import com.wl.demo.mvpsample.domain.UserInfo;
import com.wl.demo.mvpsample.user.list.model.UserListModel;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wangliang on 16-9-28.
 */

public class CommonRequest {

    private Context mContext;
    private String mTag;

    public CommonRequest(Context context) {
        mContext = context.getApplicationContext();
        if(mContext != null) {
            mTag = context.getClass().getSimpleName();
            Log.d("WLTest", ">>>> tag " + mTag);
        }
    }

    public void getUserList(MySubscriber<UserListModel> subscriber) {
        Observable o = (Observable) CommonAPIManager.getInstance().getToogooAPIService().userList();
        doRequest(o, new HttpResultFunc<UserListModel>(subscriber), subscriber);
    }

    public void getUserDetail(String uid, MySubscriber<UserInfo> subscriber) {
        Observable o = (Observable) CommonAPIManager.getInstance().getToogooAPIService().getUser(uid);
        doRequest(o, new HttpResultFunc<UserInfo>(subscriber), subscriber);
    }


    /**
     * 调用请求方法
     *
     * @param func1
     * @param subscriber
     */
    private void doRequest(Observable o, Func1 func1, Subscriber subscriber) {
        CommonAPIManager.getInstance().putReq(mTag, subscriber);
        o.map(func1).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class HttpResultFunc<T> implements Func1<Response<T>, T> {

        private Subscriber subscriber;

        public HttpResultFunc(Subscriber subscriber) {
            this.subscriber = subscriber;
        }

        @Override
        public T call(Response<T> response) {
            CommonAPIManager.getInstance().removeReq(mTag, subscriber);
            if (response.getCode() != 0) {
                throw new ApiException(response);
            }
            return response.getData();
        }
    }

}
