package com.nhb.app.custom.viewmodel;

import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.view.View;

import com.nhb.app.custom.R;
import com.nhb.app.custom.base.FetchDataViewModel;
import com.nhb.app.custom.bean.AreaBean;
import com.nhb.app.custom.bean.AreaCityBean;
import com.nhb.app.custom.bean.SelectAreaBean;
import com.nhb.app.custom.constant.Constants;
import com.nhb.app.custom.constant.Extras;
import com.nhb.app.custom.domain.NHBResponse;
import com.nhb.app.custom.domain.RestClient;
import com.nhb.app.custom.ui.personal.PersonalSwitchCityActivity;
import com.nhb.app.custom.utils.ResourceUtil;
import com.nhb.app.custom.utils.SpCustom;
import com.nhb.app.custom.viewmodel.interfaces.ITitleBarNormal;

import retrofit2.Call;

import static android.app.Activity.RESULT_OK;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-07-12 15:07
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class SelectAreaVM extends FetchDataViewModel implements ITitleBarNormal {
    public final static int REQUEST_CODE_POST_AREA = 1;
    public final static int REQUEST_CODE_GET_AREA_BY_CITY = 2;
    public ObservableField<String> selectAreaId = new ObservableField<>();
    public ObservableField<SelectAreaBean> selectAreaBean = new ObservableField<>();
    public ObservableField<AreaBean> selectArea = new ObservableField<>();
    public ObservableField<AreaCityBean> areaCityBean = new ObservableField<>();
    public ObservableBoolean loadAreaFailed = new ObservableBoolean();

    public ObservableBoolean postSuccess = new ObservableBoolean();

    public SelectAreaVM(String selectAreaId) {
        this.selectAreaId.set(selectAreaId);
    }

    @Override
    public Call loadApiService() {
        return RestClient.getService().getArea(selectAreaId.get());
    }

    @Override
    public void clickFinish(View view) {
        finishActivity();
    }

    @Override
    public ObservableField<String> getTitle() {
        return new ObservableField<>(ResourceUtil.getString(R.string.select_area));
    }

    public void postUserArea(AreaBean areaBean) {
        this.selectArea.set(areaBean);
        fetchRemoteData(REQUEST_CODE_POST_AREA, RestClient.getService().getHomeData(selectArea.get().name, selectArea.get().name));
    }

    public void getUserAreaByCity() {
        fetchRemoteData(REQUEST_CODE_GET_AREA_BY_CITY, RestClient.getService().getAreaByCity(selectAreaId.get()));
    }

    public void clickSwitch(View view) {
        startActivityForResult(new Intent(view.getContext(), PersonalSwitchCityActivity.class).putExtra(Extras.SELECT_CITY_ID, null != selectAreaBean.get() ? selectAreaBean.get().areaId : "").putExtra(Extras.SELECT_PROVINCE_ID, null != selectAreaBean.get() ? selectAreaBean.get().areaId : ""), Constants.REQUEST_CODE_SELECT_CITY);
    }

    @Override
    public void handleData(int requestCode, Object data, NHBResponse response) {
        super.handleData(requestCode, data, response);
        switch (requestCode) {
            case FetchDataViewModel.CODE_INIT:
                selectAreaBean.set((SelectAreaBean) data);
                break;
            case REQUEST_CODE_POST_AREA:
                SpCustom.get().write(Constants.LOCATION_AREA_ID, selectArea.get().id);
                SpCustom.get().write(Constants.LOCATION_AREA_NAME, selectArea.get().name);
                postSuccess.set(true);
                Intent intent = new Intent();
                intent.putExtra(Extras.SELCT_AREA_ID, selectArea.get().id);
                intent.putExtra(Extras.SELCT_AREA_NAME, selectArea.get().name);
                finishActivityForResult(RESULT_OK, intent);
                break;
            case REQUEST_CODE_GET_AREA_BY_CITY:
                AreaCityBean bean = (AreaCityBean) data;
                areaCityBean.set(bean);
                break;
            default:
                break;
        }
    }

    @Override
    public void handleFailedData(int requestCode, String errorMessage) {
        loadAreaFailed.set(true);
    }
}
