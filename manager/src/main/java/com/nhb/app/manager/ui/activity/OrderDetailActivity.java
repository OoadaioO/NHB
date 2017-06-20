package com.nhb.app.manager.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fast.library.glide.GlideLoader;
import com.fast.library.glide.MulBitmapTransformation;
import com.fast.library.span.SpanSetting;
import com.fast.library.span.SpanTextUtils;
import com.fast.library.tools.ViewTools;
import com.fast.library.ui.ContentView;
import com.fast.library.utils.StringUtils;
import com.fast.library.utils.ToolUtils;
import com.nhb.app.library.event.EventCenter;
import com.nhb.app.library.http.NHBSubscriber;
import com.nhb.app.manager.R;
import com.nhb.app.manager.base.CommonActivity;
import com.nhb.app.manager.base.NmConstant;
import com.nhb.app.manager.bean.OrderMessageBean;
import com.nhb.app.manager.bean.TradeStateBean;
import com.nhb.app.manager.event.EventType;
import com.nhb.app.manager.model.ManagerAPI;
import com.nhb.app.manager.model.SpManager;
import com.nhb.app.manager.ui.helper.MessageHelper;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 说明：订单详情
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/8/21 0:07
 * <p/>
 * 版本：verson 1.0
 */
@ContentView(R.layout.activity_order_detail)
public class OrderDetailActivity extends CommonActivity {

    public static String ORDER_INFO= "orderinfo";
//    public int id;
    public OrderMessageBean mOrderMessageBean;

    @Bind(R.id.iv_custom_pic)
    ImageView ivCustomPic;
    @Bind(R.id.tv_order_state)
    TextView tvOrderState;
    @Bind(R.id.tv_custom_name)
    TextView tvCustomName;
    @Bind(R.id.iv_item_pic)
    ImageView ivItemPic;
    @Bind(R.id.tv_item_name)
    TextView tvItemName;
    @Bind(R.id.tv_item_price)
    TextView tvItemPrice;
    @Bind(R.id.tv_custom_phone)
    TextView tvCustomPhone;
    @Bind(R.id.tv_message_orderid)
    TextView tvMessageOrderid;
    @Bind(R.id.tv_message_time)
    TextView tvMessageTime;
    @Bind(R.id.tv_message_address)
    TextView tvMessageAddress;
    @Bind(R.id.tv_confirm_order)
    TextView tvConfirmOrder;
    @Bind(R.id.tv_message_remark)
    TextView tvMessageRemark;
    @Bind(R.id.ll_order_option)
    LinearLayout llOrderOption;

    @Override
    public void getIntentData(Intent intent) {
        super.getIntentData(intent);
        mOrderMessageBean = intent.getParcelableExtra(ORDER_INFO);
    }

    @Override
    protected boolean isShowBackIcon() {
        return true;
    }

    @Override
    protected void initTitleBar(View titleView, TextView tvTitle) {
        super.initTitleBar(titleView, tvTitle);
        tvTitle.setText("订单详情");
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

    }

    @Override
    public void onInitStart() {
        super.onInitStart();
        if (mOrderMessageBean != null){
            initView();
        }
    }

    /**
     * 初始化界面
     */
    private void initView(){
//        订单状态
        initOrderOption();
//        用户头像
        GlideLoader.load(this,mOrderMessageBean.getCustomPic()).transform(new MulBitmapTransformation(this,MulBitmapTransformation.TYPE_CIRCLE)).into(ivCustomPic);
//        用户名称
        tvCustomName.setText(mOrderMessageBean.getCustomName());
//        商品的图片
        GlideLoader.into(mOrderMessageBean.getItemPic(),ivItemPic);
//        商品的名称
        tvItemName.setText(mOrderMessageBean.getItemName());
//        商品的价钱
        SpanSetting priceFisrt = new SpanSetting().setCharSequence("价格：");
        SpanSetting priceSecond = new SpanSetting().setCharSequence("￥"+mOrderMessageBean.getItemPrice());
        SpanTextUtils.setText(tvItemPrice,priceFisrt,priceSecond);
//        用户手机号码
        tvCustomPhone.setText(getCustomPhone());
//        订单号
        tvMessageOrderid.setText(mOrderMessageBean.getTradeNo());
//        创建时间
        tvMessageTime.setText(mOrderMessageBean.getCreateTime());
//        地址
        tvMessageAddress.setText(mOrderMessageBean.getAddress());
//        用户留言
        ViewTools.setText(tvMessageRemark,mOrderMessageBean.getRemark());
    }

    private String getCustomPhone(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("手机号码：");
        if (!StringUtils.isEmpty(mOrderMessageBean.getCustomPhone()) && mOrderMessageBean.getCustomPhone().length() == 11){
            for (int i=0;i<11;i++){
                stringBuilder.append(mOrderMessageBean.getCustomPhone().charAt(i));
                if (i == 2 || i == 6){
                    stringBuilder.append("-");
                }
            }
        }else {
            stringBuilder.append(mOrderMessageBean.getCustomPhone());
        }
        return stringBuilder.toString();
    }

