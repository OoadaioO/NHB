package com.nhb.app.custom.viewmodel;

import android.databinding.ObservableInt;

import com.nhb.app.custom.R;
import com.nhb.app.custom.bean.ItemCategoryChildBean;
import com.nhb.app.custom.recyclerview.RecyclerItemVM;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-27 15:30
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class ItemFilterChildVM extends RecyclerItemVM<ItemCategoryChildBean> {
    public ObservableInt selectedItemsChildIndex = new ObservableInt();

    public ItemFilterChildVM(ObservableInt selectedItemsChildIndex) {
        this.selectedItemsChildIndex=selectedItemsChildIndex;
    }

    @Override
    public int loadItemView() {
        return R.layout.listitem_filter_child;
    }
}
