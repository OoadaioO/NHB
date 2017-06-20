package com.nhb.app.manager.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fast.library.ui.ContentView;
import com.fast.library.utils.DateUtils;
import com.fast.library.utils.StringUtils;
import com.nhb.app.library.http.NHBSubscriber;
import com.nhb.app.manager.R;
import com.nhb.app.manager.base.CommonActivity;
import com.nhb.app.manager.base.NmConstant;
import com.nhb.app.manager.bean.OrderMessageBean;
import com.nhb.app.manager.bean.ParseQrCode;
import com.nhb.app.manager.model.ManagerAPI;
import com.nhb.app.manager.ui.helper.MessageHelper;

import butterknife.Bind;
import rx.Subscription;

/**
 * 说明：会员卡
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2016/8/19 14:38
 * <p/>
 * 版本：verson 1.0
 */
@ContentView(R.layout.activity_card)
public class CardActivity extends CommonActivity{

    public final static String CARD_INFO = "cardinfo";
    public String cipher;

    @Bind(R.id.iv_card_icon)
    ImageView ivCardIcon;
    @Bind(R.id.tv_card_state)
    TextView tvCardState;

    @Override
    public void getIntentData(Intent intent) {
        super.getIntentData(intent);
        cipher = intent.getStringExtra(CARD_INFO);
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
        Subscription cSub = ManagerAPI.getAPI().parseQrCode(cipher)
                .subscribe(new NHBSubscriber<ParseQrCode>() {
                    @Override
                    public void onError(int code, String msg) {
                        //验证失败
                        validationFail();
                    }

                    @Override
                    public void onNext(ParseQrCode parseQrCode) {
                        validation(parseQrCode);
                    }
                });
        addSubscrebe(cSub);
    }

    private void validation(ParseQrCode code){
        if (code != null){
            //查询
            Subscription subscription = ManagerAPI.getAPI().useCard(cipher,code.token,code.orderId)
                    .subscribe(new NHBSubscriber<OrderMessageBean>() {
                        @Override
                        public void onError(int code, String msg) {
                            validationFail();
                        }

                        @Override
                        public void onNext(OrderMessageBean orderMessageBean) {
                            if (orderMessageBean != null){
                                MessageHelper.refreshMessage();
                                validationSuccess();
                            }else {
                                validationFail();
                            }
                        }
                    });
            addSubscrebe(subscription);
        }else {
            validationFail();
        }
    }

    /**
     * 验证失败
     */
    private void validationFail(){
        ivCardIcon.setImageResource(R.drawable.wrong);
        tvCardState.setText(R.string.card_scan_fail);
    }

    /**
     * 验证通过
     */
    private void validationSuccess(){
        ivCardIcon.setImageResource(R.drawable.success);
        tvCardState.setText(R.string.card_scan_success);
    }

    @Override
    protected boolean isShowBackIcon() {
        return true;
    }

    @Override
    protected void initTitleBar(View titleView, TextView tvTitle) {
        super.initTitleBar(titleView, tvTitle);
        tvTitle.setText("会员卡");
    }

}
