package com.cocolover2.lis.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cocolover2.lis.AlbumHelper;
import com.cocolover2.lis.R;
import com.cocolover2.lis.adapter.GridPicsAdapter;
import com.cocolover2.lis.adapter.ImageBucketAdapter;
import com.cocolover2.lis.entity.ImageBucket;
import com.cocolover2.lis.entity.ImageItem;
import com.cocolover2.lis.interf.OnDisplayImgLisener;
import com.cocolover2.lis.interf.OnSelectResultListener;

import java.io.File;
import java.util.ArrayList;

/**
 * 图片选择器的本地图片展示和选择的基本父类<br>
 * 支持数据传递
 * <pre>
 *     Intent intent=new Intent(this,xxxActivity.class);
 *     intent.putParcelableArrayListExtra(KEY_IMG_SELECTED, hasSelectImgs);
 *     startActivityForResult(intent, requestCode);
 * </pre>
 */
public abstract class BaseSelectorActivity extends Activity implements OnDisplayImgLisener {
    private ArrayList<ImageItem> dataList = new ArrayList<>();//本地图片的数据源
    private ImageBucket selectedBucket;//先去选择的文件夹
    private ArrayList<ImageBucket> mBucketLists = new ArrayList<>(); //所有图片文件夹

    private OnSelectResultListener mSelectResultListener;
    private int mColumnNum = 3;

    private RecyclerView recyclerView;
    private GridPicsAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private RelativeLayout mContentRl;
    //文件夹选择对话框
    private FrameLayout popLayout;
    private FrameLayout popShadowLayout;
    private ListView popListview;


    private boolean isPopShow;

    private boolean mIsShowTakePhoto = true;

    /**
     * 图片资源
     * 0:checkbox的selector资源文件ID
     * 1:拍照相机图
     * 2:拍照的文本颜色
     * 3:拍照的点击效果
     */
    private int[] mIconResouce = {R.drawable.checkbox_default, R.drawable.ic_take_pic, Color.parseColor("#ffffff"), R.drawable.item_take_photo};

    private int mDirChooseIconRes;

    public abstract int getTopLayoutId();

    public abstract int getBottomLayoutId();

    public abstract void onMyCreate(Bundle savedInstanceState);

    public abstract void onBucketSelect(String bucketName);


    protected boolean isPopShow() {
        return isPopShow;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);

