package com.nhb.app.custom.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;

import com.fast.library.ui.ToastUtil;
import com.fast.library.utils.ToolUtils;
import com.nhb.app.custom.R;
import com.nhb.app.custom.base.FetchDataViewModel;
import com.nhb.app.custom.base.NHBViewModel;
import com.nhb.app.custom.domain.NHBResponse;
import com.nhb.app.custom.domain.RestClient;
import com.nhb.app.custom.utils.ResourceUtil;
import com.nhb.app.custom.utils.UserInfoUtils;
import com.nhb.app.custom.viewmodel.interfaces.ITitleBarNormal;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-09-01 18:15
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class FeedBackVM extends NHBViewModel implements ITitleBarNormal {
    public ObservableField<String> content = new ObservableField<>();
    public ObservableField<String> phone = new ObservableField<>();
    //提交按钮是否可用
    public ObservableBoolean submitEnable = new ObservableBoolean();

    @Override
    public void clickFinish(View view) {
        finishActivity();
    }

    @Override
    public ObservableField<String> getTitle() {
        return new ObservableField<>(ResourceUtil.getString(R.string.feedback));
    }

    public void contentEditable(Editable editable) {
        content.set(editable.toString().trim());
        checkSubmitEnable();
    }

    public void phoneEditable(Editable editable) {
        phone.set(editable.toString().trim());
        checkSubmitEnable();
    }

    public void checkSubmitEnable() {
        submitEnable.set(!TextUtils.isEmpty(content.get()) && !TextUtils.isEmpty(phone.get()));
    }

    /**
     * 提交
     *
     * @param view
     */
    public void clickSubmit(View view) {
        fetchRemoteData(FetchDataViewModel.CODE_INIT, RestClient.getService().submitFeedBack(content.get(), phone.get()));
    }

    /**
     * 联系客服
     *
     * @param view
     */
    public void clickContactServerCustomer(View view) {
        ToolUtils.callPhone(view.getContext(), UserInfoUtils.getServicePhone());
    }

    @Override
    public void handleData(int requestCode, Object data, NHBResponse response) {
        ToastUtil.get().shortToast(ResourceUtil.getString(R.string.submit_success));
        finishActivity();
    }
}
