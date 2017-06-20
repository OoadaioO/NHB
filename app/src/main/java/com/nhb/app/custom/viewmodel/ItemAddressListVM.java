package com.nhb.app.custom.viewmodel;

import com.nhb.app.custom.R;
import com.nhb.app.custom.bean.RemindBean;
import com.nhb.app.custom.recyclerview.RecyclerItemVM;


/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-25 12:03
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class ItemAddressListVM extends RecyclerItemVM<RemindBean> {
    @Override
    public int loadItemView() {
        return R.layout.item_address;
    }
}
