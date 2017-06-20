package com.nhb.app.manager.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fast.library.Adapter.recyclerview.BaseRecyclerAdapter;
import com.nhb.app.library.base.NHBFragment;
import com.nhb.app.manager.R;
import com.nhb.app.manager.base.CommonActivity;
import com.nhb.app.manager.model.SpManager;
import com.nhb.app.manager.view.EmptyView;

/**
 * 说明：列表Fragment
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/6/24 0:13
 * <p/>
 * 版本：verson 1.0
 */
public abstract class BaseListFragment<T extends Parcelable> extends NHBFragment implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefresh;
    EmptyView emptyView;
    public CommonActivity mCommonActivity;

    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onInitCreate(Bundle savedInstanceState, View view) {
        super.onInitCreate(savedInstanceState, view);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        if (getActivity() instanceof CommonActivity){
            mCommonActivity = (CommonActivity) getActivity();
        }
    }

    public void showLoginActivity(){
        SpManager.exit();
        mCommonActivity.finish();
    }

    @Override
    protected void onFirstUserVisible() {
        recyclerView = bind(R.id.recyclerView);
        emptyView = bind(R.id.emptyView);
        swipeRefresh = bind(R.id.swipeRefresh);
        emptyView.bindView(recyclerView);
        //设置刷新的颜色
        swipeRefresh.setColorSchemeResources(R.color.appColor);
        swipeRefresh.setOnRefreshListener(this);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(getAdapter());
    }

    @Override
    protected int getRootViewResID() {
        return R.layout.fragment_base_list;
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    protected abstract BaseRecyclerAdapter<T> getAdapter();

}
