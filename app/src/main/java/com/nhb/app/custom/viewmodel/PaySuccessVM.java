package com.nhb.app.custom.viewmodel;

import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;

import com.nhb.app.custom.R;
import com.nhb.app.custom.base.FetchDataViewModel;
import com.nhb.app.custom.bean.OrderDetailBean;
import com.nhb.app.custom.domain.NHBResponse;
import com.nhb.app.custom.domain.RestClient;
import com.nhb.app.custom.utils.ResourceUtil;
import com.nhb.app.custom.utils.UserInfoUtils;
import com.nhb.app.custom.viewmodel.interfaces.ITitleBarNormal;

import retrofit2.Call;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-07-10 16:07
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class PaySuccessVM extends FetchDataViewModel implements ITitleBarNormal {
    public ObservableField<OrderDetailBean> orderBean = new ObservableField<>();

    public PaySuccessVM(OrderDetailBean bean) {
        this.orderBean.set(bean);
    }

    @Override
    public void clickFinish(View view) {
        finishActivity();
    }

    @Override
    public ObservableField<String> getTitle() {
//        String title = orderBean.get() != null ? TextUtils.equals(orderBean.get().orderState, ResourceUtil.getString(R.string.order_pay_status_success)) ?  : ResourceUtil.getString(R.string.pay_fail) : "";
        return new ObservableField<>(ResourceUtil.getString(R.string.pay_success));
    }


    @Override
    public Call loadApiService() {
        return null;
//        return RestClient.getService().getOrderDetail(UserInfoUtils.getUserToken(), orderId.get());
    }

    @Override
    public void handleData(int requestCode, Object data, NHBResponse response) {
//        orderBean.set((OrderDetailBean) data);
    }
}
