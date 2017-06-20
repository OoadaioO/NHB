package com.nhb.app.custom.ui.items;

import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.fast.library.utils.DateUtils;
import com.nhb.app.custom.R;
import com.nhb.app.custom.base.BaseActivity;
import com.nhb.app.custom.bean.OrderDetailBean;
import com.nhb.app.custom.constant.Extras;
import com.nhb.app.custom.databinding.ActivityUserPayBinding;
import com.nhb.app.custom.utils.PayUtils;
import com.nhb.app.custom.utils.SpCustom;
import com.nhb.app.custom.utils.UserInfoUtils;
import com.nhb.app.custom.utils.helper.RouteHelper;
import com.nhb.app.custom.viewmodel.UserPayVM;


/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-07-10 12:47
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class UserPayActivity extends BaseActivity<UserPayVM, ActivityUserPayBinding> implements View.OnClickListener {

    private String itemName;
    private String orderPrice;
    private String orderId;

    private PayUtils.PayListener mPayListener = new PayUtils.PayListener() {
        @Override
        public void onPaySuccess() {
            showToast(R.string.pay_order_success);
            saveOrderDetailInfo();
            RouteHelper.getInstance().startPaySuccess(UserPayActivity.this,orderId);
//            关闭其他页面
            finish();
        }

        @Override
        public void onPayCancel() {
            showToast(R.string.pay_order_cancel);
            finish();
        }

        @Override
        public void onPayFail() {
            showToast(R.string.pay_order_fail);
            finish();
        }

        @Override
        public void onPayInvalid() {
            showToast(R.string.pay_order_fail);
            finish();
        }
    };

    @Override
    protected void intentWithNormal(Intent intent) {
        super.intentWithNormal(intent);
        itemName = intent.getStringExtra(Extras.ORDER_ITEM_NAME);
        orderPrice = intent.getStringExtra(Extras.ORDER_PRICE);
        orderId = intent.getStringExtra(Extras.ORDER_ID);
    }

    @Override
    protected UserPayVM loadViewModel() {
        return new UserPayVM(this,itemName, orderPrice,orderId);
    }

    @Override
    protected int loadLayoutId() {
        return R.layout.activity_user_pay;
    }

    @Override
    protected void initialize() {
        checkedPayMethod(viewDataBinding.userWxPayRadioBTN, true, false);
        checkedPayMethod(viewDataBinding.userZfbPayRadioBTN, false, true);
        viewDataBinding.userWxPayRadioBTN.setChecked(true);
        setOnClickListener(viewDataBinding.userWxPayRadioBTN,viewDataBinding.userZfbPayRadioBTN,viewDataBinding.rlAliPay,viewDataBinding.rlWechatPay);
        viewModel.payChannel.set("wx");
    }

    private void checkedPayMethod(RadioButton radioButton, final boolean WeChat, final boolean alipay) {
        if (radioButton != null) {
            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        viewDataBinding.userWxPayRadioBTN.setChecked(WeChat);
                        viewDataBinding.userZfbPayRadioBTN.setChecked(alipay);
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.rl_wechat_pay:
            case R.id.user_wx_pay_radioBTN:
                viewDataBinding.userWxPayRadioBTN.setChecked(true);
                viewModel.payChannel.set("wx");
                break;
            case R.id.rl_AliPay:
            case R.id.user_zfb_pay_radioBTN:
                viewDataBinding.userZfbPayRadioBTN.setChecked(true);
                viewModel.payChannel.set("alipay");
                break;
        }
    }

    private void saveOrderDetailInfo(){
        OrderDetailBean bean = new OrderDetailBean();
        bean.orderState = "1";
        bean.orderId = orderId;
        bean.createTime = DateUtils.getNowTime(DateUtils.FORMAT_YYYY_MM_DD_HH_MM_SS_1);
        bean.storePhone = UserInfoUtils.getPhone();
        SpCustom.setOrderDeatailInfo(bean);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        PayUtils.getInstanse().payResult(requestCode,resultCode,data,mPayListener);
    }
}
