package com.nhb.app.library.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.fast.library.utils.UIUtils;

/**
 * 说明：TitleBarUtils
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2016/6/22 14:11
 * <p/>
 * 版本：verson 1.0
 */
public class TitleBarUtils {
    private Context mContext;
    private LinearLayout mContentView;
    private View mUserView;
    private View mTitleView;

//    /*
//    * 两个属性
//    * 1、toolbar是否悬浮在窗口之上
//    * 2、toolbar的高度获取
//    * */
//    private static int[] ATTRS = {
//            R.attr.windowActionBarOverlay,
//            R.attr.actionBarSize
//    };

    public TitleBarUtils(Context context, int layoutId, int titleId) {
        this.mContext = context;
        initContentView();
        initTitleBar(titleId);
        initUserView(layoutId);
    }

    /**
     * 说明：初始化整块内容
     */
    private void initContentView() {
        mContentView = new LinearLayout(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mContentView.setOrientation(LinearLayout.VERTICAL);
        mContentView.setLayoutParams(params);
    }

    /**
     * 说明：初始化标题栏
     */
    private void initTitleBar(int titleId) {
        mTitleView = UIUtils.inflate(titleId,mContentView,mContentView!=null);
    }

    private void initUserView(int layoutId) {
        mUserView = UIUtils.inflate(layoutId, null, false);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContentView.addView(mUserView, params);
    }

    public View getTitleBar() {
        return mTitleView;
    }

    public View getUserView() {
        return mContentView;
    }
}
