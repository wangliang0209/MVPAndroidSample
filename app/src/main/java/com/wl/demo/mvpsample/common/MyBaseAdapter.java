package com.wl.demo.mvpsample.common;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangliang on 16-10-14.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {
    private static final String TAG = MyBaseAdapter.class.getSimpleName();

    protected List<T> mList = new ArrayList<>();
    private Context mContext;

    public MyBaseAdapter(Context context){
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        if(position >= 0 && position < mList.size()) {
            return mList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void refreshData(List<T> data) {
        mList.clear();
        if(data != null) {
            mList.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AbsItemView holder;
        final T itemData = mList.get(position);
        View returnView = convertView;
        if (returnView != null) {
            holder = (AbsItemView) returnView.getTag();
            holder.setData(itemData);
        } else {
            holder = generItemView();
            holder.setData(itemData);
            returnView = holder;
            returnView.setTag(holder);
        }

        return returnView;
    }


    public abstract AbsItemView generItemView();

}
