package com.nhb.app.custom.viewmodel;

import android.content.Intent;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;

import com.fast.library.ui.ToastUtil;
import com.nhb.app.custom.R;
import com.nhb.app.custom.base.FetchDataViewModel;
import com.nhb.app.custom.base.NHBViewModel;
import com.nhb.app.custom.bean.MemberCardBean;
import com.nhb.app.custom.common.dialog.QRCodeDialog;
import com.nhb.app.custom.constant.Constants;
import com.nhb.app.custom.constant.Extras;
import com.nhb.app.custom.domain.NHBResponse;
import com.nhb.app.custom.domain.RestClient;
import com.nhb.app.custom.ui.personal.PersonalAddressActivity;
import com.nhb.app.custom.ui.personal.PersonalMemberCardActivity;
import com.nhb.app.custom.utils.ResourceUtil;
import com.nhb.app.custom.utils.UserInfoUtils;
import com.nhb.app.custom.viewmodel.interfaces.ITitleBarNormal;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-07-08 15:40
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class MemberCardDetailVM extends NHBViewModel implements ITitleBarNormal {
    public static final int REQUEST_QR_CODE = 1;
    public ObservableBoolean isEnable = new ObservableBoolean();

    public ObservableField<MemberCardBean> memberCard = new ObservableField<>();
    public ObservableField<View> userCardView = new ObservableField<>();

    public MemberCardDetailVM(boolean isEnable, MemberCardBean memberCard) {
        this.isEnable.set(isEnable);
        this.memberCard.set(memberCard);
    }

    @Override
    public void clickFinish(View view) {
        finishActivity();
    }

    @Override
    public ObservableField<String> getTitle() {
        return new ObservableField<>(ResourceUtil.getString(R.string.member_card));
    }

    public void clickUseCard(View view) {
        if (isEnable.get()) {
            if (TextUtils.equals(Constants.ITEM_SERVICE_TYPE.SERVICE_HOME, memberCard.get().itemType)) {
                startActivityForResult(new Intent(view.getContext(), PersonalAddressActivity.class).putExtra(Extras.WHERE_FROM, Extras.FROM_MEMBER_CARD).putExtra(Extras.ORDER_ID, memberCard.get().orderId).putExtra(Extras.ITEMS_SERVER_TYPE, memberCard.get().itemType), PersonalMemberCardActivity.REQUEST_CODE_SELECT_ADDRESS);
            } else {
                userCardView.set(view);
                getQRCodeStr(memberCard.get().orderId);
            }
        }
    }

    public void userMemberCard(String orderId, String addressId, String cardType, String consumeCode, String markedWords) {
        fetchRemoteData(FetchDataViewModel.CODE_INIT, RestClient.getService().useMemberCard(UserInfoUtils.getUserToken(), orderId, addressId, cardType, consumeCode, markedWords));
    }

    @Override
    public void handleData(int requestCode, Object data, NHBResponse response) {
        super.handleData(requestCode, data, response);
        switch (requestCode) {
            case FetchDataViewModel.CODE_INIT:
                finishActivity();
                ToastUtil.get().shortToast(ResourceUtil.getString(R.string.use_card_success));
                break;
            case REQUEST_QR_CODE:
                String qrCode = (String) data;
                new QRCodeDialog(userCardView.get().getContext(),memberCard.get().orderId,qrCode).show();
                break;
            default:
                break;
        }
    }

    /**
     * 生成二维码字符串
     *
     * @param orderId
     */
    public void getQRCodeStr(String orderId) {
        fetchRemoteData(REQUEST_QR_CODE, RestClient.getService().getQRCodeStr(UserInfoUtils.getUserToken(), orderId));
    }
}
