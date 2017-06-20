package com.nhb.app.custom.ui.personal;

import android.content.IntentFilter;
import android.databinding.Observable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.fast.library.ui.ToastUtil;
import com.fast.library.utils.StatusBarUtils;
import com.fast.library.utils.StringUtils;
import com.nhb.app.custom.R;
import com.nhb.app.custom.base.BaseActivity;
import com.nhb.app.custom.constant.Constants;
import com.nhb.app.custom.databinding.ActivityLoginBinding;
import com.nhb.app.custom.receiver.SMSBroadcastReceiver;
import com.nhb.app.custom.viewmodel.LoginVM;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static com.nhb.app.custom.constant.Constants.LOGIN.PHONE_LENGTH;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-07-08 11:08
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class LoginActivity extends BaseActivity<LoginVM, ActivityLoginBinding> {

    private SMSBroadcastReceiver mSMSBroadcastReceiver;

    private SmsDownTimer mSmsDownTimer;
    private Watcher mEditWatcher;
    private String mTel,mCode;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            switch (event){
                case SMSSDK.EVENT_GET_VERIFICATION_CODE://短信验证码
                    if (result == SMSSDK.RESULT_COMPLETE){
                        ToastUtil.get().shortToast(R.string.get_verify_code_success);
                    }else {
                        ToastUtil.get().shortToast(R.string.get_verify_code_fail);
                    }
                    break;
                case SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE://语音短信验证码
                    if (result == SMSSDK.RESULT_COMPLETE){
                        ToastUtil.get().shortToast(R.string.get_verify_code_yy_success);
                    }else {
                        ToastUtil.get().shortToast(R.string.get_verify_code_yy_fail);
                    }
                    break;
                case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE://短信验证码提交验证
                    if (result == SMSSDK.RESULT_COMPLETE){
//                        验证码正确
                        viewModel.toLogin(mTel,mCode);
                    }else {
//                        验证码错误
                        ToastUtil.get().shortToast(R.string.auth_code_tip_error);
                    }
                    break;
            }
        }
    };

    @Override
    protected LoginVM loadViewModel() {
        return new LoginVM();
    }

    @Override
    protected int loadLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initialize() {
        registerEventHandler();
        mEditWatcher = new Watcher();
        mSmsDownTimer = new SmsDownTimer(Constants.LOGIN.AUTH_CODE_TIME_OUT,1000);

        viewDataBinding.loginEdtPhone.addTextChangedListener(mEditWatcher);
        viewDataBinding.loginEdtCode.addTextChangedListener(mEditWatcher);

        mSMSBroadcastReceiver = new SMSBroadcastReceiver(viewDataBinding.loginEdtCode);
        //实例化过滤器并设置要过滤的广播
        IntentFilter intentFilter = new IntentFilter(Constants.SMS_RECEIVED_ACTION);
        intentFilter.setPriority(1000);
//        注册广播
        registerReceiver(mSMSBroadcastReceiver, intentFilter);
//        获取验证码
        viewModel.clickVerify.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (checkPhone()){
                    mSmsDownTimer.start();
                    SMSSDK.getVerificationCode(Constants.LOGIN.CHINA,mTel);
                }
            }
        });
//        没有收到？
        viewModel.clickNoCode.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (viewDataBinding.llLoginCantSend.getVisibility() != View.VISIBLE){
                    viewDataBinding.llLoginCantSend.setVisibility(View.VISIBLE);
                }
            }
        });
//        语音验证码
        viewModel.clickYyCode.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
//                语音验证码
                if (checkPhone()){
                    SMSSDK.getVoiceVerifyCode(Constants.LOGIN.CHINA,mTel);
                }
            }
        });
