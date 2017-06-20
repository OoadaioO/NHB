package com.nhb.app.custom.common.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import com.handmark.pulltorefresh.library.OverscrollHelper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

/**
 * 可以下拉刷新和上拉加载的可嵌套ListView的ScrollView
 * <p/>
 * 5.8.0 created by zhangmao 2012-12-22
 */
public class MatchListPullToRefreshScrollView extends PullToRefreshBase<ScrollView>
        implements MatchListScrollView.ScrollListener {

    private static final String TAG = MatchListPullToRefreshScrollView.class.getSimpleName();

    private ScrollListener mScrollListener;
    private MatchListScrollView scrollView;

    public MatchListPullToRefreshScrollView(Context context) {
        super(context);
    }

    public MatchListPullToRefreshScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MatchListPullToRefreshScrollView(Context context, Mode mode) {
        super(context, mode);
    }

    @Override
    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected ScrollView createRefreshableView(Context context, AttributeSet attrs) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            scrollView = new InternalScrollViewSDK9(context, attrs);
        } else {
            scrollView = new MatchListScrollView(context, attrs);
        }

        scrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        scrollView.setScrollListener(this);
        scrollView.setId(com.handmark.pulltorefresh.R.id.scrollview);
        return scrollView;
    }

    @Override
    protected boolean isReadyForPullStart() {
        return scrollView.getScrollY() == 0;
    }

    @Override
    protected boolean isReadyForPullEnd() {
        View scrollViewChild = scrollView.getChildAt(0);
        if (null != scrollViewChild) {
            if (scrollView.getScrollY() >= (scrollViewChild.getHeight() - getHeight())) {
                if (((MatchListScrollView) scrollView).isNestedListViewScrolledToBottom()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    @TargetApi(9)
    final class InternalScrollViewSDK9 extends MatchListScrollView {

        public InternalScrollViewSDK9(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
                                       int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

            final boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
                    scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);

            // Does all of the hard work...
            OverscrollHelper.overScrollBy(MatchListPullToRefreshScrollView.this, deltaX, scrollX, deltaY, scrollY,
                    getScrollRange(), isTouchEvent);

            return returnValue;
        }

        /**
         * Taken from the AOSP ScrollView source
         */
        private int getScrollRange() {
            int scrollRange = 0;
            if (getChildCount() > 0) {
                View child = getChildAt(0);
                scrollRange = Math.max(0, child.getHeight() - (getHeight() - getPaddingBottom() - getPaddingTop()));
            }
            return scrollRange;
        }
    }

    public void setListViewScrollTop(boolean inTop) {
        //LogUtil.d(TAG, "setListViewScrollTop: inTop = " + inTop);
        ((MatchListScrollView) getRefreshableView()).setListViewScrollTop(inTop);
    }

    public void setListViewScrollBottom(boolean inBottom) {
        //LogUtil.d(TAG, "setListViewScrollBottom: inBottom = " + inBottom);
        ((MatchListScrollView) getRefreshableView()).setListViewScrollBottom(inBottom);
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
        void onScrollChanged(MatchListPullToRefreshScrollView scrollView, int x, int y, int oldx, int oldy);

        void onNestedScrollChanged(MatchListScrollView scrollView, int x, int y, int oldx, int oldy);
    }

    @Override
    public void onScrollChanged(MatchListScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (mScrollListener != null) {
            mScrollListener.onNestedScrollChanged(scrollView, x, y, oldx, oldy);
        }
    }

}
