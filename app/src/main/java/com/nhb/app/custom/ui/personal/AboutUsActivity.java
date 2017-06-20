package com.nhb.app.custom.ui.personal;

import com.nhb.app.custom.R;
import com.nhb.app.custom.base.BaseActivity;
import com.nhb.app.custom.databinding.ActivityAboutUsBinding;
import com.nhb.app.custom.viewmodel.AboutUsVM;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-07-03 17:54
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class AboutUsActivity extends BaseActivity<AboutUsVM, ActivityAboutUsBinding> {
    @Override
    protected AboutUsVM loadViewModel() {
        return new AboutUsVM();
    }

    @Override
    protected int loadLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initialize() {

    }
}
