package com.nhb.app.custom.viewmodel;

import android.content.Intent;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nhb.app.custom.R;
import com.nhb.app.custom.bean.OrderBean;
import com.nhb.app.custom.constant.Extras;
import com.nhb.app.custom.domain.NHBResponse;
import com.nhb.app.custom.domain.RestClient;
import com.nhb.app.custom.recyclerview.DividerItemDecoration;
import com.nhb.app.custom.recyclerview.NHBRecyclerVM;
import com.nhb.app.custom.recyclerview.RecyclerItemVM;
import com.nhb.app.custom.ui.personal.OrderDetailActivity;
import com.nhb.app.custom.utils.ResourceUtil;
import com.nhb.app.custom.utils.UserInfoUtils;
import com.nhb.app.custom.viewmodel.interfaces.ITitleBarNormal;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-29 20:10
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class OrderListVM extends NHBRecyclerVM<OrderBean> implements ITitleBarNormal {
    @NonNull
    @Override
    public RecyclerItemVM<OrderBean> getItemVM(int viewType) {
        return new ItemOrderVM();
    }

    @Override
    public Call loadApiService() {
        return RestClient.getService().getPersonalOrder(UserInfoUtils.getUserToken());
    }

    @Override
    public void handleData(int requestCode, Object data, NHBResponse response) {
        addAll((List<OrderBean>) data);
    }

    @Override
    public boolean enablePullToRefresh() {
        return true;
    }

    @Override
    public void clickFinish(View view) {
        finishActivity();
    }

    @Override
    public ObservableField<String> getTitle() {
        return new ObservableField<>(ResourceUtil.getString(R.string.personal_order));
    }

    @Override
    public RecyclerView.ItemDecoration getItemDecoration(WeakReference<RecyclerView> recyclerViewRef) {
        DividerItemDecoration decoration = new DividerItemDecoration(recyclerViewRef.get().getContext(), DividerItemDecoration.VERTICAL_LIST);
        decoration.setDivider(recyclerViewRef.get().getContext(), R.drawable.list_divider_height10);
        return decoration;
    }

    @Override
    public void onItemClick(WeakReference<View> viewRef, OrderBean bean, int position) {
        super.onItemClick(viewRef, bean, position);
        startActivity(new Intent(viewRef.get().getContext(), OrderDetailActivity.class).putExtra(Extras.ORDER_ID, bean.orderId));
    }
}
