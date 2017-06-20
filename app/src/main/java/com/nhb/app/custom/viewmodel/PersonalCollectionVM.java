package com.nhb.app.custom.viewmodel;

import android.app.Activity;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import com.fast.library.ui.ToastUtil;
import com.nhb.app.custom.R;
import com.nhb.app.custom.base.FetchDataViewModel;
import com.nhb.app.custom.bean.ItemsItemBean;
import com.nhb.app.custom.common.dialog.CommonDialog;
import com.nhb.app.custom.constant.Constants;
import com.nhb.app.custom.domain.NHBResponse;
import com.nhb.app.custom.domain.RestClient;
import com.nhb.app.custom.recyclerview.NHBRecyclerVM;
import com.nhb.app.custom.recyclerview.RecyclerItemVM;
import com.nhb.app.custom.utils.ResourceUtil;
import com.nhb.app.custom.utils.UserInfoUtils;
import com.nhb.app.custom.utils.helper.RouteHelper;
import com.nhb.app.custom.viewmodel.interfaces.ITitleBarNormal;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-07-01 17:05
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class PersonalCollectionVM extends NHBRecyclerVM<ItemsItemBean> implements ITitleBarNormal {
    public static final int REQUEST_CODE_DELETE_COLLECTION = 1;

    @NonNull
    @Override
    public RecyclerItemVM<ItemsItemBean> getItemVM(int viewType) {
        return new ItemCollectionVM();
    }

    @Override
    public Call loadApiService() {
        return RestClient.getService().getCollection(UserInfoUtils.getUserToken(), String.valueOf(startNum));
    }

    @Override
    public void handleData(int requestCode, Object data, NHBResponse response) {
        super.handleData(requestCode, data, response);
        switch (requestCode) {
            case FetchDataViewModel.CODE_INIT:
                List<ItemsItemBean> itemsItemBeanList = (List<ItemsItemBean>) data;
                addAll(itemsItemBeanList);
                break;
            case REQUEST_CODE_DELETE_COLLECTION:
                ToastUtil.get().shortToast(ResourceUtil.getString(R.string.delete_success));
                refreshData();
                break;
            default:
                break;
        }
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
    public void clickFinish(View view) {
        finishActivity();
    }

    @Override
    public ObservableField<String> getTitle() {
        return new ObservableField<>(ResourceUtil.getString(R.string.personal_collection));
    }

    @Override
    public void onItemClick(WeakReference<View> viewRef, ItemsItemBean bean, int position) {
        super.onItemClick(viewRef, bean, position);
        RouteHelper.getInstance().startItemDetail((Activity) viewRef.get().getContext(), bean.itemId);
    }

    @Override
    public void onItemLongClick(WeakReference<View> viewRef, ItemsItemBean bean, int position) {
        super.onItemLongClick(viewRef, bean, position);
        final int pos = position;
        new CommonDialog(viewRef.get().getContext(), ResourceUtil.getString(R.string.operate_confirm), ResourceUtil.getString(R.string.confirm_delete), ResourceUtil.getString(R.string.cancel), ResourceUtil.getString(R.string.confirm), new CommonDialog.OnActionListener() {
            @Override
            public void clickConfirm() {
                deleteCollection(pos);
            }
        }).show();
    }

    /**
     * 删除选中收藏
     */
    public void deleteCollection(int position) {
        if (position < beans.size()) {
            fetchRemoteData(REQUEST_CODE_DELETE_COLLECTION, RestClient.getService().operateCollection(UserInfoUtils.getUserToken(), beans.get(position).itemId, Constants.OPERATE_COLLECTION_DELETE), false);
        }
    }
}
