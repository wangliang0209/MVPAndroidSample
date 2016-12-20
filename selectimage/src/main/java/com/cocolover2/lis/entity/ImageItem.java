package com.cocolover2.lis.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 图片实体类
 */
public class ImageItem implements Parcelable {
    public long imageId;
    public String imagePath;//原图的路径
    public long imageSize;
    public long createTime;
    public boolean isSelected;
    public boolean isCompressed = true;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(imageId);
        dest.writeString(imagePath);
        dest.writeLong(imageSize);
        dest.writeLong(createTime);
        dest.writeByte((byte) (isSelected ? 1 : 0));
        dest.writeByte((byte) (isCompressed ? 1 : 0));
    }

    public static final Creator<ImageItem> CREATOR = new Creator<ImageItem>() {
        @Override
        public ImageItem createFromParcel(Parcel source) {
            //读取要和写入的顺序一致
            ImageItem item = new ImageItem();
            item.imageId = source.readLong();
            item.imagePath = source.readString();
            item.imageSize = source.readLong();
            item.createTime = source.readLong();
            item.isSelected = (source.readByte() != 0);
            item.isCompressed = (source.readByte() != 0);
            return item;
        }

        @Override
        public ImageItem[] newArray(int size) {
            return new ImageItem[size];
        }
    };

    @Override
    public int hashCode() {
        return this.imagePath.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ImageItem) {
            return this.imagePath.equals(((ImageItem) o).imagePath);
        }
        return super.equals(o);
    }
}
