package com.cocolover2.lis.interf;

import android.widget.ImageView;

/**
 * 图片显示
 */
public interface OnDisplayImgLisener {
    /*选择器图片显示*/
    void displayImg(String url, ImageView imageView, int width, int height);

    /*选择器中的文件夹图片展示*/
    void displayDirConver(String url, ImageView imageView);
}
