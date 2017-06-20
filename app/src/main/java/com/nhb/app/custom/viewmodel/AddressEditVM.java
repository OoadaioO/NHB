package com.nhb.app.custom.viewmodel;

import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.fast.library.ui.ToastUtil;
import com.nhb.app.custom.R;
import com.nhb.app.custom.base.NHBViewModel;
import com.nhb.app.custom.bean.AddressBean;
import com.nhb.app.custom.domain.NHBResponse;
import com.nhb.app.custom.domain.RestClient;
import com.nhb.app.custom.utils.ResourceUtil;
import com.nhb.app.custom.utils.UserInfoUtils;
import com.nhb.app.custom.viewmodel.interfaces.ITitleBarRightText;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-30 18:56
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class AddressEditVM extends NHBViewModel implements ITitleBarRightText {
    private static final int REQUEST_CODE_EDIT_ADDRESS = 1;
    public ObservableField<AddressBean> addressBean = new ObservableField<>();

    public ObservableField<String> contactName = new ObservableField<>();
    public ObservableField<String> gender = new ObservableField<>();
    public ObservableField<String> phone = new ObservableField<>();
    public ObservableField<String> address = new ObservableField<>();
    public ObservableField<String> province = new ObservableField<>();
    public ObservableField<String> addressId = new ObservableField<>();

    public AddressEditVM(AddressBean addressBean) {
        this.addressBean.set(addressBean);
        if (addressBean != null) {
            contactName.set(addressBean.realName);
            gender.set(addressBean.gender);
            phone.set(addressBean.phone);
            addressId.set(addressBean.addressId);
            address.set("");
            this.addressBean.get().address = "";
        }
    }

    @Override
    public ObservableField<String> getTitle() {
        return null == addressBean.get() ? new ObservableField<>(ResourceUtil.getString(R.string.add_address)) : new ObservableField<>(ResourceUtil.getString(R.string.edit_address));
    }

    @Override
    public void clickFinish(View view) {
        finishActivity();
    }

    @Override
    public ObservableField<String> getRightText() {
        return new ObservableField<>(ResourceUtil.getString(R.string.finish));
    }

    /**
     * 联系人改变
     *
     * @param editable
     */
    public void contactNameChange(Editable editable) {
        contactName.set(editable.toString().trim());
    }

    /**
     * 性别改变
     *
     * @param group
     * @param checkedId
     */
    public void sexChange(RadioGroup group, int checkedId) {
        gender.set(TextUtils.equals(((RadioButton) group.findViewById(checkedId)).getText().toString().trim(), ResourceUtil.getString(R.string.address_woman)) ? ResourceUtil.getString(R.string.sex_woman) : ResourceUtil.getString(R.string.sex_man));
    }

    /**
     * 联系方式改变
     *
     * @param editable
     */
    public void phoneNameChange(Editable editable) {
        phone.set(editable.toString().trim());
    }

    /**
     * 地址省市区改变
     *
     * @param editable
     */
    public void provinceNameChange(Editable editable) {
        province.set(editable.toString().trim());
    }


    /**
     * 地址改变
     *
     * @param editable
     */
    public void addressNameChange(Editable editable) {
        address.set(editable.toString().trim());
    }

    @Override
    public void clickRightTv(View view) {
        String addr = province.get() + address.get();
        if (!TextUtils.isEmpty(addr)) {
            String address_id = null == addressBean.get() ? "" : addressId.get();
            fetchRemoteData(REQUEST_CODE_EDIT_ADDRESS, RestClient.getService().addAddress(UserInfoUtils.getUserToken(), address_id, contactName.get(), gender.get(), phone.get(), addr, ""));
        } else {
            ToastUtil.get().shortToast(R.string.address_is_not_empty);
        }
    }

    @Override
    public void handleData(int requestCode, Object data, NHBResponse response) {
        switch (requestCode) {
            case REQUEST_CODE_EDIT_ADDRESS:
                ToastUtil.get().shortToast(R.string.operate_success);
                finishActivity();
                break;
            default:
                break;
        }
    }
}
