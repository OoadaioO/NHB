package com.nhb.app.custom.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.fast.library.utils.StringUtils;
import com.nhb.app.custom.R;
import com.nhb.app.custom.base.FetchDataViewModel;
import com.nhb.app.custom.base.NHBViewModel;
import com.nhb.app.custom.domain.NHBResponse;
import com.nhb.app.custom.domain.RestClient;
import com.nhb.app.custom.utils.ResourceUtil;
import com.nhb.app.custom.utils.UserInfoUtils;
import com.nhb.app.custom.viewmodel.interfaces.ITitleBarNormal;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-07-31 17:18
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class CreateOrderVM extends NHBViewModel implements ITitleBarNormal {
    private static final int DEFAULT_ITEM_NUM = 1;
    public ObservableField<String> itemId = new ObservableField<>();
    public ObservableField<String> itemName = new ObservableField<>();
    public ObservableField<String> itemPrice = new ObservableField<>();
    public ObservableInt itemNum = new ObservableInt();
    public ObservableField<String> totalPrice = new ObservableField<>();
    public ObservableField<String> discountPrice = new ObservableField<>("0");
    public ObservableField<String> payPrice = new ObservableField<>();

    public ObservableField<String> orderId = new ObservableField<>();
    public ObservableBoolean isClickComfirm = new ObservableBoolean();

    public CreateOrderVM(String itemId, String itemName, String itemPrice) {
        this.itemId.set(itemId);
        this.itemName.set(itemName);
        this.itemPrice.set(itemPrice);
        this.itemNum.set(DEFAULT_ITEM_NUM);
        this.totalPrice.set(itemPrice);
        this.payPrice.set(itemPrice);
    }

    /**
     * 减
     *
     * @param view
     */
    public void clickSubtractView(View view) {
        if (itemNum.get() > 1) {
            itemNum.set(itemNum.get() - 1);
            totalPrice.set(StringUtils.getString(Integer.valueOf(totalPrice.get()) - Integer.valueOf(itemPrice.get())));
            payPrice.set(StringUtils.getString(Integer.valueOf(totalPrice.get()) - Integer.valueOf(discountPrice.get())));
        }
    }

    /**
     * 加
     *
     * @param view
     */
    public void clickAddView(View view) {
        itemNum.set(itemNum.get() + 1);
        totalPrice.set(StringUtils.getString(Integer.valueOf(totalPrice.get()) + Integer.valueOf(itemPrice.get())));
        payPrice.set(StringUtils.getString(Integer.valueOf(totalPrice.get()) - Integer.valueOf(discountPrice.get())));
    }

    /**
     * 点击确认
     *
     * @param view
     */
    public void clickConfirm(View view) {
        fetchRemoteData(FetchDataViewModel.CODE_INIT, RestClient.getService().createOrder(itemId.get(), StringUtils.getString(itemNum.get()), UserInfoUtils.getUserToken()));
    }

    @Override
    public void clickFinish(View view) {
        finishActivity();
    }

    @Override
    public ObservableField<String> getTitle() {
        return new ObservableField<>(ResourceUtil.getString(R.string.create_order));
    }

    @Override
    public void handleData(int requestCode, Object data, NHBResponse response) {
        orderId.set((String) data);
        isClickComfirm.notifyChange();
    }
}
