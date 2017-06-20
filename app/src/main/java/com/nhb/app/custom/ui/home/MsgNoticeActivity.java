package com.nhb.app.custom.ui.home;

import com.nhb.app.custom.R;
import com.nhb.app.custom.base.BaseActivity;
import com.nhb.app.custom.databinding.ActivityListviewNormalBinding;
import com.nhb.app.custom.recyclerview.NHBRecyclerFragment;
import com.nhb.app.custom.viewmodel.MsgNoticeVM;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-26 17:35
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class MsgNoticeActivity extends BaseActivity<MsgNoticeVM, ActivityListviewNormalBinding> {
    @Override
    protected MsgNoticeVM loadViewModel() {
        return new MsgNoticeVM();
    }

    @Override
    protected int loadLayoutId() {
        return R.layout.activity_listview_normal;
    }

    @Override
    protected void initialize() {
        replaceFragmentByTag(R.id.fragment_recycler, new NHBRecyclerFragment().setViewModel(viewModel), "msg_notice_list");
    }
}
