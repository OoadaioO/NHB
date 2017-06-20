package com.nhb.app.custom.ui.personal;

import com.nhb.app.custom.R;
import com.nhb.app.custom.base.BaseActivity;
import com.nhb.app.custom.databinding.ActivityPersonalProfileBinding;
import com.nhb.app.custom.viewmodel.PersonalProfileVM;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-29 18:44
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class PersonalProfileActivity extends BaseActivity<PersonalProfileVM, ActivityPersonalProfileBinding> {
    @Override
    protected PersonalProfileVM loadViewModel() {
        return new PersonalProfileVM();
    }

    @Override
    protected int loadLayoutId() {
        return R.layout.activity_personal_profile;
    }

    @Override
    protected void initialize() {
        viewModel.fetchRemoteData(false);
    }
}
