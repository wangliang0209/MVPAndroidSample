package com.wl.demo.mvpsample.net;


import rx.Subscriber;

/**
 * Created by wangliang on 16-9-29.
 */

public abstract class MySubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {
        // do nothing.
    }

    @Override
    public void onError(Throwable e) {

        if(e instanceof ApiException) {
            onError(((ApiException) e).getErrorMsg());
        } else {
            onError("请求发生错误");
        }
    }

    @Override
    public void onNext(T t) {
        onSucc(t);
    }

    public abstract void onSucc(T t);
    public abstract void onError(String error);
}
