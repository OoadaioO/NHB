package com.nhb.app.custom.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import com.nhb.app.custom.base.NHBViewModel;
import com.nhb.app.custom.recyclerview.ItemVMFactory;
import com.nhb.app.custom.recyclerview.RecyclerItemVM;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-02 18:44
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class CommonSearchVM extends NHBViewModel {
    //清除搜索历史记录的显示
    public ObservableBoolean clearHistoryShow = new ObservableBoolean();
    //是否item点击
    public ObservableField<String> isItemClick = new ObservableField<>();

    public ObservableArrayList<String> searchHistoryList = new ObservableArrayList<>();

    public ItemVMFactory historyItemVMFactory = new ItemVMFactory() {
        @NonNull
        @Override
        public RecyclerItemVM getItemVM(int viewType) {
            return new ItemCommonSearchHistoryVM();
        }
    };

    public void clickHistoryItem(View view, int position) {
        // 清空搜索框，隐藏搜索结果，隐藏输入法键盘
        isItemClick.set(searchHistoryList.get(position));
    }

    public void clickClearHistory(View view) {
        clearHistoryShow.set(false);
    }
}
