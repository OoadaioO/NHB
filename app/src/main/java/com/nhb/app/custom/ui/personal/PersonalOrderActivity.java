package com.nhb.app.custom.ui.personal;

import com.nhb.app.custom.R;
import com.nhb.app.custom.base.BaseActivity;
import com.nhb.app.custom.databinding.ActivityListviewNormalBinding;
import com.nhb.app.custom.recyclerview.NHBRecyclerFragment;
import com.nhb.app.custom.viewmodel.OrderListVM;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-29 20:18
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class PersonalOrderActivity extends BaseActivity<OrderListVM, ActivityListviewNormalBinding> {
    @Override
    protected OrderListVM loadViewModel() {
        return new OrderListVM();
    }

    @Override
    protected int loadLayoutId() {
        return R.layout.activity_listview_normal;
    }

    @Override
    protected void initialize() {
        replaceFragmentByTag(R.id.fragment_recycler, new NHBRecyclerFragment().setViewModel(viewModel), "personal_order_list");
    }
}
