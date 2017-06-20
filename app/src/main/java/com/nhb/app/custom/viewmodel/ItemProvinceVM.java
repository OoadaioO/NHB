package com.nhb.app.custom.viewmodel;

import android.databinding.ObservableInt;

import com.nhb.app.custom.R;
import com.nhb.app.custom.bean.ProvinceBean;
import com.nhb.app.custom.recyclerview.RecyclerItemVM;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-27 15:28
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class ItemProvinceVM extends RecyclerItemVM<ProvinceBean> {
    public ObservableInt selectedProvince = new ObservableInt();

    public ItemProvinceVM(ObservableInt selectedProvince) {
        this.selectedProvince = selectedProvince;
    }

    @Override
    public int loadItemView() {
        return R.layout.item_province;
    }
}