        initView();
        onMyCreate(savedInstanceState);
        setUpView();
    }

    private void initView() {
        mContentRl = (RelativeLayout) findViewById(R.id.selector_content);
        recyclerView = (RecyclerView) findViewById(R.id.selector_content_rv);
        popLayout = (FrameLayout) findViewById(R.id.layout_bucket_pop_container);
        popShadowLayout = (FrameLayout) findViewById(R.id.layout_bucket_pop_shadow);
        popListview = (ListView) findViewById(R.id.layout_bucket_pop_listview);
        if (getTopLayoutId() > 0) {
            final FrameLayout topLayout = (FrameLayout) findViewById(R.id.selector_topbar_container);
            final View topView = LayoutInflater.from(this).inflate(getTopLayoutId(), topLayout, false);
            topLayout.addView(topView, topView.getLayoutParams());
        }
        if (getBottomLayoutId() > 0) {
            final FrameLayout bottomLayout = (FrameLayout) findViewById(R.id.selector_bottombar_container);
            final View bottomView = LayoutInflater.from(this).inflate(getBottomLayoutId(), bottomLayout, false);
            bottomLayout.addView(bottomView, bottomView.getLayoutParams());
        }
        popLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPopShow) {
                    hidePop();
                }
            }
        });
    }

    protected void setOnSelectResultListener(OnSelectResultListener listener) {
        mSelectResultListener = listener;
    }

    protected int getSelectImgCount() {
        return AlbumHelper.getHasSelectCount();
    }

    private void setUpView() {
        if (mBucketLists.size() <= 0)
            mBucketLists = new AlbumHelper().getImageBucketList(this);
        initPop();

        dataList.clear();
        dataList.addAll(mBucketLists.get(0).imageList);
        gridLayoutManager = new GridLayoutManager(this, mColumnNum);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new GridPicsAdapter(this, dataList, mColumnNum, mIconResouce);
        recyclerView.setAdapter(adapter);
        adapter.setOnDisplayImgListener(this);
        adapter.setOnSelectPicListener(mSelectResultListener);
        adapter.refresh(mIsShowTakePhoto);
        if (mSelectResultListener != null)
            mSelectResultListener.onSelectImgs(getSelectImgCount());
    }


    private void changeBucket(int position, ArrayList<ImageItem> imageList) {
        dataList.clear();
        dataList.addAll(imageList);
        gridLayoutManager.scrollToPosition(0);
        mIsShowTakePhoto = position == 0;
        adapter.refresh(mIsShowTakePhoto);
    }

    protected void setMaxSize(int maxSize) {
        AlbumHelper.setMaxSize(maxSize);
    }


    protected ArrayList<ImageItem> getSelectImgs() {
        return AlbumHelper.getHasSelectImgs();
    }


    protected void showPop() {
        isPopShow = true;
        Animation popInListAnim = AnimationUtils.loadAnimation(this, R.anim.bucket_pop_in);
        Animation popInBgAnim = AnimationUtils.loadAnimation(this, R.anim.bucket_pop_bg_alpha_in);
        popLayout.setVisibility(View.VISIBLE);
        popShadowLayout.startAnimation(popInBgAnim);
        popListview.startAnimation(popInListAnim);
    }

    protected void hidePop() {
        isPopShow = false;
        Animation popoutListAnim = AnimationUtils.loadAnimation(this, R.anim.bucket_pop_out);
        Animation popOutBgAnim = AnimationUtils.loadAnimation(this, R.anim.bucket_pop_bg_alpha_out);
        popoutListAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                popLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        popShadowLayout.startAnimation(popOutBgAnim);
        popListview.startAnimation(popoutListAnim);
    }

    private void initPop() {
        selectedBucket = mBucketLists.get(0);
        final ImageBucketAdapter bucketAdapter = new ImageBucketAdapter(this, mBucketLists, mDirChooseIconRes);
        bucketAdapter.setOnDisplayImgListener(this);
        popListview.setAdapter(bucketAdapter);
        popListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final ImageBucket curBucket = mBucketLists.get(position);
                if (curBucket != selectedBucket) {
                    curBucket.isSelected = true;
                    selectedBucket.isSelected = false;
                    selectedBucket = curBucket;
                    onBucketSelect(curBucket.bucketName);
                    changeBucket(position, curBucket.imageList);
                    bucketAdapter.notifyDataSetChanged();
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hidePop();
                    }
                }, 100);
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (isPopShow) {
            hidePop();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GridPicsAdapter.REQUEST_PREVIEW) {
            adapter.refresh(mIsShowTakePhoto);
            if (mSelectResultListener != null)
                mSelectResultListener.onSelectImgs(getSelectImgCount());

        } else if (requestCode == GridPicsAdapter.REQUEST_TAKE_PIC) {
            String path = adapter.getPhotoPath();
            File file = new File(path);
            if (!file.exists())
                return;
            ImageItem item = new ImageItem();
            item.createTime = file.lastModified();
            item.imagePath = path;
            item.imageSize = file.length();
            item.isSelected = true;
            ArrayList<ImageItem> tmpList = new ArrayList<>();
            tmpList.add(item);
            adapter.startPreviewImgs(0, tmpList, true);
            MediaScannerConnection.scanFile(this, new String[]{path}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("ExternalStorage", "Scanned " + path + ":");
                            Log.i("ExternalStorage", "-> uri=" + uri);
                        }
                    });
        }
    }

    protected void setPreView(boolean isPreview) {
        GridPicsAdapter.setPreView(isPreview);
    }

    protected void setBackgroundColor(int color) {
        mContentRl.setBackgroundColor(color);
    }

    protected void setColunmNum(int colunmNum) {
        mColumnNum = colunmNum;
    }


    protected void startPreviewImgs(ArrayList<ImageItem> datas, boolean isTakePic) {
        adapter.startPreviewImgs(0, datas, isTakePic);
    }

    /**
     * 设置选择文件夹的icon
     */
    protected void setDirChooseIconRes(int drawableId) {
        mDirChooseIconRes = drawableId;
    }

    protected void setCheckBoxDrawableRes(int checkboxDrawable) {
        if (checkboxDrawable == 0)
            return;
        mIconResouce[0] = checkboxDrawable;
    }

    protected void setTakePhotoRes(int cameraDrawableRes, int textColor, int pressedSelector) {
        if (cameraDrawableRes == 0 || textColor == 0 || pressedSelector == 0)
            return;
        mIconResouce[1] = cameraDrawableRes;
        mIconResouce[2] = textColor;
        mIconResouce[3] = pressedSelector;
    }
}
