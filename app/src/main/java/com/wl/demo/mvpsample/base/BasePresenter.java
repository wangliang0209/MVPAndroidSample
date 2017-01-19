package com.wl.demo.mvpsample.base;

import android.content.Context;

/**
 * Created by wangliang on 17-1-19.
 */

public abstract class BasePresenter<V> {
    protected V v;
    protected String mTagName;
    protected Context mContext;

    public BasePresenter(V v, Context context, String tagName) {
        mTagName = tagName;
        mContext = context;
        attachView(v);
    }

    public void attachView(V v) {
        this.v = v;
    }


    public void detachView() {
        this.v = null;
    }
}
