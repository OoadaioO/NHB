package com.nhb.app.custom.viewmodel;

import android.view.View;

import com.fast.library.utils.ToolUtils;
import com.nhb.app.custom.R;
import com.nhb.app.custom.bean.OrderBean;
import com.nhb.app.custom.recyclerview.RecyclerItemVM;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-29 19:18
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class ItemOrderVM extends RecyclerItemVM<OrderBean> {
    @Override
    public int loadItemView() {
        return R.layout.item_order;
    }

    public void clickPhone(View view) {
        ToolUtils.callPhone(view.getContext(), item().storePhone);
    }
}
