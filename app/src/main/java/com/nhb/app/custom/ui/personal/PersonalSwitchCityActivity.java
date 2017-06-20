package com.nhb.app.custom.ui.personal;

import android.content.Intent;

import com.nhb.app.custom.R;
import com.nhb.app.custom.base.BaseActivity;
import com.nhb.app.custom.constant.Extras;
import com.nhb.app.custom.databinding.ActivitySwitchCityBinding;
import com.nhb.app.custom.viewmodel.SwitchCityVM;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-07-11 17:01
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class PersonalSwitchCityActivity extends BaseActivity<SwitchCityVM, ActivitySwitchCityBinding> {
    private String mSelectCityId;
    private String mSelectProvinceId;

    @Override
    protected void intentWithNormal(Intent intent) {
        mSelectProvinceId=intent.getStringExtra(Extras.SELECT_PROVINCE_ID);
        mSelectCityId = intent.getStringExtra(Extras.SELECT_CITY_ID);
    }

    @Override
    protected SwitchCityVM loadViewModel() {
        return new SwitchCityVM(mSelectProvinceId,mSelectCityId);
    }

    @Override
    protected int loadLayoutId() {
        return R.layout.activity_switch_city;
    }

    @Override
    protected void initialize() {
        viewModel.fetchRemoteData(false);
    }
}
