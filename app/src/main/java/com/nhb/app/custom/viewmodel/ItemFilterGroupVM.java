package com.nhb.app.custom.viewmodel;

import android.databinding.ObservableInt;

import com.nhb.app.custom.R;
import com.nhb.app.custom.bean.ShopCategoryGroupBean;
import com.nhb.app.custom.recyclerview.RecyclerItemVM;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-27 15:28
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class ItemFilterGroupVM extends RecyclerItemVM<ShopCategoryGroupBean> {
    public ObservableInt selectedItemsGroupIndex = new ObservableInt();

    public ItemFilterGroupVM(ObservableInt selectedItemsGroupIndex) {
        this.selectedItemsGroupIndex=selectedItemsGroupIndex;
    }

    @Override
    public int loadItemView() {
        return R.layout.listitem_filter_group;
    }
}
