package com.nhb.app.custom.viewmodel;

import android.app.Activity;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nhb.app.custom.R;
import com.nhb.app.custom.bean.ItemsItemBean;
import com.nhb.app.custom.constant.Constants;
import com.nhb.app.custom.domain.NHBResponse;
import com.nhb.app.custom.domain.RestClient;
import com.nhb.app.custom.recyclerview.DividerItemDecoration;
import com.nhb.app.custom.recyclerview.NHBRecyclerVM;
import com.nhb.app.custom.recyclerview.RecyclerItemVM;
import com.nhb.app.custom.utils.SpCustom;
import com.nhb.app.custom.utils.helper.RouteHelper;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;

import static com.nhb.app.custom.constant.Constants.LOCATION_AREA_ID;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-06 16:02
 * Version:1.0.0
 * Description:xx
 * ***********************************************************************
 */
public class ItemsSearchResultListVM extends NHBRecyclerVM<ItemsItemBean> {

    public ObservableField<String> searchContent = new ObservableField<>();

    public void setSearchContent(String searchContent) {
        this.searchContent.set(searchContent);
    }

    @Override
    public Call loadApiService() {
        return RestClient.getService().toGetSearchTopicList(SpCustom.get().readString(LOCATION_AREA_ID, Constants.DEFAULT_SELECT_AREA), searchContent.get().trim(), Constants.SEARCH_ITEMS, "", "", String.valueOf(startNum));
    }

    @Override
    public void handleData(int requestCode, Object data, NHBResponse response) {
        super.handleData(requestCode, data, response);
        addAll((List<ItemsItemBean>) data);
    }

    @Override
    public void onItemClick(WeakReference<View> viewRef, ItemsItemBean bean, int position) {
        super.onItemClick(viewRef, bean, position);
        RouteHelper.getInstance().startItemDetail((Activity) viewRef.get().getContext(), bean.itemId);
    }

    @NonNull
    @Override
    public RecyclerItemVM<ItemsItemBean> getItemVM(int viewType) {
        return new ItemItemsVM();
    }

    @Override
    public RecyclerView.ItemDecoration getItemDecoration(WeakReference<RecyclerView> recyclerViewRef) {
        DividerItemDecoration divider = (DividerItemDecoration) super.getItemDecoration(recyclerViewRef);
        divider.setDivider(recyclerViewRef.get().getContext(), R.drawable.list_divider_height01);
        return divider;
    }

    @Override
    public int loadMoreView() {
        return R.layout.load_more;
    }

    @Override
    public boolean enablePullToRefresh() {
        return true;
    }
}
