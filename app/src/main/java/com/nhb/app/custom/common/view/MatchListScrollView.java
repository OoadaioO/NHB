package com.nhb.app.custom.common.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

/**
 * Created by michaelliu on 21/12/15.
 */
public class MatchListScrollView extends ScrollView {

    private static final String TAG = MatchListScrollView.class.getSimpleName();

    private ScrollListener mScrollListener;

    private Context mContext;

    private float   mTouchX;
    private float   mTouchY;
    /**
     * 嵌套的ListView是否已滑动到顶部
     */
    private boolean mNestedListViewScrolledToTop;
    /**
     * 嵌套的ListView是否已滑动到底部
     */
    private boolean mNestedListViewScrolledToBottom;
    /**
     * 容错像素范围
     */
    private int     mTouchSlop;

    public MatchListScrollView(Context context) {
        super(context);
        init(context);
    }

    public MatchListScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mTouchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
        // LogUtil.d(TAG, "mTouchSlop = " + mTouchSlop);
    }

    public void setListViewScrollTop(boolean inTop) {
        //LogUtil.d(TAG, "setListViewScrollTop: inTop = " + inTop);
        mNestedListViewScrolledToTop = inTop;
    }

    public void setListViewScrollBottom(boolean inBottom) {
        //LogUtil.d(TAG, "mNestedListViewScrolledToBottom: inBottom = " + inBottom);
        mNestedListViewScrolledToBottom = inBottom;
    }

    public boolean isNestedListViewScrolledToTop() {
        return mNestedListViewScrolledToTop;
    }

    public boolean isNestedListViewScrolledToBottom() {
        return mNestedListViewScrolledToBottom;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // LogUtil.d(TAG, "the scrollY is:" + getScrollY());
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchX = ev.getX();
                mTouchY = ev.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                float moveX = Math.abs(ev.getX() - mTouchX);
                float moveY = Math.abs(ev.getY() - mTouchY);
                // 容错：只保留两位小数（手指触摸屏幕时其实是一个大点）
                moveX = (float) (Math.round(moveX * 100)) / 100;
                moveY = (float) (Math.round(moveY * 100)) / 100;

                //LogUtil.d(TAG, "moveX = " + moveX + ", moveY = " + moveY);
                if (moveX >= moveY) {
                    //横向滑动
                    return false;
                } else {
                    //LogUtil.d(TAG, "ev.getY() = " + ev.getY() + ", mTouchY = " + mTouchY);
                    boolean result = false;
                    // 触摸容错处理，触摸点相差不到一定像素个数时忽略
                    if (Math.abs(ev.getY() - mTouchY) <= mTouchSlop) {
                        return result;
                    }
                    //纵向滑动
                    if (ev.getY() > mTouchY) {
                        //向下滑时是否应该拦截
                        result = shouldInterceptWhenScrollDown();
                    } else if (ev.getY() < mTouchY) {
                        //向上滑时是否应该拦截
                        result = shouldInterceptWhenScrollUp();
                    }
                    //LogUtil.d(TAG, "result = " + result);
                    return result;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }

    private boolean shouldInterceptWhenScrollDown() {
        //LogUtil.d(TAG, "scrollDown: mNestedListViewScrolledToTop = " + mNestedListViewScrolledToTop);
        if (mNestedListViewScrolledToTop) {
            return true;
        } else {
            return false;
        }
    }

    private boolean shouldInterceptWhenScrollUp() {
        boolean result = false;
        //LogUtil.d(TAG, "getScrollY() = " + getScrollY());
        //LogUtil.d(TAG, "getChildAt(0).getMeasuredHeight() = " + getChildAt(0).getMeasuredHeight());
        //LogUtil.d(TAG, "getHeight() = " + getHeight());
        if (getScrollY() < getChildAt(0).getMeasuredHeight() - getHeight()) {
            result = true;
        }
        //LogUtil.d(TAG, "scrollUp: result = " + result);
        return result;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (mScrollListener != null) {
            mScrollListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

    public void setScrollListener(ScrollListener listener) {
        mScrollListener = listener;
    }

    public interface ScrollListener {
        void onScrollChanged(MatchListScrollView scrollView, int x, int y, int oldx, int oldy);
    }

    /**
     * 禁止ScrollView在子控件的布局改变时自动滚动的的方法
     * (参考：http://my.oschina.net/cjk035/blog/127445)
     *
     * @param rect
     * @return
     */
    @Override
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        return 0;
    }

}
