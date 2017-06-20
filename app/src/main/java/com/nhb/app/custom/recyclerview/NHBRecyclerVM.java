package com.nhb.app.custom.recyclerview;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nhb.app.custom.base.FetchDataViewModel;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * <br>***********************************************************************
 * <br> Author:pengxiaofang
 * <br> CreateData:2016-03-10 11:48
 * <br> Version:1.0
 * <br> Description:{@link RecyclerView}的ViewModel
 * <br>***********************************************************************
 */
public abstract class NHBRecyclerVM<T> extends FetchDataViewModel {
    // 列表数据
    public final ObservableArrayList<T> beans = new ObservableArrayList();
    // 可以在任意位置为headerLayout赋值，赋值后该View会出现在列表中
    public final ObservableInt headerLayout = new ObservableInt();
    // 可以在任意位置为footerLayout赋值，赋值后该View会出现在列表中
    public final ObservableInt footerLayout = new ObservableInt();
    // 是否加载到了最后一页
    public final ObservableBoolean isLastPage = new ObservableBoolean(false);

    // HeaderView
    public View headerView;
    // FooterView
    public View footerView;
    // 获取数据时startNum
    public int startNum = 1;

    /**
     * 下拉刷新数据,无Loading框
     */
    public void refreshData() {
        refreshData(true);
    }

    /**
     * 刷新数据,有Loading框
     *
     * @param isSilence
     */
    public void refreshData(boolean isSilence) {
        startNum = 1;
        if (isSilence) {
            fetchRemoteData();
        } else {
            fetchRemoteData(isSilence);
        }
    }

    /**
     * 分页加载数据
     */
    public void loadMoreData() {
        startNum++;
        fetchRemoteData();
    }

    /**
     * 列表点击事件
     *
     * @param viewRef
     * @param bean
     * @param position
     */
    public void onItemClick(WeakReference<View> viewRef, T bean, int position) {

    }


    /**
     * 列表长按点击事件
     *
     * @param viewRef
     * @param bean
     * @param position
     */
    public void onItemLongClick(WeakReference<View> viewRef, T bean, int position) {

    }

    /**
     * 追加数据
     *
     * @param data
     */
    public void addAll(List<T> data) {
        if (startNum == 1) {
            isLastPage.set(false);
            isLastPage.notifyChange();
            // 首次加载或下拉刷新
            if (data.size() == 0) {
                resultCode.set(CODE_EMPTY);
            } else {
                resultCode.set(CODE_SUCCESS);
                beans.clear();
                beans.addAll(data);
            }
        } else {
            if (null == data || data.size() == 0) {
                // 到最后一页了
                isLastPage.set(true);
                isLastPage.notifyChange();
            } else {
                beans.addAll(data);
            }
        }
    }

    public int getItemViewType(int position) {
        return 0;
    }

    @NonNull
    public abstract RecyclerItemVM<T> getItemVM(int viewType);

    @LayoutRes
    public int loadMoreView() {
        return 0;
    }

    /**
     * 是否启用下拉刷新,默认启用
     *
     * @return
     */
    public boolean enablePullToRefresh() {
        return true;
    }

    public RecyclerView.LayoutManager getLayoutManager(WeakReference<RecyclerView> recyclerViewRef) {
        return new LinearLayoutManager(recyclerViewRef.get().getContext());
    }

    public RecyclerView.ItemDecoration getItemDecoration(WeakReference<RecyclerView> recyclerViewRef) {
        return new DividerItemDecoration(recyclerViewRef.get().getContext(), DividerItemDecoration.VERTICAL_LIST);
    }
}
