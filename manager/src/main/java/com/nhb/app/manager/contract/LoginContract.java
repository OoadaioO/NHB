package com.nhb.app.manager.contract;

import com.nhb.app.library.callback.OnSuccessCallBack;
import com.nhb.app.manager.base.BasePresenter;
import com.nhb.app.manager.base.BaseView;

/**
 * 说明：LoginContract
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/6/20 21:15
 * <p/>
 * 版本：verson 1.0
 */
public interface LoginContract {

    interface View extends BaseView {
        void showError(String msg);
        void loading();
        void disLoading();
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void login(String phone, String pwd, String udid,OnSuccessCallBack callBack);
    }
}
