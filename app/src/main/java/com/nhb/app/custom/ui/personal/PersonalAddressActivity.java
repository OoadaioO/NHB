package com.nhb.app.custom.ui.personal;

import android.content.Intent;
import android.text.TextUtils;

import com.nhb.app.custom.R;
import com.nhb.app.custom.base.BaseActivity;
import com.nhb.app.custom.constant.Extras;
import com.nhb.app.custom.databinding.ActivityListviewRightImageBinding;
import com.nhb.app.custom.recyclerview.NHBRecyclerFragment;
import com.nhb.app.custom.viewmodel.AddressListVM;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-30 17:58
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class PersonalAddressActivity extends BaseActivity<AddressListVM, ActivityListviewRightImageBinding> {
    private boolean mIsSelectAddress;
    private String mOrderId;
    private String mItemsType;
    private String mDefaultAddress;

    @Override
    protected AddressListVM loadViewModel() {
        return new AddressListVM(mIsSelectAddress, mOrderId, mItemsType,mDefaultAddress);
    }

    @Override
    protected int loadLayoutId() {
        return R.layout.activity_listview_right_image;
    }

    @Override
    protected void initialize() {
        replaceFragmentByTag(R.id.fragment_recycler, new NHBRecyclerFragment().setViewModel(viewModel), "personal_address_list");
    }

    @Override
    protected void intentWithNormal(Intent intent) {
        super.intentWithNormal(intent);
        mIsSelectAddress = TextUtils.equals(Extras.FROM_MEMBER_CARD, intent.getStringExtra(Extras.WHERE_FROM));
        mOrderId = intent.getStringExtra(Extras.ORDER_ID);
        mItemsType = intent.getStringExtra(Extras.ITEMS_SERVER_TYPE);
        mDefaultAddress = intent.getStringExtra(Extras.DEFAULT_ADDRESS_ID);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.refreshData(false);
    }
}