//        立即登录
        viewModel.clickLogin.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (checkLogin()){
                    SMSSDK.submitVerificationCode(Constants.LOGIN.CHINA,mTel,mCode);
                }
            }
        });
    }

    private void registerEventHandler(){
        SMSSDK.initSDK(mContext, Constants.LOGIN.APPKEY,Constants.LOGIN.APPSECRET);
        EventHandler codeHandler = new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message message = Message.obtain();
                message.arg1 = event;
                message.arg2 = result;
                message.obj = data;
                handler.sendMessage(message);
            }
        };
        SMSSDK.registerEventHandler(codeHandler);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
        // 取消广播接受
        unregisterReceiver(mSMSBroadcastReceiver);
    }

    /**
     * 获取验证码，验证手机号码
     * @return
     */
    private boolean checkPhone() {
        // 校验电话号码有效性
        mTel = viewDataBinding.loginEdtPhone.getText().toString().trim();
        if (StringUtils.checkPhoneNumber(mTel)) {
            // 验证码获取等待
            return true;
        } else {
            ToastUtil.get().shortToast(R.string.tip_wrong_number);
            return false;
        }
    }

    private boolean checkLogin(){
        boolean result = true;
        mTel = viewDataBinding.loginEdtPhone.getText().toString().trim();
        mCode = viewDataBinding.loginEdtCode.getText().toString().trim();
        if (TextUtils.isEmpty(mTel)) {
            ToastUtil.get().shortToast(R.string.auth_code_tip_empty_number);
            result = false;
        } else if (!StringUtils.checkPhoneNumber(mTel)) {
            ToastUtil.get().shortToast(R.string.tip_wrong_number);
            result = false;
        } else if (mCode.length() != Constants.LOGIN.AUTH_CODE_LENGTH_FOUR && mCode.length() != Constants.LOGIN.AUTH_CODE_LENGTH_SIX) {
            ToastUtil.get().shortToast(R.string.input_identify_code);
            result = false;
        }
        return result;
    }

    /**
     * 对输入框进行监听
     */
    private class Watcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String tel = viewDataBinding.loginEdtPhone.getText().toString().trim();
            String authCode = viewDataBinding.loginEdtCode.getText().toString().trim();

            if (tel.length() == PHONE_LENGTH) {
                viewDataBinding.loginTvGetCode.setBackgroundResource(R.drawable.selector_auth_code_btn);
                viewDataBinding.loginTvGetCode.setEnabled(true);
                viewDataBinding.loginTvGetYycode.setBackgroundResource(R.drawable.selector_auth_code_btn);
                viewDataBinding.loginTvGetYycode.setEnabled(true);
            } else {
                viewDataBinding.loginTvGetYycode.setBackgroundResource(R.drawable.bg_content_corner_9);
                viewDataBinding.loginTvGetYycode.setEnabled(false);
                viewDataBinding.loginTvGetCode.setBackgroundResource(R.drawable.bg_content_corner_9);
                viewDataBinding.loginTvGetCode.setEnabled(false);
            }

            if (tel.length() == Constants.LOGIN.PHONE_LENGTH && authCode.length() == Constants.LOGIN.AUTH_CODE_LENGTH_FOUR ||
                    authCode.length() == Constants.LOGIN.AUTH_CODE_LENGTH_SIX) {
                viewDataBinding.loginTvLogin.setBackgroundResource(R.drawable.bg_content_corner_9_main);
                viewDataBinding.loginTvLogin.setEnabled(true);
            } else {
                viewDataBinding.loginTvLogin.setBackgroundResource(R.drawable.bg_content_corner_9);
                viewDataBinding.loginTvLogin.setEnabled(false);
            }
        }
    }

    /**
     * 计时器
     */
    private class SmsDownTimer extends CountDownTimer{

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public SmsDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            viewDataBinding.loginTvGetCode.setEnabled(false);
            viewDataBinding.loginTvGetCode.setEnabled(false);
            viewDataBinding.loginTvGetCode.setBackgroundResource(R.drawable.bg_content_corner_9);
            viewDataBinding.loginTvGetCode.setText(StringUtils.getString(millisUntilFinished/1000, getString(R.string.unit_second)));
        }

        @Override
        public void onFinish() {
            viewDataBinding.loginTvGetCode.setEnabled(true);
            viewDataBinding.loginTvGetCode.setBackgroundResource(R.drawable.selector_auth_code_btn);
            viewDataBinding.loginTvGetCode.setText(R.string.get_auth_code);
        }
    }
}
