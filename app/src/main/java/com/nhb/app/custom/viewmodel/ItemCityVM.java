package com.nhb.app.custom.viewmodel;

import com.nhb.app.custom.R;
import com.nhb.app.custom.bean.CityBean;
import com.nhb.app.custom.recyclerview.RecyclerItemVM;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-27 15:30
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class ItemCityVM extends RecyclerItemVM<CityBean> {

    @Override
    public int loadItemView() {
        return R.layout.item_city;
    }
}
