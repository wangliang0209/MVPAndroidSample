package com.wl.demo.mvpsample.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by wangliang on 17-1-19.
 */
public abstract class MVPActivity<P extends BasePresenter> extends BaseActivity {
    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = initPresenter();
        super.onCreate(savedInstanceState);
    }

    protected abstract P initPresenter();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }
}
