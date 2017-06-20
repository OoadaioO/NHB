package com.nhb.app.custom.ui.launch;

import com.nhb.app.custom.R;
import com.nhb.app.custom.base.BaseActivity;
import com.nhb.app.custom.base.NHBViewModel;
import com.nhb.app.custom.databinding.ActivityGuidepageBinding;
import com.nhb.app.custom.ui.adapter.GuidePageAdapter;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-25 16:06
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class GuidePageActivity extends BaseActivity<NHBViewModel, ActivityGuidepageBinding> {
    @Override
    protected NHBViewModel loadViewModel() {
        return new NHBViewModel();
    }

    @Override
    protected int loadLayoutId() {
        return R.layout.activity_guidepage;
    }

    @Override
    protected void initialize() {
        viewDataBinding.autoSlideView.setAdapter(new GuidePageAdapter(this));
    }

    /**
     * 禁止返回
     */
    @Override
    public void onBackPressed() {

    }
}
