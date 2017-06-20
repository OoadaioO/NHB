package com.nhb.app.custom.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nhb.app.custom.base.BaseApplication;
import com.nhb.app.custom.base.NHBViewModel;
import com.nhb.app.custom.bean.CommonEntranceBean;
import com.nhb.app.custom.common.bean.TemplateSubItem;
import com.nhb.app.custom.recyclerview.HFGridLayoutManager;
import com.nhb.app.custom.recyclerview.ItemVMFactory;
import com.nhb.app.custom.recyclerview.RecyclerItemVM;

import java.util.List;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-26 00:11
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class HomeHeaderVM extends NHBViewModel {
    public ObservableField<List<TemplateSubItem>> templateSubItems = new ObservableField<>();
    public ObservableInt clickItem = new ObservableInt();
    public ObservableField<CommonEntranceBean> clickCommonEntranceItem = new ObservableField<>();

    public ObservableArrayList<CommonEntranceBean> commonEntranceBeenList = new ObservableArrayList<>();

    public ObservableBoolean isClickCommonEntrance = new ObservableBoolean();
    public ObservableBoolean isClickItem = new ObservableBoolean();

    public ItemVMFactory commonEntranceFactory = new ItemVMFactory() {
        @NonNull
        @Override
        public RecyclerItemVM getItemVM(int viewType) {
            return new ItemHomeButtonsVM();
        }
    };

    public void clickCommonEntrance(View view, int position) {
        clickCommonEntranceItem.set(commonEntranceBeenList.get(position));
        isClickCommonEntrance.notifyChange();
    }

    public RecyclerView.LayoutManager layoutManager = new HFGridLayoutManager(BaseApplication.appContext, 5);

    public void cliclTemplate(View view) {
        clickItem.set(view.getId());
        isClickItem.notifyChange();
    }
}
