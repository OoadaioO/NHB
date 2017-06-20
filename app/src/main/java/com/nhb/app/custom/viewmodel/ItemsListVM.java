package com.nhb.app.custom.viewmodel;

import android.app.Activity;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.nhb.app.custom.R;
import com.nhb.app.custom.bean.ItemsItemBean;
import com.nhb.app.custom.common.view.CommonFilter;
import com.nhb.app.custom.constant.Constants;
import com.nhb.app.custom.domain.NHBResponse;
import com.nhb.app.custom.domain.RestClient;
import com.nhb.app.custom.recyclerview.DividerGridItemDecoration;
import com.nhb.app.custom.recyclerview.DividerItemDecoration;
import com.nhb.app.custom.recyclerview.HFGridLayoutManager;
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
 * CreateData:2016-06-26 09:25
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class ItemsListVM extends NHBRecyclerVM<ItemsItemBean> {

    private ObservableField<String> itemsId = new ObservableField<>();
    private ObservableField<String> tagId = new ObservableField<>();
    private ObservableField<String> orderId = new ObservableField<>();
    private ObservableField<String> searchType=new ObservableField<>();

    public ItemsListVM(String itemsId, String tagId, String orderId) {
        this.itemsId.set(itemsId);
        this.tagId.set(tagId);
        this.orderId.set(orderId);
        this.searchType.set(Constants.SELECT_ITEMS);
    }

    public ItemsListVM(String itemsId) {
        this.itemsId.set(itemsId);
        this.orderId.set(CommonFilter.ORDER_LIST);
        this.searchType.set(Constants.SELECT_ITEMS_ONE);
    }

    @NonNull
    @Override
    public RecyclerItemVM<ItemsItemBean> getItemVM(int viewType) {
        return TextUtils.equals(CommonFilter.ORDER_LIST, orderId.get()) ? new ItemItemsVM() : new ItemItemsGridVM();
    }

    @Override
    public Call loadApiService() {
        return RestClient.getService().toGetSearchTopicList(SpCustom.get().readString(LOCATION_AREA_ID, Constants.DEFAULT_SELECT_AREA), "", searchType.get(), itemsId.get(), tagId.get(), String.valueOf(startNum));
    }

    @Override
    public void onItemClick(WeakReference<View> viewRef, ItemsItemBean bean, int position) {
        super.onItemClick(viewRef, bean, position);
        RouteHelper.getInstance().startItemDetail((Activity) viewRef.get().getContext(),bean.itemId);
    }

    @Override
    public int loadMoreView() {
        return R.layout.load_more;
    }

    @Override
    public boolean enablePullToRefresh() {
        return true;
    }

    @Override
    public void handleData(int requestCode, Object data, NHBResponse response) {
        super.handleData(requestCode, data, response);
        addAll((List<ItemsItemBean>) data);
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager(WeakReference<RecyclerView> recyclerViewRef) {
        return TextUtils.equals(CommonFilter.ORDER_GRID, orderId.get()) ? new HFGridLayoutManager(recyclerViewRef.get().getContext(), 2) : super.getLayoutManager(recyclerViewRef);
    }

    @Override
    public RecyclerView.ItemDecoration getItemDecoration(WeakReference<RecyclerView> recyclerViewRef) {
        DividerItemDecoration divider = (DividerItemDecoration) super.getItemDecoration(recyclerViewRef);
        divider.setDivider(recyclerViewRef.get().getContext(), R.drawable.list_divider_height01);

        DividerGridItemDecoration decoration = new DividerGridItemDecoration(recyclerViewRef.get().getContext());
        decoration.setDivider(recyclerViewRef.get().getContext(), R.drawable.list_divider_height15);
        return TextUtils.equals(CommonFilter.ORDER_GRID, orderId.get()) ? decoration : divider;
    }
}
