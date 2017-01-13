package com.cocolover2.lis;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore.Images.Media;
import android.util.SparseArray;

import com.cocolover2.lis.entity.ImageBucket;
import com.cocolover2.lis.entity.ImageItem;

import java.io.File;
import java.util.ArrayList;

/**
 * 相册图片帮助类
 *
 * @author liubo
 * @since 1.0.0
 */
public final class AlbumHelper {
    /*已经选择的图片集合*/
    private static ArrayList<ImageItem> hasSelectImgs = new ArrayList<>();
    /*最大选择的图片数量*/
    private static int MAXSIZE;

    public static void setMaxSize(int size) {
        if (size <= 0) {
            MAXSIZE = 0;
        } else {
            MAXSIZE = size;
        }
    }

    public static int getMaxSize() {
        return MAXSIZE;
    }

    /**
     * 获取sd卡中的图片，并创建图片文件夹的列表
     */
    private void buildImagesBucketList(Context context, ArrayList<ImageItem> imageList, SparseArray<ImageBucket> bucketMap) {
        File file;
        String[] columns = {Media._ID, Media.BUCKET_ID, Media.DATA, Media.BUCKET_DISPLAY_NAME};
        Cursor cur = context.getContentResolver().query(Media.EXTERNAL_CONTENT_URI, columns, null, null,
                "_id DESC");
        if (cur != null && cur.getCount() <= 0) {
            throw new NullPointerException("do not  find any picture");
        }
        if (cur != null && cur.moveToFirst()) {
            final int photoIdIndex = cur.getColumnIndexOrThrow(Media._ID);
            final int photoPathIndex = cur.getColumnIndexOrThrow(Media.DATA);//图片路径字段
            final int bucketDisplayNameIndex = cur.getColumnIndexOrThrow(Media.BUCKET_DISPLAY_NAME);
            final int bucketIdIndex = cur.getColumnIndexOrThrow(Media.BUCKET_ID);
            do {
                int id = cur.getInt(photoIdIndex);
                String path = cur.getString(photoPathIndex);
                int bucketId = cur.getInt(bucketIdIndex);
                String bucketname = cur.getString(bucketDisplayNameIndex);
                file = new File(path);
                if (!file.exists() || file.length() == 0)
                    continue;
                ImageItem i = new ImageItem();
                i.imageId = id;
                i.imagePath = path;
                i.imageSize = file.length();
                i.createTime = file.lastModified();
                imageList.add(i);
                ImageBucket bucket;
                if (bucketMap.indexOfKey(bucketId) >= 0)
                    bucket = bucketMap.get(bucketId);
                else {
                    bucket = new ImageBucket();
                    bucketMap.put(bucketId, bucket);
                    bucket.bucketName = bucketname;
                    bucket.imageList = new ArrayList<>();
                }
                bucket.count++;
                bucket.imageList.add(i);
            } while (cur.moveToNext());
        }
        if (cur != null)
            cur.close();
    }

    /**
     * 获取图片的文件夹列表
     */
    public ArrayList<ImageBucket> getImageBucketList(Context context) {
        ArrayList<ImageItem> allImags = new ArrayList<>();
        SparseArray<ImageBucket> bucketMap = new SparseArray<>();
        if (bucketMap.size() == 0)
            buildImagesBucketList(context, allImags, bucketMap);

        final ArrayList<ImageBucket> allBuckets = new ArrayList<>();
        ImageBucket allBucket = new ImageBucket();
        allBucket.bucketName = context.getString(R.string.all_img_tag);
        allBucket.imageList = allImags;
        allBucket.count = allImags.size();
        allBucket.isSelected = true;
        allBuckets.add(allBucket);

        for (int i = 0; i < bucketMap.size(); i++) {
            int key = bucketMap.keyAt(i);
            allBuckets.add(bucketMap.get(key));
        }
        return allBuckets;
    }

    public static void clearSelectedImgs() {
        if (hasSelectImgs != null)
            hasSelectImgs.clear();
    }

    public static ArrayList<ImageItem> getHasSelectImgs() {
        return hasSelectImgs;
    }

    public static boolean addtoSelectImgs(ImageItem imageItem) {
        return hasSelectImgs.add(imageItem);
    }


    public static void removeItem(ImageItem item) {
        hasSelectImgs.remove(item);
    }

    public static int getHasSelectCount() {
        return hasSelectImgs.size();
    }

    public static void initDataList(ArrayList<ImageItem> dataRes) {
        for (ImageItem img : dataRes) {
            img.isSelected = hasSelectImgs.contains(img);
        }
    }

    /**
     * 从已经选择的集合中获取图片信息
     *
     * @param imageItem 原始的图片信息
     */
    public static ImageItem getItemFromSelected(ImageItem imageItem) {
        for (ImageItem item : hasSelectImgs) {
            if (imageItem.imagePath.equals(item.imagePath))
                return item;
        }
        return imageItem;
    }
}
