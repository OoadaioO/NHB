package com.nhb.app.custom.ui.items;

import android.content.Intent;

import com.nhb.app.custom.R;
import com.nhb.app.custom.base.BaseActivity;
import com.nhb.app.custom.constant.Extras;
import com.nhb.app.custom.databinding.ActivityListItemsBinding;
import com.nhb.app.custom.recyclerview.NHBRecyclerFragment;
import com.nhb.app.custom.viewmodel.ItemsListCategoryVM;
import com.nhb.app.custom.viewmodel.ItemsListVM;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-07-29 19:04
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class ItemsListActivity extends BaseActivity<ItemsListCategoryVM, ActivityListItemsBinding> {
    private String mShopCategoryId;

    @Override
    protected void intentWithNormal(Intent intent) {
        mShopCategoryId = intent.getStringExtra(Extras.SHOP_CATEGORY_ID);
    }

    @Override
    protected ItemsListCategoryVM loadViewModel() {
        return new ItemsListCategoryVM();
    }

    @Override
    protected int loadLayoutId() {
        return R.layout.activity_list_items;
    }

    @Override
    protected void initialize() {
        replaceFragmentByTag(R.id.list_item_ll_content, new NHBRecyclerFragment().setViewModel(new ItemsListVM(mShopCategoryId)), "items_list");
    }
}
