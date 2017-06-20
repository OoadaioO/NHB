package com.nhb.app.manager.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fast.library.ui.ContentView;
import com.fast.library.utils.StringUtils;
import com.fast.library.utils.ToolUtils;
import com.fast.mvp.loader.PresenterFactory;
import com.fast.mvp.loader.PresenterLoader;
import com.igexin.sdk.PushManager;
import com.nhb.app.library.callback.OnSuccessCallBack;
import com.nhb.app.library.config.Constant;
import com.nhb.app.manager.R;
import com.nhb.app.manager.base.CommonActivity;
import com.nhb.app.manager.contract.LoginContract;
import com.nhb.app.manager.model.SpManager;
import com.nhb.app.manager.presenter.LoginPresenterImpl;
import com.nhb.app.manager.push.PushUtils;
import com.nhb.app.manager.utils.Locker;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 说明：B端登录
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/6/20 21:21
 * <p/>
 * 版本：verson 1.0
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends CommonActivity<LoginContract.Presenter> implements LoginContract.View {

    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_pwd)
    EditText etPwd;

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(this, new PresenterFactory<LoginContract.Presenter>() {

            @Override
            public LoginContract.Presenter create() {
                return new LoginPresenterImpl();
            }
        });
    }

    @Override
    public void onInitStart() {
        super.onInitStart();
        getPresenter().attachView(this);
    }

    private void initPhoneNumber(){
        if (etPhone != null){
            if (!StringUtils.isEmpty(SpManager.getPhone())){
                etPhone.setText(SpManager.getPhone());
                etPhone.setSelection(etPhone.getText().length());
            }else {
                etPhone.setText("");
            }
        }
        if (etPwd != null){
//            if (!StringUtils.isEmpty(SpManager.getPwd())){
//                etPwd.setText(SpManager.getPwd());
//                etPwd.setSelection(etPwd.getText().length());
//            }else {
//                etPwd.setText("");
//            }
            etPwd.setText("");
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initPhoneNumber();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SpManager.isLogin()){
            showActivity(MainActivity.class);
        }else {
            initPhoneNumber();
        }
    }

    @Override
    protected void initTitleBar(View titleView, TextView tvTitle) {
        tvTitle.setText(R.string.login);
    }

    @OnClick({R.id.btn_login, R.id.tv_call})
    public void onBtnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_call://拨打客服电话
                ToolUtils.callPhone(this, getString(R.string.customer_phone_number));
                break;
            case R.id.btn_login://登录
                getPresenter().login(etPhone.getText().toString(), etPwd.getText().toString(),
                        PushManager.getInstance().getClientid(mContext),new OnSuccessCallBack() {
                    @Override
                    public void onSuccess() {
                        PushUtils.bind(LoginActivity.this);
                        showActivity(MainActivity.class);
                    }

                    @Override
                    public void onFail() {

                    }
                });
                break;
        }
    }

    @Override
    public void showError(String msg) {
        shortToast(msg);
    }

    @Override
    public void loading() {
        super.showLoading();
        showLoading();
    }

    @Override
    public void disLoading() {
        super.dismissLoading();
        dismissLoading();
    }
}
