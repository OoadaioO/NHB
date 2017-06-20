package com.nhb.app.custom.ui.items;

import android.content.Intent;
import android.databinding.Observable;
import android.text.TextUtils;

import com.nhb.app.custom.R;
import com.nhb.app.custom.base.BaseActivity;
import com.nhb.app.custom.bean.OrderDetailBean;
import com.nhb.app.custom.constant.Extras;
import com.nhb.app.custom.databinding.ActivityPaySuccessBinding;
import com.nhb.app.custom.utils.ResourceUtil;
import com.nhb.app.custom.utils.SpCustom;
import com.nhb.app.custom.utils.UserInfoUtils;
import com.nhb.app.custom.viewmodel.PaySuccessVM;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-07-10 17:24
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class UserPaySuccessActivity extends BaseActivity<PaySuccessVM, ActivityPaySuccessBinding> {

    private String orderId;
    private OrderDetailBean orderDetailBean;

    @Override
    protected void intentWithNormal(Intent intent) {
        super.intentWithNormal(intent);
        orderId = intent.getStringExtra(Extras.ORDER_ID);
        orderDetailBean = SpCustom.getOrderDetailBean();
    }

    @Override
    protected PaySuccessVM loadViewModel() {
        return new PaySuccessVM(orderDetailBean);
    }

    @Override
    protected int loadLayoutId() {
        return R.layout.activity_pay_success;
    }

    @Override
    protected void initialize() {
//        viewModel.fetchRemoteData();
        if (orderDetailBean != null && orderDetailBean.orderId == orderId){
            viewModel.orderBean.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
                @Override
                public void onPropertyChanged(Observable observable, int i) {
                    viewDataBinding.layoutTitle.tvTitle.setText(viewModel.orderBean.get() != null ? TextUtils.equals(viewModel.orderBean.get().orderState, ResourceUtil.getString(R.string.order_pay_status_success)) ? ResourceUtil.getString(R.string.pay_success) : ResourceUtil.getString(R.string.pay_fail) : "");
                }
            });
        }
    }
}
