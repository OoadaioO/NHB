package com.nhb.app.custom.viewmodel;

import android.app.Activity;
import android.databinding.ObservableField;
import android.view.View;

import com.fast.library.ui.ToastUtil;
import com.fast.library.utils.GsonUtils;
import com.nhb.app.custom.R;
import com.nhb.app.custom.base.NHBViewModel;
import com.nhb.app.custom.domain.NHBResponse;
import com.nhb.app.custom.domain.RestClient;
import com.nhb.app.custom.utils.PayUtils;
import com.nhb.app.custom.utils.ResourceUtil;
import com.nhb.app.custom.utils.UserInfoUtils;
import com.nhb.app.custom.viewmodel.interfaces.ITitleBarNormal;
import com.pingplusplus.android.PaymentActivity;
import com.pingplusplus.android.Pingpp;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-07-10 12:45
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class UserPayVM extends NHBViewModel implements ITitleBarNormal {

    public static final int REQUEST_CODE_PAY = 1001;

    private Activity mActivity;
    public ObservableField<String> itemName = new ObservableField<>();
    public ObservableField<String> orderPrice = new ObservableField<>();
    public ObservableField<String> orderId = new ObservableField<>();
    public ObservableField<String> payChannel = new ObservableField<>();

    public UserPayVM(Activity activity,String itemName, String orderPrice, String orderId) {
        this.mActivity = activity;
        this.itemName.set(itemName);
        this.orderPrice.set(orderPrice);
        this.orderId.set(orderId);
    }

    @Override
    public void clickFinish(View view) {
        finishActivity();
    }

    @Override
    public ObservableField<String> getTitle() {
        return new ObservableField<>(ResourceUtil.getString(R.string.user_pay));
    }

    /**
     * 确认支付
     *
     * @param view
     */
    public void pay(View view) {
        fetchRemoteData(REQUEST_CODE_PAY, RestClient.getService().payOrder(UserInfoUtils.getUserToken(),orderId.get(),payChannel.get()));
    }

    @Override
    public void handleData(int requestCode, Object data, NHBResponse response) {
        super.handleData(requestCode, data, response);
        Pingpp.createPayment(mActivity, GsonUtils.optString((String)data,"charge"));
    }
}
