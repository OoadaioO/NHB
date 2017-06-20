package com.nhb.app.custom.viewmodel;

import android.databinding.ObservableField;

import com.nhb.app.custom.R;
import com.nhb.app.custom.base.NHBViewModel;
import com.nhb.app.custom.bean.ShopCategoryGroupBean;
import com.nhb.app.custom.domain.NHBResponse;
import com.nhb.app.custom.domain.RestClient;
import com.nhb.app.custom.utils.ResourceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-27 15:17
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class CommonFilterVM extends NHBViewModel {
    public static final int REQUEST_CODE_GET_FILTER_ITEM = 0;

    public ObservableField<List<ShopCategoryGroupBean>> itemsData = new ObservableField<>();

    public void toGetData() {
        fetchRemoteData(REQUEST_CODE_GET_FILTER_ITEM, RestClient.getService().getFilterItems(), false);
    }

    @Override
    public void handleData(int requestCode, Object data, NHBResponse response) {
        switch (requestCode) {
            case REQUEST_CODE_GET_FILTER_ITEM:
                ShopCategoryGroupBean shopCategoryGroupBean = new ShopCategoryGroupBean();
                shopCategoryGroupBean.shopCategoryId = "";
                shopCategoryGroupBean.shopCategoryName = ResourceUtil.getString(R.string.filter_tab_items_default);
                List<ShopCategoryGroupBean> datas = new ArrayList<>();
                datas.add(shopCategoryGroupBean);
                datas.addAll((List<ShopCategoryGroupBean>) data);
                itemsData.set(datas);
                break;
            default:
                break;
        }
    }
}
