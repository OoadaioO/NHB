package com.nhb.app.custom.viewmodel;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import com.nhb.app.custom.bean.CommonEntranceBean;
import com.nhb.app.custom.recyclerview.NHBRecyclerVM;
import com.nhb.app.custom.recyclerview.RecyclerItemVM;

import java.lang.ref.WeakReference;

import retrofit2.Call;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-26 00:35
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class HomeButtonsListVM extends NHBRecyclerVM<CommonEntranceBean> {
    public ObservableField<CommonEntranceBean> clickItem = new ObservableField<>();

    @NonNull
    @Override
    public RecyclerItemVM<CommonEntranceBean> getItemVM(int viewType) {
        return new ItemHomeButtonsVM();
    }

    @Override
    public Call loadApiService() {
        return null;
    }

    @Override
    public void onItemClick(WeakReference<View> viewRef, CommonEntranceBean bean, int position) {
        super.onItemClick(viewRef, bean, position);
        clickItem.set(bean);
    }
}
