package com.nhb.app.custom.ui.personal;

import android.content.Intent;

import com.nhb.app.custom.R;
import com.nhb.app.custom.base.BaseActivity;
import com.nhb.app.custom.constant.Extras;
import com.nhb.app.custom.databinding.ActivityOrderDetailBinding;
import com.nhb.app.custom.viewmodel.OrderDetailVM;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-30 14:12
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class OrderDetailActivity extends BaseActivity<OrderDetailVM, ActivityOrderDetailBinding> {

    private String orderId;

    @Override
    protected void intentWithNormal(Intent intent) {
        super.intentWithNormal(intent);
        orderId = intent.getStringExtra(Extras.ORDER_ID);
    }

    @Override
    protected OrderDetailVM loadViewModel() {
        return new OrderDetailVM(orderId);
    }

    @Override
    protected int loadLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initialize() {
        viewModel.fetchRemoteData(false);
    }
}
