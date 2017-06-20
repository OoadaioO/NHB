package com.nhb.app.custom.viewmodel;

import android.content.Intent;
import android.view.View;

import com.nhb.app.custom.base.NHBViewModel;
import com.nhb.app.custom.constant.Extras;
import com.nhb.app.custom.ui.search.CommonSearchActivity;
import com.nhb.app.custom.viewmodel.interfaces.ITitleBarSearchSimple;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-07-29 23:45
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class ItemsListCategoryVM extends NHBViewModel implements ITitleBarSearchSimple {
    @Override
    public void clickSearch(View view) {
        Intent intent = new Intent(view.getContext(), CommonSearchActivity.class);
        intent.putExtra(Extras.FROM, Extras.FROM_HOME);
        startActivity(intent);
    }

    @Override
    public void clickFinish(View view) {
        finishActivity();
    }
}
