package com.nhb.app.custom.viewmodel;

import com.nhb.app.custom.R;
import com.nhb.app.custom.bean.CommonEntranceBean;
import com.nhb.app.custom.recyclerview.RecyclerItemVM;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-26 00:29
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class ItemHomeButtonsVM extends RecyclerItemVM<CommonEntranceBean> {
    @Override
    public int loadItemView() {
        return R.layout.item_common_header_button;
    }
}
