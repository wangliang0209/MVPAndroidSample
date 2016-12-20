package com.cocolover2.lis.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * 支持左右反弹的viewpager
 */
public class BounceBackViewPager extends ViewPager {

    private Rect mRect = new Rect();//用来记录初始位置
    private float preX = 0f;
    private static final float RATIO = 0.8f;//摩擦系数


    public BounceBackViewPager(Context context) {
        super(context);
    }

    public BounceBackViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                preX = ev.getX();//记录起点
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (isBoundBack()) {
                    return actionMove(ev);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!mRect.isEmpty()) {
                    recoveryPosition();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private boolean isBoundBack() {
        final int index = getCurrentItem();
        if (index == 0 || getAdapter().getCount() - 1 == index) {
            return true;
        } else {
            return false;
        }
    }

    private boolean actionMove(MotionEvent ev) {
        final float nowX = ev.getX();
        //偏移量
        final float offset = preX - nowX;
        preX = nowX;
        if (mRect.isEmpty()) {
            mRect.set(getLeft(), getTop(), getRight(), getBottom());
        }
        scrollBy((int) (offset * RATIO), 0);
        return true;
    }

    private void recoveryPosition() {
        TranslateAnimation ta = new TranslateAnimation(getLeft(), mRect.left, 0, 0);
        ta.setDuration(150);
        ta.setInterpolator(new DecelerateInterpolator());
        startAnimation(ta);
        layout(mRect.left, mRect.top, mRect.right, mRect.bottom);
        mRect.setEmpty();
    }

}