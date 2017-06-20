package com.nhb.app.custom.viewmodel;


import com.nhb.app.custom.R;
import com.nhb.app.custom.recyclerview.RecyclerItemVM;

/**
 * Created by pengxiaofang on 6/01/16.
 */
public class SearchKeywordItemVM extends RecyclerItemVM<String> {

    @Override
    public int loadItemView() {
        return R.layout.item_search_keyword;
    }
}
