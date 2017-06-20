package com.nhb.app.custom.ui.personal;

import com.nhb.app.custom.R;
import com.nhb.app.custom.base.BaseActivity;
import com.nhb.app.custom.databinding.ActivityListviewNormalBinding;
import com.nhb.app.custom.recyclerview.NHBRecyclerFragment;
import com.nhb.app.custom.viewmodel.PersonalCollectionVM;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-07-01 17:10
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class PersonalCollectionActivity extends BaseActivity<PersonalCollectionVM, ActivityListviewNormalBinding> {
    @Override
    protected PersonalCollectionVM loadViewModel() {
        return new PersonalCollectionVM();
    }

    @Override
    protected int loadLayoutId() {
        return R.layout.activity_listview_normal;
    }

    @Override
    protected void initialize() {
        replaceFragmentByTag(R.id.fragment_recycler, new NHBRecyclerFragment().setViewModel(viewModel), "personal_collection_list");
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.refreshData();
    }
}
