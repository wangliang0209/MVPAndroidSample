package com.wl.demo.mvpsample.common;

import android.content.Context;
import android.widget.LinearLayout;

/**
 * Created by wangliang on 16-10-14.
 */

public abstract class AbsItemView<T> extends LinearLayout implements IBaseItemView<T> {

    public AbsItemView(Context context) {
        super(context);
    }

}
