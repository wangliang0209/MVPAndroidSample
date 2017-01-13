package com.cocolover2.lis.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cocolover2.lis.R;
import com.cocolover2.lis.entity.ImageBucket;
import com.cocolover2.lis.interf.OnDisplayImgLisener;

import java.util.ArrayList;

public class ImageBucketAdapter extends BaseAdapter {

    private ArrayList<ImageBucket> bucketList;
    private Context context;
    private int chooseIconRes;
    private OnDisplayImgLisener mLisener;

    public ImageBucketAdapter(Context context, ArrayList<ImageBucket> bucketList, int chooseIconRes) {
        this.context = context;
        this.bucketList = bucketList;
        if (chooseIconRes == 0)
            chooseIconRes = R.drawable.ic_dir_choose;
        this.chooseIconRes = chooseIconRes;
    }

    @Override
    public int getCount() {
        return bucketList == null ? 0 : bucketList.size();
    }

    @Override
    public Object getItem(int position) {
        return bucketList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageBucket bucket = bucketList.get(position);
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.img_bucket_item, null);
            vh.frontConver = (ImageView) convertView
                    .findViewById(R.id.img_bucket_item_img);
            vh.chooseFlag = (ImageView) convertView
                    .findViewById(R.id.img_bucket_item_choose_flag);
            vh.bucketName = (TextView) convertView
                    .findViewById(R.id.img_bucket_item_name);
            vh.countNum = (TextView) convertView
                    .findViewById(R.id.img_bucket_item_num);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        if (bucket.isSelected) {
            vh.chooseFlag.setVisibility(View.VISIBLE);
            vh.chooseFlag.setImageResource(chooseIconRes);
        } else {
            vh.chooseFlag.setVisibility(View.INVISIBLE);
        }
        vh.bucketName.setText(bucket.bucketName);
        vh.countNum.setText(bucket.count + "张");

        if (mLisener != null)
            mLisener.displayDirConver(bucket.imageList.get(0).imagePath, vh.frontConver);
        return convertView;
    }

    public void setOnDisplayImgListener(OnDisplayImgLisener listener) {
        mLisener = listener;
    }

    private static class ViewHolder {
        ImageView frontConver;
        ImageView chooseFlag;
        TextView bucketName;
        TextView countNum;
    }
}