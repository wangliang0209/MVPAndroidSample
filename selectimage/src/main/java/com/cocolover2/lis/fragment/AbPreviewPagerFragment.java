package com.cocolover2.lis.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cocolover2.lis.R;
import com.cocolover2.lis.interf.OnPagerUpdateListener;
import com.cocolover2.lis.view.HackyViewPager;

import java.io.Serializable;
import java.util.List;

/**
 * 图片预览界面
 *
 * @param <T> 预览的数据源类型
 * @author liubo
 * @since 1.0.0
 */
public abstract class AbPreviewPagerFragment<T extends Serializable> extends Fragment {
    private OnPagerUpdateListener pagerUpdateListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pre_pager, container, false);
        HackyViewPager viewPager = (HackyViewPager) view.findViewById(R.id.previewpager_viewpager_pager);
               /*禁用ViewPager左右两侧拉到边界的渐变颜色*/
        viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        ImagePagerAdapter mAdapter = new ImagePagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(getStartPos());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        return view;
    }


    /*这里建议使用FragmentStatePagerAdapter,不建议使用FragmentPagerAdapter,因为FragmentPagerAdapter,会缓存页面，容易造成OOM*/
    private class ImagePagerAdapter extends FragmentStatePagerAdapter {
        public ImagePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return getShowContentFragment(getDatas().get(position));
        }

        @Override
        public int getCount() {
            return getDatas() == null ? 0 : getDatas().size();
        }
    }

    public void setPagerUpdateListener(OnPagerUpdateListener listener) {
        pagerUpdateListener = listener;
    }

    protected abstract Fragment getShowContentFragment(T data);

    protected abstract int getStartPos();

    protected abstract List<T> getDatas();
}
