package com.fast.library.tools;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * 说明：RecyclerViewTools
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/4/7 10:24
 * <p/>
 * 版本：verson 1.0
 */
public class RecyclerViewTools {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mManager;

    public RecyclerViewTools(){
        this(null);
    }

    public RecyclerViewTools(RecyclerView recyclerView){
        mRecyclerView = recyclerView;
        if (mRecyclerView != null){
            init();
        }
    }

    private void checkBindRecylerView(){
        if (mRecyclerView == null){
            throw new RuntimeException("你应该先调用setRecyclerView(),绑定RecyclerView!");
        }
    }

    public void setRecyclerView(RecyclerView recyclerView){
        mRecyclerView = recyclerView;
        checkBindRecylerView();
        init();
    }

    /**
     * 说明：初始化
     */
    private void init() {
        if (mManager == null){
            mManager = new LinearLayoutManager(mRecyclerView.getContext(),LinearLayoutManager.VERTICAL,false);
        }
        mRecyclerView.setLayoutManager(mManager);
    }

    /**
     * 说明：设置垂直方向
     */
    public void setVertical(){
        checkBindRecylerView();
        mManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mManager);
    }

    /**
     * 说明：设置水平方向
     */
    public void setHorizontal(){
        checkBindRecylerView();
        mManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(mManager);
    }

    /**
     * 说明：设置水平方向
     */
    public void setLinearLayoutManager(LinearLayoutManager manager){
        checkBindRecylerView();
        this.mManager = manager;
        mRecyclerView.setLayoutManager(mManager);
    }

    /**
     * 说明：如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
     * @param fixed
     */
    public void setHasFixedSize(boolean fixed){
        checkBindRecylerView();
        mRecyclerView.setHasFixedSize(fixed);
    }
}
