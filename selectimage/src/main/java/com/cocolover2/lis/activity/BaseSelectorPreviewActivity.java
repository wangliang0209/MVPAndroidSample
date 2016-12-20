package com.cocolover2.lis.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.cocolover2.lis.AlbumHelper;
import com.cocolover2.lis.LISConstant;
import com.cocolover2.lis.R;
import com.cocolover2.lis.entity.ImageItem;
import com.cocolover2.lis.interf.OnPagerUpdateListener;
import com.cocolover2.lis.view.HackyViewPager;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * 本地图片预览选择的基类
 */
public abstract class BaseSelectorPreviewActivity extends AppCompatActivity {
    final long PER_MB = 1024 * 1024;
    final long PER_KB = 1024;


    private FrameLayout topLayout, bottomLayout;

    private int startPos;
    private ArrayList<ImageItem> mImgDatas;

    private OnPagerUpdateListener pagerUpdateListener;

    private int mOriginStatusBarColor = 0xff000000;
    DecimalFormat mDecimalFormat = new DecimalFormat("#.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previewpager);
        mImgDatas = getIntent().getParcelableArrayListExtra(LISConstant.KEY_PRE_IMG_PATH_LIST);
        startPos = getIntent().getIntExtra(LISConstant.KEY_PRE_IMG_START_POSITION, 0);

        initView();
        mOriginStatusBarColor = getStatusBarColor();
        onMyCreate(savedInstanceState);
    }

    public ImageItem getItem(int position) {
        ImageItem item = mImgDatas.get(position);
        if (item.isSelected) {
            return AlbumHelper.getItemFromSelected(item);
        } else
            return item;
    }

    protected void setOnPagerUpdateListener(OnPagerUpdateListener listener) {
        pagerUpdateListener = listener;
    }


    private void initView() {
        topLayout = (FrameLayout) findViewById(R.id.previewpager_topbar);
        if (getTopBarLayoutId() > 0) {
            final View topBar = LayoutInflater.from(this).inflate(getTopBarLayoutId(), topLayout, false);
            topLayout.addView(topBar, topBar.getLayoutParams());
        }
        bottomLayout = (FrameLayout) findViewById(R.id.previewpager_bottom_layout);
        if (getBottomLayoutId() > 0) {
            final View bottomBar = LayoutInflater.from(this).inflate(getBottomLayoutId(), bottomLayout, false);
            bottomLayout.addView(bottomBar, bottomBar.getLayoutParams());
        }
        initViewPager();
    }

    private void initViewPager() {
        final HackyViewPager mViewPager = (HackyViewPager) findViewById(R.id.previewpager_viewpager_pager);
              /*禁用ViewPager左右两侧拉到边界的渐变颜色*/
        mViewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        ImagePagerAdapter mAdapter = new ImagePagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(startPos);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (pagerUpdateListener != null)
                    pagerUpdateListener.onSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    protected int getStartPos() {
        return startPos;
    }

    protected int getAllDatasSize() {
        return mImgDatas.size();
    }


    public abstract int getTopBarLayoutId();

    public abstract int getBottomLayoutId();

    public abstract void onMyCreate(Bundle savedInstanceState);

    public abstract Fragment showContentFragment(ImageItem content);


    private Animation mAnimationTOP, mAnimationBottom;
    protected boolean isShowTopBottom = true;

    protected void showTopAndBottomLayout() {
        setStatusBarColor(mOriginStatusBarColor);
        if (topLayout.getVisibility() != View.VISIBLE)
            topLayout.setVisibility(View.VISIBLE);
        if (bottomLayout.getVisibility() != View.VISIBLE)
            bottomLayout.setVisibility(View.VISIBLE);
        mAnimationTOP = AnimationUtils.loadAnimation(this, R.anim.slide_top_in);
        mAnimationBottom = AnimationUtils.loadAnimation(this, R.anim.slide_bottom_in);
        topLayout.startAnimation(mAnimationTOP);
        bottomLayout.startAnimation(mAnimationBottom);
        isShowTopBottom = true;
    }

    protected void hideTopAndBottomLayout() {
        mAnimationTOP = AnimationUtils.loadAnimation(this, R.anim.slide_top_out);
        mAnimationTOP.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                topLayout.setVisibility(View.GONE);
                setStatusBarColor(getResources().getColor(android.R.color.black));

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mAnimationBottom = AnimationUtils.loadAnimation(this, R.anim.slide_bottom_out);
        mAnimationBottom.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                bottomLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        topLayout.startAnimation(mAnimationTOP);
        bottomLayout.startAnimation(mAnimationBottom);
        isShowTopBottom = false;
    }

    /*这里建议使用FragmentStatePagerAdapter,不建议使用FragmentPagerAdapter,因为FragmentPagerAdapter,会缓存页面，容易造成OOM*/
    private class ImagePagerAdapter extends FragmentStatePagerAdapter {
        ImagePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return showContentFragment(mImgDatas.get(position));
        }

        @Override
        public int getCount() {
            return mImgDatas == null ? 0 : mImgDatas.size();
        }
    }

    protected boolean addToSelectList(ImageItem item) {
        if (AlbumHelper.getHasSelectCount() >= AlbumHelper.getMaxSize()) {
            item.isSelected = false;
            Toast.makeText(this, "最多选择" + AlbumHelper.getMaxSize() + "张图片", Toast.LENGTH_SHORT).show();
            return false;
        }
        item.isSelected = true;
        return AlbumHelper.addtoSelectImgs(item);
    }

    protected int getHasSelectCount() {
        return AlbumHelper.getHasSelectCount();
    }

    protected String getSelectImgsSize() {
        int size = 0;
        for (ImageItem i : AlbumHelper.getHasSelectImgs()) {
            size += i.imageSize;
        }
        if (size < PER_KB) {//小于1KB
            return size + "B";
        }
        if (size < PER_MB) {
            return mDecimalFormat.format(size / (PER_KB * 1.0f)) + "KB";
        }
        return mDecimalFormat.format(size / (PER_MB * 1.0f)) + "MB";
    }

    /**
     * 这里只是把该图片的选择标志，标记为false,并未删除（数据源不是选择后的图片集合，是传进来的所有的图片）
     */
    protected void removeSelectItem(ImageItem item) {
        AlbumHelper.removeItem(item);
        for (ImageItem imageItem : mImgDatas) {
            if (item.imagePath.equals(imageItem.imagePath)) {
                item.isSelected = false;
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setStatusBarColor(mOriginStatusBarColor);
    }

    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setStatusBarColor(color);
    }

    private int getStatusBarColor() {
        if (Build.VERSION.SDK_INT >= 21)
            return getWindow().getStatusBarColor();
        else
            return mOriginStatusBarColor;
    }
}
