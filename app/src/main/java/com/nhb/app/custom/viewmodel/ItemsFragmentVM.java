package com.nhb.app.custom.viewmodel;

import android.content.Intent;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;

import com.nhb.app.custom.R;
import com.nhb.app.custom.base.NHBViewModel;
import com.nhb.app.custom.constant.Constants;
import com.nhb.app.custom.constant.Extras;
import com.nhb.app.custom.location.LocationServiceutils;
import com.nhb.app.custom.ui.home.MsgNoticeActivity;
import com.nhb.app.custom.ui.personal.PersonalSelectAreaActivity;
import com.nhb.app.custom.ui.search.CommonSearchActivity;
import com.nhb.app.custom.utils.ResourceUtil;
import com.nhb.app.custom.utils.SpCustom;
import com.nhb.app.custom.viewmodel.interfaces.ITitleBarSearch;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-27 18:42
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class ItemsFragmentVM extends NHBViewModel implements ITitleBarSearch {

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
