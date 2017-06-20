package com.nhb.app.custom.viewmodel;

import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.view.View;

import com.fast.library.ui.ToastUtil;
import com.fast.library.utils.StringUtils;
import com.nhb.app.custom.R;
import com.nhb.app.custom.base.FetchDataViewModel;
import com.nhb.app.custom.bean.UserBasicInfoBean;
import com.nhb.app.custom.constant.Extras;
import com.nhb.app.custom.domain.NHBResponse;
import com.nhb.app.custom.domain.RestClient;
import com.nhb.app.custom.ui.personal.PersonalAddressActivity;
import com.nhb.app.custom.ui.personal.PersonalCollectionActivity;
import com.nhb.app.custom.ui.personal.PersonalMemberCardActivity;
import com.nhb.app.custom.ui.personal.PersonalOrderActivity;
import com.nhb.app.custom.ui.personal.PersonalProfileActivity;
import com.nhb.app.custom.utils.ResourceUtil;
import com.nhb.app.custom.utils.UserInfoUtils;

import retrofit2.Call;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-29 14:20
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class PersonalFragmentVM extends FetchDataViewModel {
    public ObservableField<UserBasicInfoBean> personalData = new ObservableField<>();
    public ObservableBoolean tokenInvalid = new ObservableBoolean();

    @Override
    public Call loadApiService() {
        return RestClient.getService().getPersonalInfo(UserInfoUtils.getUserToken());
    }

    @Override
    public void handleData(int requestCode, Object data, NHBResponse response) {
        UserBasicInfoBean userBasicInfoBean = (UserBasicInfoBean) data;
        if (null != userBasicInfoBean) {
            if (StringUtils.isEmpty(userBasicInfoBean.userNote)) {
                userBasicInfoBean.userNote = UserInfoUtils.DEFAULT_USE_NOTE;
            }
            if (StringUtils.isEmpty(userBasicInfoBean.nickName)) {
                userBasicInfoBean.nickName = userBasicInfoBean.mobile;
            }
            personalData.set(userBasicInfoBean);
        } else {
            tokenInvalid();
        }
    }

    @Override
    public void handleFailedData(int requestCode, String errorMessage) {
        tokenInvalid();
    }

    /**
     * token失效
     */
    private void tokenInvalid() {
        ToastUtil.get().shortToast(ResourceUtil.getString(R.string.login_out_of_date));
        UserInfoUtils.clearToken();
        tokenInvalid.set(true);
    }

    /**
     * 跳转到个人信息
     *
     * @param view
     */
    public void clickPersonalInfo(View view) {
        startActivity(new Intent(view.getContext(), PersonalProfileActivity.class));
    }

    /**
     * 跳转到个人订单
     *
     * @param view
     */
    public void clickPersonalOrder(View view) {
        startActivity(new Intent(view.getContext(), PersonalOrderActivity.class));
    }

    /**
     * 跳转到个人地址
     *
     * @param view
     */
    public void clickPersonalAddress(View view) {
        startActivity(new Intent(view.getContext(), PersonalAddressActivity.class).putExtra(Extras.DEFAULT_ADDRESS_ID, null != personalData.get() ? personalData.get().addressId : ""));
    }

    /**
     * 跳转到个人收藏
     *
     * @param view
     */
    public void clickPersonalCollection(View view) {
        startActivity(new Intent(view.getContext(), PersonalCollectionActivity.class));
    }

    /**
     * 跳转到个人会员卡
     *
     * @param view
     */
    public void clickPersonalMember(View view) {
        startActivity(new Intent(view.getContext(), PersonalMemberCardActivity.class));
    }
}
