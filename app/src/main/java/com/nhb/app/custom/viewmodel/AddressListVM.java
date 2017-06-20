package com.nhb.app.custom.viewmodel;

import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import com.fast.library.ui.ToastUtil;
import com.nhb.app.custom.R;
import com.nhb.app.custom.base.FetchDataViewModel;
import com.nhb.app.custom.bean.AddressBean;
import com.nhb.app.custom.common.dialog.CommonDialog;
import com.nhb.app.custom.constant.Extras;
import com.nhb.app.custom.domain.NHBResponse;
import com.nhb.app.custom.domain.RestClient;
import com.nhb.app.custom.recyclerview.NHBRecyclerVM;
import com.nhb.app.custom.recyclerview.RecyclerItemVM;
import com.nhb.app.custom.ui.personal.AddressOperateActivity;
import com.nhb.app.custom.utils.ResourceUtil;
import com.nhb.app.custom.utils.UserInfoUtils;
import com.nhb.app.custom.viewmodel.interfaces.ITitleBarRightImage;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;

import static android.app.Activity.RESULT_OK;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-30 17:20
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class AddressListVM extends NHBRecyclerVM<AddressBean> implements ITitleBarRightImage {
    private static final int REQUEST_CODE_DELETE_ADDRESS = 1;
    private ObservableBoolean isSelectAddress = new ObservableBoolean();
    private String orderId;
    private String itemsType;
    private ObservableField<String> defaultAddress = new ObservableField<>();

    public AddressListVM(boolean isSelectAddress, String orderId, String itemsType, String defaultAddress) {
        this.isSelectAddress.set(isSelectAddress);
        this.orderId = orderId;
        this.itemsType = itemsType;
        this.defaultAddress.set(defaultAddress);
    }

    @NonNull
    @Override
    public RecyclerItemVM<AddressBean> getItemVM(int viewType) {
        return new ItemPersonalAddressVM(defaultAddress);
    }

    @Override
    public Call loadApiService() {
        return RestClient.getService().getAddressData(UserInfoUtils.getUserToken());
    }

    @Override
    public ObservableField<String> getTitle() {
        return new ObservableField<>(ResourceUtil.getString(R.string.personal_address));
    }

    @Override
    public void clickFinish(View view) {
        finishActivity();
    }

    @Override
    public int getRightBtnSrc() {
        return R.drawable.ic_add;
    }

    @Override
    public void clickRightImg(View view) {
        startActivity(new Intent(view.getContext(), AddressOperateActivity.class));
    }

    @Override
    public void handleData(int requestCode, Object data, NHBResponse response) {
        switch (requestCode) {
            case FetchDataViewModel.CODE_INIT:
                List<AddressBean> addressBeanList = (List<AddressBean>) data;
                addAll(addressBeanList);
                break;
            case REQUEST_CODE_DELETE_ADDRESS:
                ToastUtil.get().shortToast(ResourceUtil.getString(R.string.delete_success));
                refreshData();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(WeakReference<View> viewRef, final AddressBean bean, int position) {
        super.onItemClick(viewRef, bean, position);
        final String addressId = bean.addressId;
        if (isSelectAddress.get()) {
            new CommonDialog(viewRef.get().getContext(), ResourceUtil.getString(R.string.operate_confirm), ResourceUtil.getString(R.string.sure_select_address), ResourceUtil.getString(R.string.cancel), ResourceUtil.getString(R.string.confirm), new CommonDialog.OnActionListener() {
                @Override
                public void clickConfirm() {
                    Intent intent = new Intent();
                    intent.putExtra(Extras.SELECT_ADDRESS_ID, addressId);
                    intent.putExtra(Extras.ORDER_ID, orderId);
                    intent.putExtra(Extras.ITEMS_SERVER_TYPE, itemsType);
                    finishActivityForResult(RESULT_OK, intent);
                }
            }).show();
        }
    }
}