    private void initOrderOption(){
        if (StringUtils.isEquals(mOrderMessageBean.getTradeState(), NmConstant.TradeState.STATE_COMPLETE)){
//            完成订单
            tvOrderState.setText(R.string.order_detail_state_complete);
            llOrderOption.setVisibility(View.INVISIBLE);
        }else if (StringUtils.isEquals(mOrderMessageBean.getTradeState(), NmConstant.TradeState.STATE_CANCEL)){
//            取消订单
            tvOrderState.setText(R.string.order_detail_state_cancel);
            llOrderOption.setVisibility(View.INVISIBLE);
        }else if (StringUtils.isEquals(mOrderMessageBean.getTradeState(), NmConstant.TradeState.STATE_TO_CONFIRM)){
//            待确认订单
            tvConfirmOrder.setText(R.string.order_detail_confirm_order);
            tvConfirmOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmOrder();
                }
            });
            llOrderOption.setVisibility(View.VISIBLE);
        }else if (StringUtils.isEquals(mOrderMessageBean.getTradeState(), NmConstant.TradeState.STATE_CONFIRMED)){
//            确认订单
            tvConfirmOrder.setText(R.string.order_detail_complete_order);
            tvConfirmOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    completeOrder();
                }
            });
            llOrderOption.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 确认订单
     */
    private void confirmOrder(){
        ManagerAPI.getAPI().updateTradeState(SpManager.getUserInfo().token,mOrderMessageBean.getTradeNo(),"1").subscribe(new NHBSubscriber<TradeStateBean>() {

            @Override
            public void onStart() {
                super.onStart();
                showLoading();
            }

            @Override
            public void onError(int code, String msg) {
                shortToast(R.string.order_detail_state_update_fail);
            }

            @Override
            public void onNext(TradeStateBean tradeStateBean) {
                if (StringUtils.isEquals(tradeStateBean.tradeNo,mOrderMessageBean.getTradeNo()) && NmConstant.TradeState.STATE_CONFIRMED.equals(tradeStateBean.tradeState)){
//                    操作成功
                    shortToast(R.string.order_detail_state_update_suceess);
                    MessageHelper.refreshMessage();
                    mOrderMessageBean.setTradeState(tradeStateBean.tradeState);
                    initOrderOption();
                }else {
                    onError(-1,"");
                }
            }

            @Override
            public void onCompleted() {
                dismissLoading();
            }
        });
    }

    /**
     * 完成订单
     */
    private void completeOrder(){
        ManagerAPI.getAPI().updateTradeState(SpManager.getUserInfo().token,mOrderMessageBean.getTradeNo(),"0").subscribe(new NHBSubscriber<TradeStateBean>() {

            @Override
            public void onStart() {
                super.onStart();
                showLoading();
            }

            @Override
            public void onError(int code, String msg) {
                shortToast(R.string.order_detail_state_update_fail);
            }

            @Override
            public void onNext(TradeStateBean tradeStateBean) {
                if (StringUtils.isEquals(tradeStateBean.tradeNo,mOrderMessageBean.getTradeNo()) && NmConstant.TradeState.STATE_COMPLETE.equals(tradeStateBean.tradeState)){
//                            操作成功
                    shortToast(R.string.order_detail_state_update_suceess);
                    EventBus.getDefault().post(new EventCenter<String>(EventType.MESSAGE_REFRESH,""));
                    mOrderMessageBean.setTradeState(tradeStateBean.tradeState);
                    initOrderOption();
                }else {
                    onError(-1,"");
                }
            }

            @Override
            public void onCompleted() {
                dismissLoading();
            }
        });
    }

    @OnClick({R.id.rl_custom_phone, R.id.tv_cancel_order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_custom_phone:
                ToolUtils.callPhone(this,mOrderMessageBean.getCustomPhone());
                break;
            case R.id.tv_cancel_order://取消订单
                ManagerAPI.getAPI().updateTradeState(SpManager.getUserInfo().token,mOrderMessageBean.getTradeNo(),"2").subscribe(new NHBSubscriber<TradeStateBean>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        showLoading();
                    }

                    @Override
                    public void onError(int code, String msg) {
                        shortToast(R.string.order_detail_state_update_fail);
                    }

                    @Override
                    public void onNext(TradeStateBean tradeStateBean) {
                        if (StringUtils.isEquals(tradeStateBean.tradeNo,mOrderMessageBean.getTradeNo()) && NmConstant.TradeState.STATE_CANCEL.equals(tradeStateBean.tradeState)){
//                            操作成功
                            shortToast(R.string.order_detail_state_update_suceess);
                            EventBus.getDefault().post(new EventCenter<String>(EventType.MESSAGE_REFRESH,""));
                            mOrderMessageBean.setTradeState(tradeStateBean.tradeState);
                            initOrderOption();
                        }else {
                            onError(-1,"");
                        }
                    }

                    @Override
                    public void onCompleted() {
                        dismissLoading();
                    }
                });
                break;
        }
    }
}
