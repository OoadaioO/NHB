package com.nhb.app.custom.viewmodel;

import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

import com.nhb.app.custom.bean.ItemCategoryChildBean;
import com.nhb.app.custom.recyclerview.NHBRecyclerVM;
import com.nhb.app.custom.recyclerview.RecyclerItemVM;

import java.lang.ref.WeakReference;

import retrofit2.Call;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-27 15:35
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class FilterChildVM extends NHBRecyclerVM<ItemCategoryChildBean> {
    public ObservableInt selectItemChild = new ObservableInt(-1);

    @NonNull
    @Override
    public RecyclerItemVM<ItemCategoryChildBean> getItemVM(int viewType) {
        return new ItemFilterChildVM(selectItemChild);
    }

    @Override
    public Call loadApiService() {
        return null;
    }

    @Override
    public void onItemClick(WeakReference<View> viewRef, ItemCategoryChildBean bean, int position) {
        super.onItemClick(viewRef, bean, position);
        // 记录所选的一级分类项
        selectItemChild.set(position);
        selectItemChild.notifyChange();
    }
}
