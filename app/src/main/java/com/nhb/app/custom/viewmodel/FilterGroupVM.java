package com.nhb.app.custom.viewmodel;

import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

import com.nhb.app.custom.bean.ShopCategoryGroupBean;
import com.nhb.app.custom.recyclerview.NHBRecyclerVM;
import com.nhb.app.custom.recyclerview.RecyclerItemVM;

import java.lang.ref.WeakReference;

import retrofit2.Call;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-27 15:36
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class FilterGroupVM extends NHBRecyclerVM<ShopCategoryGroupBean> {
    public ObservableInt selectItemGroup = new ObservableInt(-1);

    @NonNull
    @Override
    public RecyclerItemVM<ShopCategoryGroupBean> getItemVM(int viewType) {
        return new ItemFilterGroupVM(selectItemGroup);
    }

    @Override
    public Call loadApiService() {
        return null;
    }

    @Override
    public void onItemClick(WeakReference<View> viewRef, ShopCategoryGroupBean bean, int position) {
        super.onItemClick(viewRef, bean, position);
        // 记录所选的一级分类项
        selectItemGroup.set(position);
        selectItemGroup.notifyChange();
    }
}
