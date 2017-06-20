package com.nhb.app.manager.presenter;

import com.fast.library.utils.GsonUtils;
import com.fast.library.utils.StringUtils;
import com.fast.library.utils.UIUtils;
import com.nhb.app.library.callback.OnSuccessCallBack;
import com.nhb.app.library.http.NHBSubscriber;
import com.nhb.app.manager.R;
import com.nhb.app.manager.bean.UserInfo;
import com.nhb.app.manager.contract.LoginContract;
import com.nhb.app.manager.model.ManagerAPI;
import com.nhb.app.manager.model.SpManager;

/**
 * 说明：LoginPresenterImpl
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/6/20 21:16
 * <p/>
 * 版本：verson 1.0
 */
public class LoginPresenterImpl extends LoginContract.Presenter{

    @Override
    public void onStart() {

    }

    private boolean checkLoginInfo(String phone,String pwd){
        int str = 0;
        if (StringUtils.isEmpty(phone)){
            str = R.string.login_phone_empty;
        }else if (StringUtils.isEmpty(pwd)){
            str = R.string.login_pwd_empty;
        }else if (!StringUtils.isPhone(phone)){
            str = R.string.login_phone_error;
        }
        if (str == 0){
            return true;
        }else {
            getMvpView().showError(UIUtils.getString(str));
            return false;
        }
    }

    @Override
    public void login(final String phone, final String pwd, String udid, final OnSuccessCallBack callBack) {
        if (checkLoginInfo(phone,pwd)){
            getMvpView().loading();
            ManagerAPI.getAPI().login(phone,pwd, udid)
                    .subscribe(new NHBSubscriber<UserInfo>() {
                        @Override
                        public void onError(int code, String msg) {
                            getMvpView().showError(UIUtils.getString(R.string.login_fail));
                            if (callBack != null){
                                callBack.onFail();
                            }
                        }

                        @Override
                        public void onNext(UserInfo userInfo) {
                            if (StringUtils.isEmpty(userInfo.token)){
                                onError(1,UIUtils.getString(R.string.login_fail));
                            }else {
                                SpManager.savePhone(phone);
                                SpManager.savePwd(pwd);
                                SpManager.saveUserInfo(GsonUtils.toJson(userInfo));
                                if (callBack != null){
                                    callBack.onSuccess();
                                }
                            }
                        }

                        @Override
                        public void onCompleted() {
                            getMvpView().disLoading();
                        }
                    });
        }else {
            if (callBack != null){
                callBack.onFail();
            }
        }
    }


}
