package com.nhb.app.custom.viewmodel;

import com.nhb.app.custom.R;
import com.nhb.app.custom.recyclerview.RecyclerItemVM;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-02 18:29
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class ItemCommonSearchHistoryVM extends RecyclerItemVM<String> {

    @Override
    public int loadItemView() {
        return R.layout.item_common_search_history;
    }
}
