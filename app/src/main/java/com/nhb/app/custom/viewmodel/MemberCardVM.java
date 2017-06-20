package com.nhb.app.custom.viewmodel;

import android.databinding.ObservableField;
import android.view.View;

import com.fast.library.ui.ToastUtil;
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
 * CreateData:2016-07-01 23:46
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class MemberCardVM extends NHBViewModel implements ITitleBarNormal {
    @Override
    public void clickFinish(View view) {
        finishActivity();
    }

    @Override
    public ObservableField<String> getTitle() {
        return new ObservableField<>(ResourceUtil.getString(R.string.personal_member));
    }

    public void userMemberCard(String orderId, String addressId, String cardType, String consumeCode, String markedWords) {
        fetchRemoteData(FetchDataViewModel.CODE_INIT, RestClient.getService().useMemberCard(UserInfoUtils.getUserToken(),orderId,addressId,cardType,consumeCode,markedWords));
    }

    @Override
    public void handleData(int requestCode, Object data, NHBResponse response) {
        super.handleData(requestCode, data, response);
        finishActivity();
        ToastUtil.get().shortToast(ResourceUtil.getString(R.string.use_card_success));
    }
}
