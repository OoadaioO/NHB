package com.nhb.app.custom.viewmodel;

import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.view.View;

import com.nhb.app.custom.bean.MemberCardBean;
import com.nhb.app.custom.constant.Constants;
import com.nhb.app.custom.constant.Extras;
import com.nhb.app.custom.domain.NHBResponse;
import com.nhb.app.custom.domain.RestClient;
import com.nhb.app.custom.recyclerview.NHBRecyclerVM;
import com.nhb.app.custom.recyclerview.RecyclerItemVM;
import com.nhb.app.custom.ui.personal.MemberCardDetailActivity;
import com.nhb.app.custom.utils.UserInfoUtils;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-07-01 18:54
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class MemberCardListVM extends NHBRecyclerVM<MemberCardBean> {

    private ObservableBoolean isEnable = new ObservableBoolean();

    public MemberCardListVM(boolean isEnable) {
        this.isEnable.set(isEnable);
    }

    @NonNull
    @Override
    public RecyclerItemVM<MemberCardBean> getItemVM(int viewType) {
        return new ItemMemberCardVM(isEnable.get());
    }

    @Override
    public Call loadApiService() {
        String cardType = isEnable.get() ? Constants.MEMBER_CARD_ENABLE : Constants.MEMBER_CARD_DISABLE;
        return RestClient.getService().getPersonalCard(UserInfoUtils.getUserToken(), cardType);
    }

    @Override
    public void handleData(int requestCode, Object data, NHBResponse response) {
        super.handleData(requestCode, data, response);
        addAll((List<MemberCardBean>) data);
    }

    @Override
    public boolean enablePullToRefresh() {
        return true;
    }

    @Override
    public void onItemClick(WeakReference<View> viewRef, MemberCardBean bean, int position) {
        super.onItemClick(viewRef, bean, position);
        startActivity(new Intent(viewRef.get().getContext(), MemberCardDetailActivity.class).putExtra(Extras.MEMBER_CARD_ISENABLE, isEnable.get()).putExtra(Extras.MEMBER_CARD_BEAN, bean));
    }
}
