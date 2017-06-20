package com.nhb.app.library.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 说明：MineViewPager禁止左右滑动
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2016/6/23 12:44
 * <p/>
 * 版本：verson 1.0
 */
public class MineViewPager extends ViewPager{

    public MineViewPager(Context context) {
        super(context);
    }

    public MineViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

}
