package com.nhb.app.custom.ui.items;

import android.content.Intent;
import android.databinding.Observable;

import com.nhb.app.custom.R;
import com.nhb.app.custom.base.BaseActivity;
import com.nhb.app.custom.constant.Extras;
import com.nhb.app.custom.databinding.ActivityCreateOrderBinding;
import com.nhb.app.custom.utils.helper.RouteHelper;
import com.nhb.app.custom.viewmodel.CreateOrderVM;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-07-31 18:51
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class CreateOrderActivity extends BaseActivity<CreateOrderVM, ActivityCreateOrderBinding> {
    private String mItemId;
    private String mItemName;
    private String mItemPrice;

    @Override
    protected void intentWithNormal(Intent intent) {
        mItemId = intent.getStringExtra(Extras.ITEM_ID);
        mItemName = intent.getStringExtra(Extras.ITEM_NAME);
        mItemPrice = intent.getStringExtra(Extras.ITEM_PRICE);
    }

    @Override
    protected CreateOrderVM loadViewModel() {
        return new CreateOrderVM(mItemId, mItemName, mItemPrice);
    }

    @Override
    protected int loadLayoutId() {
        return R.layout.activity_create_order;
    }

    @Override
    protected void initialize() {
        viewModel.isClickComfirm.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                RouteHelper.getInstance().startUserPay(mContext, mItemName, mItemPrice, viewModel.orderId.get());
                finish();
            }
        });
    }
}
