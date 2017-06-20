package com.nhb.app.custom.ui.personal;

import android.content.Intent;
import android.text.TextUtils;

import com.fast.library.utils.DateUtils;
import com.fast.library.utils.MD5;
import com.fast.library.utils.StringUtils;
import com.nhb.app.custom.R;
import com.nhb.app.custom.base.BaseActivity;
import com.nhb.app.custom.bean.MemberCardBean;
import com.nhb.app.custom.common.dialog.EditInfoDialog;
import com.nhb.app.custom.constant.Extras;
import com.nhb.app.custom.databinding.ActivityMemberCardDetailBinding;
import com.nhb.app.custom.utils.UserInfoUtils;
import com.nhb.app.custom.viewmodel.MemberCardDetailVM;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-07-08 16:40
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class MemberCardDetailActivity extends BaseActivity<MemberCardDetailVM, ActivityMemberCardDetailBinding> implements EditInfoDialog.OnActionListener {
    private boolean mIsEnable;
    private MemberCardBean mMemberCardBean;

    private String mSelectAddressId;
    private String mOrderId;
    private String mItemsType;

    @Override
    protected void intentWithNormal(Intent intent) {
        super.intentWithNormal(intent);
        mIsEnable = intent.getBooleanExtra(Extras.MEMBER_CARD_ISENABLE, false);
        mMemberCardBean = (MemberCardBean) intent.getSerializableExtra(Extras.MEMBER_CARD_BEAN);
    }

    @Override
    protected MemberCardDetailVM loadViewModel() {
        return new MemberCardDetailVM(mIsEnable, mMemberCardBean);
    }

    @Override
    protected int loadLayoutId() {
        return R.layout.activity_member_card_detail;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK || null == data) {
            return;
        }
        mSelectAddressId = data.getStringExtra(Extras.SELECT_ADDRESS_ID);
        mOrderId = data.getStringExtra(Extras.ORDER_ID);
        mItemsType = data.getStringExtra(Extras.ITEMS_SERVER_TYPE);
        if (!TextUtils.isEmpty(mSelectAddressId)) {
            new EditInfoDialog(mContext, getString(R.string.hint_to_store), getString(R.string.edit_hint_to_store), this).show();
        }
    }

    @Override
    public void clickConfirm(String editStr) {
        String consumCode = MD5.getMD5(StringUtils.getString(mOrderId, UserInfoUtils.getUserToken(), DateUtils.currentTimeMillis()));
        viewModel.userMemberCard(mOrderId, mSelectAddressId, mItemsType, consumCode, editStr);
    }
}
