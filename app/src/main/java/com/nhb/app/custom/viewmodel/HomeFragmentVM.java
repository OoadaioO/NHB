package com.nhb.app.custom.viewmodel;

import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;

import com.nhb.app.custom.R;
import com.nhb.app.custom.base.FetchDataViewModel;
import com.nhb.app.custom.bean.HomeDataBean;
import com.nhb.app.custom.constant.Constants;
import com.nhb.app.custom.constant.Extras;
import com.nhb.app.custom.domain.NHBResponse;
import com.nhb.app.custom.domain.RestClient;
import com.nhb.app.custom.location.LocationServiceutils;
import com.nhb.app.custom.ui.home.MsgNoticeActivity;
import com.nhb.app.custom.ui.personal.PersonalSelectAreaActivity;
import com.nhb.app.custom.ui.search.CommonSearchActivity;
import com.nhb.app.custom.utils.ResourceUtil;
import com.nhb.app.custom.utils.SpCustom;
import com.nhb.app.custom.viewmodel.interfaces.ITitleBarSearch;

import retrofit2.Call;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-25 17:37
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class HomeFragmentVM extends FetchDataViewModel implements ITitleBarSearch {
    // 用于通知数据请求下来了
    public ObservableBoolean dataObservable = new ObservableBoolean();

    public ObservableField<HomeDataBean> homeBean = new ObservableField<>();

    public HomeFragmentVM() {

    }

    @Override
    public Call loadApiService() {
        String areaName = TextUtils.isEmpty(SpCustom.get().readString(Constants.LOCATION_AREA_NAME)) ? LocationServiceutils.getInstance().locationName : SpCustom.get().readString(Constants.LOCATION_AREA_NAME);
        return RestClient.getService().getHomeData(areaName, areaName);
    }

    @Override
    public void handleData(int requestCode, Object data, NHBResponse response) {
        super.handleData(requestCode, data, response);
        try {
            homeBean.set((HomeDataBean) data);
            SpCustom.get().write(Constants.LOCATION_AREA_ID, homeBean.get().areaId);
            SpCustom.get().write(Constants.LOCATION_AREA_NAME, homeBean.get().areaName);
            dataObservable.notifyChange();
        } catch (Exception e) {
            dataObservable.notifyChange();
        }
    }

    @Override
    public void handleFailedData(int requestCode, String errorMessage) {
        dataObservable.notifyChange();
    }

    @Override
    public void clickSearch(View view) {
        Intent intent = new Intent(view.getContext(), CommonSearchActivity.class);
        intent.putExtra(Extras.FROM, Extras.FROM_HOME);
        startActivity(intent);
    }

    @Override
    public void clickMsg(View view) {
        startActivity(new Intent(view.getContext(), MsgNoticeActivity.class));
    }

    @Override
    public void clickSelectCity(View view) {
        startActivity(new Intent(view.getContext(), PersonalSelectAreaActivity.class).putExtra(Extras.SELCT_AREA_ID, SpCustom.get().readString(Constants.LOCATION_AREA_ID)));
    }

    @Override
    public ObservableField<String> getLocationArea() {
        String locationName = TextUtils.isEmpty(SpCustom.get().readString(Constants.LOCATION_AREA_NAME)) ? LocationServiceutils.getInstance().locationName : SpCustom.get().readString(Constants.LOCATION_AREA_NAME);
        return new ObservableField<>(locationName);
    }

    @Override
    public ObservableField<String> getSearchContent() {
        return new ObservableField<>(ResourceUtil.getString(R.string.common_search_hint));
    }
}
