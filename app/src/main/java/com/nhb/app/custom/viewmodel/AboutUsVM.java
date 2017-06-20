package com.nhb.app.custom.viewmodel;

import android.databinding.ObservableField;
import android.view.View;

import com.fast.library.utils.ToolUtils;
import com.nhb.app.custom.R;
import com.nhb.app.custom.base.NHBViewModel;
import com.nhb.app.custom.utils.ResourceUtil;
import com.nhb.app.custom.utils.UserInfoUtils;
import com.nhb.app.custom.viewmodel.interfaces.ITitleBarNormal;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-07-03 17:22
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class AboutUsVM extends NHBViewModel implements ITitleBarNormal {

    //联系电话
    public ObservableField<String> telNumber = new ObservableField<>("电话：" + UserInfoUtils.getServicePhone());

    @Override
    public void clickFinish(View view) {
        finishActivity();
    }

    @Override
    public ObservableField<String> getTitle() {
        return new ObservableField<>(ResourceUtil.getString(R.string.about_us));
    }


    public void clickPhone(View view) {
        ToolUtils.callPhone(view.getContext(), UserInfoUtils.getServicePhone());
    }
}
