package com.nhb.app.custom.ui.others.fragment;

import com.nhb.app.custom.R;
import com.nhb.app.custom.base.BaseFragment;
import com.nhb.app.custom.databinding.FragmentPersonalBinding;
import com.nhb.app.custom.viewmodel.MoreFragmentVM;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-07-03 16:00
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class MoreFragment extends BaseFragment<MoreFragmentVM, FragmentPersonalBinding> {
    @Override
    protected MoreFragmentVM loadViewModel() {
        return new MoreFragmentVM();
    }

    @Override
    protected int loadLayoutId() {
        return R.layout.fragment_more;
    }

    @Override
    protected void initialize() {
        viewModel.updateCacheUI();
        viewModel.fetchRemoteData();
    }
}
