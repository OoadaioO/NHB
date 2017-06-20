package com.nhb.app.custom.ui.home.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.nhb.app.custom.R;
import com.nhb.app.custom.base.BaseNormalFragment;
import com.nhb.app.custom.common.view.LoadingStatusView;
import com.nhb.app.custom.constant.Constants;

import java.util.HashMap;
import java.util.Map;


/**
 * 首页列表部分
 */
public abstract class BaseHomeListFragment extends BaseNormalFragment implements OnScrollListener {

    private static final String TAG = BaseHomeListFragment.class.getSimpleName();

    protected ListView lv_content;
    protected LoadingStatusView mStatusView;

    // 用于列表数据去重
    protected Map<String, Integer> mExitIdMap = new HashMap<String, Integer>();

    private OnNestedListViewScrollListener mOnNestedListViewScrollListener;

    private String mTabType;

    @Override
    protected int loadLayoutId() {
        return R.layout.fragment_home_list;
    }

    @Override
    protected void initialize() {
        lv_content = (ListView) findViewById(R.id.lv_data);
        lv_content.setOnScrollListener(this);
        lv_content.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BaseHomeListFragment.this.onItemClick(parent, view, position, id);
            }
        });
        findViewById(R.id.commonList_iv_backToTheTop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv_content.setSelection(0);
                if (mOnNestedListViewScrollListener != null) {
                    mOnNestedListViewScrollListener.onClickBackTop();
                }
            }
        });
        mStatusView = (LoadingStatusView) findViewById(R.id.commonList_loading);
    }

    @Override
    public void onResume() {
        super.onResume();
        // 备注一下：在这里更新滚动位置无效，结果总是错的
        //updateScrollStatus();
    }

    public void setTabType(String tabType) {
        mTabType = tabType;
    }

    public String getTabType() {
        return mTabType;
    }

    protected abstract int getStartNum();

    protected abstract boolean isEmpty();

    protected abstract void onItemClick(AdapterView<?> parent, View view, int position, long id);

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            // do nothing
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // 这里返回的 firstVisibleItem visibleItemCount totalItemCount 都TM不靠谱，坑死我了！千万不要用！
        //LogUtil.d(TAG, "onScroll: firstVisibleItem = " + firstVisibleItem + ", visibleItemCount = " + visibleItemCount + ", totalItemCount = " + totalItemCount);

        // 切换Fragment时，即将被遮住的Fragment会回调一次该方法（妈蛋，这里好坑），所以在这里加一次判断
        String thisTabType = getTabType();
        String currentTabType = ((HomeFragment) getParentFragment()).getCurrentTabType();
        //LogUtil.d(TAG, "this = " + this + ", thisTabType = " + thisTabType + ", currentTabType = " + currentTabType);
        if (currentTabType == null) {
            updateScrollStatus();
        } else if (!TextUtils.isEmpty(thisTabType) && thisTabType.equals(currentTabType)) {
            updateScrollStatus();
        }

        // 控制返回顶部按钮的显隐
        if (firstVisibleItem > Constants.FIRST_VISIBLE_ITEMS_ITEM) {
            findViewById(R.id.commonList_iv_backToTheTop).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.commonList_iv_backToTheTop).setVisibility(View.GONE);
        }
    }

    /**
     * 更新滚动状态
     */
    public void updateScrollStatus() {
        //LogUtil.d(TAG, "updateScrollStatus called!");
        if (mOnNestedListViewScrollListener != null) {
            // 是否滚动到顶部
            mOnNestedListViewScrollListener.onNestedListViewScrolledToTop(isScrolledToTop());
            // 是否滚动到底部
            mOnNestedListViewScrollListener.onNestedListViewScrolledToBottom(isScrolledToBottom());
        } else {
            mOnNestedListViewScrollListener.onNestedListViewScrolledToTop(false);
            mOnNestedListViewScrollListener.onNestedListViewScrolledToBottom(false);
        }
    }

    private boolean isScrolledToTop() {
        if (lv_content != null) {
            int firstVisiblePosition = lv_content.getFirstVisiblePosition();
            if (firstVisiblePosition <= 1) {
                final View firstVisibleChild = lv_content.getChildAt(0);
                if (firstVisibleChild != null) {
                    return firstVisibleChild.getTop() >= lv_content.getTop();
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isScrolledToBottom() {
        if (lv_content != null) {
            final int lastItemPosition = lv_content.getCount() - 1;
            final int lastVisiblePosition = lv_content.getLastVisiblePosition();

            if (lastVisiblePosition >= lastItemPosition - 1) {
                final int childIndex = lastVisiblePosition - lv_content.getFirstVisiblePosition();
                final View lastVisibleChild = lv_content.getChildAt(childIndex);
                if (lastVisibleChild != null) {
                    return lastVisibleChild.getBottom() <= lv_content.getBottom();
                }
            }
        }

        return false;
    }

    public void setOnNestedListViewScrollListener(OnNestedListViewScrollListener listener) {
        mOnNestedListViewScrollListener = listener;
    }

    public interface OnNestedListViewScrollListener {
        void onNestedListViewScrolledToTop(boolean scrolledToTop);

        void onNestedListViewScrolledToBottom(boolean scrolledToBottom);

        void onClickBackTop();
    }

}
