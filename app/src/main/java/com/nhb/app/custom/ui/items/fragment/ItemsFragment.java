package com.nhb.app.custom.ui.items.fragment;

import android.content.Intent;
import android.text.TextUtils;

import com.nhb.app.custom.R;
import com.nhb.app.custom.base.BaseFragment;
import com.nhb.app.custom.common.view.CommonFilter;
import com.nhb.app.custom.constant.Constants;
import com.nhb.app.custom.constant.EventCenter;
import com.nhb.app.custom.constant.Extras;
import com.nhb.app.custom.databinding.FragmentItemsBinding;
import com.nhb.app.custom.location.LocationServiceutils;
import com.nhb.app.custom.recyclerview.NHBRecyclerFragment;
import com.nhb.app.custom.utils.SpCustom;
import com.nhb.app.custom.viewmodel.ItemsFragmentVM;
import com.nhb.app.custom.viewmodel.ItemsListVM;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

import static android.app.Activity.RESULT_OK;


/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-27 18:49
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class ItemsFragment extends BaseFragment<ItemsFragmentVM, FragmentItemsBinding> implements CommonFilter.OnTabItemSelectedListener {

    private String mItemId = "";
    private String mTagId = "0";
    private String mOrderId = CommonFilter.ORDER_LIST;

    private NHBRecyclerFragment mItemsListFragment;
    private NHBRecyclerFragment mItemsGridFragment;

    @Override
    protected ItemsFragmentVM loadViewModel() {
        return new ItemsFragmentVM();
    }

    @Override
    protected int loadLayoutId() {
        return R.layout.fragment_items;
    }

    @Override
    protected void initialize() {
        EventBus.getDefault().register(this);
        viewDataBinding.itemsListFilter.setOnTabItemSelectedListener(this);
        viewDataBinding.itemsListFilter.mOnTabItemSelectedListener.onItemSelected(mItemId, mTagId, mOrderId);
    }

    // TODO: 9/3/16 Evenbus 
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void refreshItemsData(EventCenter<String> center) {
        if (Constants.EventType.LOCATION_AREA_CHANGE.equals(center.type)){
            String locationName = TextUtils.isEmpty(SpCustom.get().readString(Constants.LOCATION_AREA_NAME)) ? LocationServiceutils.getInstance().locationName : SpCustom.get().readString(Constants.LOCATION_AREA_NAME);
            viewDataBinding.itemsTitle.titleBarHomeTvCity.setText(locationName);
            viewDataBinding.itemsListFilter.mOnTabItemSelectedListener.onItemSelected(mItemId, mTagId, mOrderId);
        }
    }

    @Override
    public void onItemSelected(String itemId, String tagId, String orderId) {
        mItemId = itemId;
        mTagId = tagId;
        mOrderId = orderId;
        switch (orderId) {
            case CommonFilter.ORDER_LIST:
                mItemsListFragment = new NHBRecyclerFragment().setViewModel(new ItemsListVM(itemId, tagId, orderId));
                replaceFragmentByTag(R.id.fragment_recycler, mItemsListFragment, "items_fragment_list");
                break;
            case CommonFilter.ORDER_GRID:
                mItemsGridFragment = new NHBRecyclerFragment().setViewModel(new ItemsListVM(itemId, tagId, orderId));
                replaceFragmentByTag(R.id.fragment_recycler, mItemsGridFragment, "items_fragment_grid");
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
