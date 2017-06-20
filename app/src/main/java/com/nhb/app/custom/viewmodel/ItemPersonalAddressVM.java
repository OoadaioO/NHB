package com.nhb.app.custom.viewmodel;

import android.content.Intent;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.TextUtils;
import android.view.View;

import com.fast.library.ui.ToastUtil;
import com.nhb.app.custom.R;
import com.nhb.app.custom.bean.AddressBean;
import com.nhb.app.custom.common.dialog.CommonDialog;
import com.nhb.app.custom.constant.Extras;
import com.nhb.app.custom.domain.NHBResponse;
import com.nhb.app.custom.domain.RestClient;
import com.nhb.app.custom.recyclerview.RecyclerItemVM;
import com.nhb.app.custom.ui.personal.AddressOperateActivity;
import com.nhb.app.custom.utils.ResourceUtil;
import com.nhb.app.custom.utils.UserInfoUtils;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-30 16:42
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class ItemPersonalAddressVM extends RecyclerItemVM<AddressBean> {
    public static final int REQUEST_CODE_SET_DEFUALT_ADDRESS = 1;
    public static final int REQUEST_CODE_DELETE_ADDRESS = 3;
    public ObservableField<String> defaultAddress = new ObservableField<>();

    public ObservableInt defaultIcon = new ObservableInt(R.drawable.ic_radiobtn_unselect);

    public ItemPersonalAddressVM(ObservableField<String> defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    @Override
    public int loadItemView() {
        return R.layout.item_personal_address;
    }

    public void setDefaultAddress(View view) {
        fetchRemoteData(REQUEST_CODE_SET_DEFUALT_ADDRESS, RestClient.getService().setDefaultAddress(UserInfoUtils.getUserToken(), bean.addressId));
    }

    public void editAddress(View view) {
        startActivity(new Intent(view.getContext(), AddressOperateActivity.class).putExtra(Extras.ADDRESS_DATA, bean));
    }

    public void deleteAddress(View view) {
        new CommonDialog(view.getContext(), ResourceUtil.getString(R.string.operate_confirm), ResourceUtil.getString(R.string.confirm_delete), ResourceUtil.getString(R.string.cancel), ResourceUtil.getString(R.string.confirm), new CommonDialog.OnActionListener() {
            @Override
            public void clickConfirm() {
                if (position < beans.size()) {
                    fetchRemoteData(REQUEST_CODE_DELETE_ADDRESS, RestClient.getService().deleteAddress(UserInfoUtils.getUserToken(), beans.get(position).addressId), false);
                }
            }
        }).show();
    }

    @Override
    public void handleData(int requestCode, Object data, NHBResponse response) {
        switch (requestCode) {
            case REQUEST_CODE_SET_DEFUALT_ADDRESS:
                defaultAddress.set(bean.addressId);
                ToastUtil.get().shortToast(ResourceUtil.getString(R.string.collection_success));
                break;
            case REQUEST_CODE_DELETE_ADDRESS:
                ToastUtil.get().shortToast(ResourceUtil.getString(R.string.delete_success));
                beans.remove(position);
                break;
            default:
                break;
        }
    }

    public ObservableInt getDefaultAddressIcon() {
        defaultIcon.set(TextUtils.equals(defaultAddress.get(), bean.addressId) ? R.drawable.ic_radiobtn_select : R.drawable.ic_radiobtn_unselect);
        return defaultIcon;
    }
}
