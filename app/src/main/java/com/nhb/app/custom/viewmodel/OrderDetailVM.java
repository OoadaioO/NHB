package com.nhb.app.custom.viewmodel;

import android.app.Activity;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;

import com.fast.library.utils.ToolUtils;
import com.nhb.app.custom.R;
import com.nhb.app.custom.base.FetchDataViewModel;
import com.nhb.app.custom.bean.OrderDetailBean;
import com.nhb.app.custom.domain.NHBResponse;
import com.nhb.app.custom.domain.RestClient;
import com.nhb.app.custom.utils.ResourceUtil;
import com.nhb.app.custom.utils.UserInfoUtils;
import com.nhb.app.custom.utils.helper.RouteHelper;
import com.nhb.app.custom.viewmodel.interfaces.ITitleBarNormal;

import retrofit2.Call;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-30 11:01
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class OrderDetailVM extends FetchDataViewModel implements ITitleBarNormal {
    private ObservableField<String> orderId = new ObservableField<>();
    public ObservableField<OrderDetailBean> orderDetail = new ObservableField<>();
    public ObservableField<String> prePrice = new ObservableField<>();

    public OrderDetailVM(String orderId) {
        this.orderId.set(orderId);
    }

    @Override
    public Call loadApiService() {
        return RestClient.getService().getOrderDetail(UserInfoUtils.getUserToken(), orderId.get());
    }

    @Override
    public void handleData(int requestCode, Object data, NHBResponse response) {
        orderDetail.set((OrderDetailBean) data);
        if (null != orderDetail.get()) {
            prePrice.set(!TextUtils.isEmpty(orderDetail.get().price) && !TextUtils.isEmpty(orderDetail.get().payAmount) ? String.format(ResourceUtil.getString(R.string.order_pre_price), String.valueOf((Double.valueOf(orderDetail.get().payAmount) - Double.valueOf(orderDetail.get().price)))) : String.valueOf(0));
        }
    }

    @Override
    public void clickFinish(View view) {
        finishActivity();
    }

    @Override
    public ObservableField<String> getTitle() {
        return new ObservableField<>(ResourceUtil.getString(R.string.order_detail));
    }

    public void clickPhone(View view) {
        ToolUtils.callPhone(view.getContext(), orderDetail.get().storePhone);
    }

    public void orderDetailItem(View view) {
        RouteHelper.getInstance().startItemDetail((Activity) view.getContext(),orderDetail.get().itemId);
    }
}
