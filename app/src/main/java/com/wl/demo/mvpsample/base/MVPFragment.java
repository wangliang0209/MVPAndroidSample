package com.wl.demo.mvpsample.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by wangliang on 17-1-19.
 */

public abstract class MVPFragment<P extends BasePresenter> extends BaseFragment {
    protected P presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = initPresenter();
        super.onCreate(savedInstanceState);
    }

    protected abstract P initPresenter();


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }
}
