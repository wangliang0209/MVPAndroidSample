package com.wl.demo.mvpsample.selector;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.cocolover2.lis.activity.BaseSelectorActivity;
import com.cocolover2.lis.interf.OnSelectResultListener;
import com.wl.demo.mvpsample.R;

/**
 * 本地图片选择界面
 */

public class DefaultSelectorActivity extends BaseSelectorActivity implements OnSelectResultListener, View.OnClickListener {
    private TextView mPreViewTv, mBucketTv;
    private Button mSureBtn;

    @Override
    public int getTopLayoutId() {
        return R.layout.layout_topbar;
    }

    @Override
    public int getBottomLayoutId() {
        return R.layout.layout_locaimg_bottom;
    }

    @Override
    public void onMyCreate(Bundle savedInstanceState) {
        mBucketTv = (TextView) findViewById(R.id.layout_locaimg_bottom_bucket);
        mPreViewTv = (TextView) findViewById(R.id.layout_locaimg_bottom_preview);
        mSureBtn = (Button) findViewById(R.id.topbar_sure_btn);
        mSureBtn.setOnClickListener(this);

        mBucketTv.setText("所有图片");
        mBucketTv.setOnClickListener(this);
        mPreViewTv.setOnClickListener(this);
        setOnSelectResultListener(this);
        setColunmNum(3);
        setPreView(true);
        setBackgroundColor(Color.parseColor("#000000"));
        setMaxSize(9);
    }

    @Override
    public void onBucketSelect(String bucketName) {
        mBucketTv.setText(bucketName);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_locaimg_bottom_bucket:
                if (isPopShow()) {
                    hidePop();
                } else {
                    showPop();
                }
                break;
            case R.id.layout_locaimg_bottom_preview:
                startPreviewImgs(getSelectImgs(), false);
                break;
            case R.id.topbar_back_iv:
                finish();
                break;
            case R.id.topbar_sure_btn:
                finish();
                break;
        }
    }

    @Override
    public void onSelectImgs(int selectedCount) {
        if (selectedCount == 0) {
            mSureBtn.setText("确定");
            mPreViewTv.setText("预览");
            mPreViewTv.setEnabled(false);
            mSureBtn.setEnabled(false);
        } else {
            mSureBtn.setText("确定(" + selectedCount + ")");
            mPreViewTv.setText("预览(" + selectedCount + ")");
            mPreViewTv.setEnabled(true);
            mSureBtn.setEnabled(true);
        }
    }


    @Override
    public void displayImg(String path, final ImageView imageView, int width, int height) {
        Glide.with(this)
                .load("file://" + path)
                .placeholder(R.drawable.ic_default_img)
                .error(R.drawable.ic_default_img)
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.NONE) //不缓存到SD卡
                .skipMemoryCache(true)
                .centerCrop()
                .into(imageView);
    }

    @Override
    public void displayDirConver(String url, final ImageView imageView) {
        Glide.with(this)
                .load(url)
                .asBitmap()
                .placeholder(R.drawable.ic_default_img)
                .error(R.drawable.ic_default_img)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        imageView.setImageBitmap(resource);
                    }
                });
    }


}
