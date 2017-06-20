package com.nhb.app.custom.viewmodel;

import android.content.Intent;
import android.databinding.ObservableField;
import android.os.Handler;
import android.view.View;

import com.fast.library.ui.ToastUtil;
import com.fast.library.utils.StringUtils;
import com.nhb.app.custom.R;
import com.nhb.app.custom.base.BaseApplication;
import com.nhb.app.custom.base.FetchDataViewModel;
import com.nhb.app.custom.bean.UserBasicInfoBean;
import com.nhb.app.custom.constant.Constants;
import com.nhb.app.custom.constant.Extras;
import com.nhb.app.custom.domain.NHBResponse;
import com.nhb.app.custom.domain.RestClient;
import com.nhb.app.custom.receiver.PushUtils;
import com.nhb.app.custom.ui.launch.MainActivity;
import com.nhb.app.custom.utils.SpCustom;
import com.nhb.app.custom.utils.UserInfoUtils;

import retrofit2.Call;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-29 18:02
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class PersonalProfileVM extends FetchDataViewModel {
    private static final int REQUEST_CODE_LOGIN_OUT_CODE = 0;
    public ObservableField<UserBasicInfoBean> userInfo = new ObservableField<>();

    public void clickLeftFinish(View view) {
        finishActivity();
    }

    /**
     * 退出登陆
     */
    public void clickLoginOut(View view) {
        fetchRemoteData(REQUEST_CODE_LOGIN_OUT_CODE, RestClient.getService().toLogOut(UserInfoUtils.getUserToken()));
    }

    @Override
    public void handleData(int requestCode, Object data, NHBResponse response) {
        super.handleData(requestCode, data, response);
        switch (requestCode) {
            case REQUEST_CODE_LOGIN_OUT_CODE:
                PushUtils.unBind(BaseApplication.appContext);
                UserInfoUtils.logOut();
                ToastUtil.get().shortToast(R.string.logout_success);
                startActivity(new Intent(BaseApplication.appContext, MainActivity.class).putExtra(Extras.RELAUNCH, true));
                finishDelayed();
                break;
            case FetchDataViewModel.CODE_INIT:
                UserBasicInfoBean personalBean = (UserBasicInfoBean) data;
                if (StringUtils.isEmpty(personalBean.userNote)){
                    personalBean.userNote = UserInfoUtils.DEFAULT_USE_NOTE;
                }
                if (StringUtils.isEmpty(personalBean.nickName)){
                    personalBean.nickName = personalBean.mobile;
                }
                if (StringUtils.isEmpty(personalBean.address)){
                    personalBean.address = SpCustom.get().readString(Constants.LOCATION_AREA_NAME);
                }
                userInfo.set(personalBean);
                break;
            default:
                break;

        }
    }

    /**
     * 延迟关闭
     */
    public void finishDelayed() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                finishActivity();
            }
        }, 500);
    }

    @Override
    public Call loadApiService() {
        return RestClient.getService().getPersonalInfo(UserInfoUtils.getUserToken());
    }
}
