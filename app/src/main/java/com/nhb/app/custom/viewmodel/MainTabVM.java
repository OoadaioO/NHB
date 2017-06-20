package com.nhb.app.custom.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.view.View;

import com.nhb.app.custom.base.NHBViewModel;
import com.nhb.app.custom.bean.RemindBean;
import com.nhb.app.custom.domain.NHBResponse;
import com.nhb.app.custom.ui.launch.MainActivity;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-25 16:23
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class MainTabVM extends NHBViewModel {

    private final int REQUEST_CODE_GET_BADGES = 1;
    public final ObservableField<String> selectedTab = new ObservableField<>();
    //未读消息小红点显示
    public ObservableBoolean ivMsgBadge = new ObservableBoolean();

    /**
     * 获取未读消息
     */
    public void getBadges() {
//        fetchRemoteData(REQUEST_CODE_GET_BADGES, RestClient.getService().getBadge(), true);
    }

    @Override
    public void handleData(int requestCode, Object data, NHBResponse response) {
        super.handleData(requestCode, data, response);
        switch (requestCode) {
            case REQUEST_CODE_GET_BADGES:
                RemindBean remindBean = (RemindBean) data;
                badges.set(remindBean);
                ivMsgBadge.set(badges.get().getReply_msg_num() > 0 || badges.get().getPrivate_num() > 0);
                break;
        }
    }

    public void clickHome(View view) {
        selectedTab.set(MainActivity.HOME);
        getBadges();
    }

    public void clickItems(View view) {
        selectedTab.set(MainActivity.ITEMS);
        getBadges();
    }

    public void clickPersonal(View view) {
        selectedTab.set(MainActivity.PERSONAL);
        getBadges();
    }

    public void clickMore(View view) {
        selectedTab.set(MainActivity.MORE);
        getBadges();
    }
}
