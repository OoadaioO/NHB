package com.nhb.app.custom.viewmodel;

import android.databinding.ObservableField;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;

import com.nhb.app.custom.R;
import com.nhb.app.custom.bean.ItemsItemBean;
import com.nhb.app.custom.recyclerview.RecyclerItemVM;
import com.nhb.app.custom.utils.ResourceUtil;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-26 09:17
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class ItemItemsVM extends RecyclerItemVM<ItemsItemBean> {

    @Override
    public int loadItemView() {
        return R.layout.item_items;
    }

    public ObservableField<SpannableString> getPriceInStore() {
        String priceInStore = ResourceUtil.getString(R.string.pay_price, TextUtils.isEmpty(bean.priceInStore) ? "" : bean.priceInStore);
        SpannableString sp = new SpannableString(priceInStore);
        sp.setSpan(new StrikethroughSpan(), 0, priceInStore.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return new ObservableField<>(sp);
    }
}
