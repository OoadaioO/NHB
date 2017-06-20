package com.nhb.app.custom.viewmodel;

import com.nhb.app.custom.R;
import com.nhb.app.custom.bean.ItemsItemBean;
import com.nhb.app.custom.recyclerview.RecyclerItemVM;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-07-01 17:00
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class ItemCollectionVM extends RecyclerItemVM<ItemsItemBean> {
    @Override
    public int loadItemView() {
        return R.layout.item_collection_items;
    }
}
