package com.nhb.app.custom.viewmodel;

import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.TextUtils;
import android.view.View;

import com.nhb.app.custom.R;
import com.nhb.app.custom.base.FetchDataViewModel;
import com.nhb.app.custom.bean.MemberCardBean;
import com.nhb.app.custom.common.dialog.QRCodeDialog;
import com.nhb.app.custom.constant.Constants;
import com.nhb.app.custom.constant.Extras;
import com.nhb.app.custom.domain.NHBResponse;
import com.nhb.app.custom.domain.RestClient;
import com.nhb.app.custom.recyclerview.RecyclerItemVM;
import com.nhb.app.custom.ui.personal.PersonalAddressActivity;
import com.nhb.app.custom.ui.personal.PersonalMemberCardActivity;
import com.nhb.app.custom.utils.UserInfoUtils;


/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-07-01 17:55
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class ItemMemberCardVM extends RecyclerItemVM<MemberCardBean> {
    public static final int[] ITEM_BG_COLOR = new int[]{R.drawable.bg_member_card_1, R.drawable.bg_member_card_2, R.drawable.bg_member_card_3, R.drawable.bg_member_card_4, R.drawable.bg_member_card_5};
    public ObservableInt unEnableBgColor = new ObservableInt(R.drawable.bg_member_card);
    public ObservableBoolean isEnable = new ObservableBoolean();
    public ObservableField<View> userCardView = new ObservableField<>();

    public ItemMemberCardVM(boolean isEnable) {
        this.isEnable.set(isEnable);
    }

    @Override
    public int loadItemView() {
        return R.layout.item_member_card;
    }

    public void clickUseCard(View view) {
        if (isEnable.get()) {
            if (TextUtils.equals(Constants.ITEM_SERVICE_TYPE.SERVICE_HOME, item().itemType)) {
                startActivityForResult(new Intent(view.getContext(), PersonalAddressActivity.class).putExtra(Extras.WHERE_FROM, Extras.FROM_MEMBER_CARD).putExtra(Extras.ORDER_ID, item().orderId).putExtra(Extras.ITEMS_SERVER_TYPE, item().itemType), PersonalMemberCardActivity.REQUEST_CODE_SELECT_ADDRESS);
            } else {
                userCardView.set(view);
                getQRCodeStr(item().orderId);
            }
        }
    }

    public ObservableInt getItemBg() {
        return new ObservableInt(ITEM_BG_COLOR[position % ITEM_BG_COLOR.length]);
    }

    @Override
    public void handleData(int requestCode, Object data, NHBResponse response) {
        String qrCode = (String) data;
        new QRCodeDialog(userCardView.get().getContext(),item().orderId,qrCode).show();
    }

    /**
     * 生成二维码字符串
     *
     * @param orderId
     */
    public void getQRCodeStr(String orderId) {
        fetchRemoteData(FetchDataViewModel.CODE_INIT, RestClient.getService().getQRCodeStr(UserInfoUtils.getUserToken(), orderId));
    }
}
