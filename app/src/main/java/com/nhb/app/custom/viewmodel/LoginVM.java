package com.nhb.app.custom.viewmodel;

import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.view.View;

import com.fast.library.ui.ToastUtil;
import com.igexin.sdk.PushManager;
import com.nhb.app.custom.R;
import com.nhb.app.custom.base.BaseApplication;
import com.nhb.app.custom.base.NHBViewModel;
import com.nhb.app.custom.bean.UserLoginBean;
import com.nhb.app.custom.constant.Constants;
import com.nhb.app.custom.domain.NHBResponse;
import com.nhb.app.custom.domain.RestClient;
import com.nhb.app.custom.receiver.PushUtils;
import com.nhb.app.custom.ui.launch.MainActivity;
import com.nhb.app.custom.ui.personal.WebViewActivity;
import com.nhb.app.custom.utils.ResourceUtil;
import com.nhb.app.custom.utils.UserInfoUtils;
import com.nhb.app.custom.viewmodel.interfaces.ITitleBarNormal;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-07-07 16:33
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class LoginVM extends NHBViewModel implements ITitleBarNormal {
    private static final int REQUEST_CODE_LOGIN = 1;
    public ObservableBoolean clickVerify = new ObservableBoolean();
    public ObservableBoolean clickLogin = new ObservableBoolean();
    public ObservableBoolean clickNoCode = new ObservableBoolean();
    public ObservableBoolean clickYyCode = new ObservableBoolean();

    @Override
    public void clickFinish(View view) {
        finishActivity();
    }

    @Override
    public ObservableField<String> getTitle() {
        return new ObservableField<>(ResourceUtil.getString(R.string.login));
    }

    /**
     * 注册&登录
     *
     * @param code
     * @param phone
     */
    public void toLogin(String phone, String code) {
        fetchRemoteData(REQUEST_CODE_LOGIN, RestClient.getService().toLogin(phone, code, PushManager.getInstance().getClientid(BaseApplication.appContext), Constants.LOGIN_APPCLIENT));
    }

    @Override
    public void handleData(int requestCode, Object data, NHBResponse response) {
        switch (requestCode) {
            case REQUEST_CODE_LOGIN:
                ToastUtil.get().shortToast(R.string.registered_success);
                UserLoginBean userLoginBean = (UserLoginBean) data;
                //保存信息到本地
                UserInfoUtils.saveUserLoginInfo(userLoginBean);
                PushUtils.bind(BaseApplication.appContext);
                startActivity(new Intent(BaseApplication.appContext, MainActivity.class));
                finishActivity();
                break;
            default:
                break;
        }
    }

    /**
     * 获取验证码
     *
     * @param view
     */
    public void clickVerifyCode(View view) {
        clickVerify.notifyChange();
    }

    public void clickLoginBtn(View view) {
        clickLogin.notifyChange();
    }

    /**
     * 没有收到验证码
     *
     * @param view
     */
    public void clickNoCodeBtn(View view) {
        clickNoCode.notifyChange();
    }

    /**
     * 使用协议
     *
     * @param view
     */
    public void clickProtocol(View view) {
        startActivity(new Intent(view.getContext(), WebViewActivity.class).putExtra(Constants.WEB_URL, UserInfoUtils.getCustomerUserAgreement()));
    }

    /**
     * 获取语音验证码
     *
     * @param view
     */
    public void clickYyCodeBtn(View view) {
        clickYyCode.notifyChange();
    }
}
