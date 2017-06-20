package com.nhb.app.custom.ui.personal;

import android.content.Intent;
import android.view.View;

import com.nhb.app.custom.R;
import com.nhb.app.custom.base.BaseActivity;
import com.nhb.app.custom.bean.AddressBean;
import com.nhb.app.custom.common.view.pickerview.AddressInitTask;
import com.nhb.app.custom.constant.Constants;
import com.nhb.app.custom.constant.Extras;
import com.nhb.app.custom.databinding.ActivityAddressEditBinding;
import com.nhb.app.custom.utils.SpCustom;
import com.nhb.app.custom.viewmodel.AddressEditVM;

import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-30 20:27
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class AddressOperateActivity extends BaseActivity<AddressEditVM, ActivityAddressEditBinding> implements AddressInitTask.OnOptionsSelectListener {
    private AddressBean mAddressBean = new AddressBean();

    @Override
    protected void intentWithNormal(Intent intent) {
        super.intentWithNormal(intent);
        mAddressBean = (AddressBean) intent.getSerializableExtra(Extras.ADDRESS_DATA);
    }

    @Override
    protected AddressEditVM loadViewModel() {
        return new AddressEditVM(mAddressBean);
    }

    @Override
    protected int loadLayoutId() {
        return R.layout.activity_address_edit;
    }

    @Override
    protected void initialize() {
        viewDataBinding.addressTvProvince.setFocusable(false);
        setOnClickListener(viewDataBinding.addressTvProvince);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.address_tv_province:
                new AddressInitTask(this).setOnOptionsSelectListener(this).execute(SpCustom.get().readString(Constants.LOCATION_PROVINCE_NAME), SpCustom.get().readString(Constants.LOCATION_CITY_NAME), SpCustom.get().readString(Constants.LOCATION_AREA_NAME));
                break;
            default:
                break;
        }
    }

    @Override
    public void onOptionsSelect(Province province, City city, County county) {
        //返回的分别是三个级别的选中位置
        String tx = province.getAreaName() + city.getAreaName() + county.getAreaName();
        viewDataBinding.addressTvProvince.setText(tx);
    }

    @Override
    public void onOptionsSelect(Province province, City city) {
        //返回的分别是三个级别的选中位置
        String tx = province.getAreaName() + city.getAreaName();
        viewDataBinding.addressTvProvince.setText(tx);
    }
}
