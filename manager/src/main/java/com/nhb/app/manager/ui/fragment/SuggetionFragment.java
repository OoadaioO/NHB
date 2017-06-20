package com.nhb.app.manager.ui.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;

import com.fast.library.ui.ContentView;
import com.fast.library.utils.StringUtils;
import com.nhb.app.library.http.NHBSubscriber;
import com.nhb.app.manager.R;
import com.nhb.app.manager.model.ManagerAPI;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 说明：意见反馈
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2016/6/30 14:18
 * <p/>
 * 版本：verson 1.0
 */
@ContentView(R.layout.fragment_suggestion)
public class SuggetionFragment extends ContentFragment {

    @Bind(R.id.et_suggest)
    EditText etSuggest;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.btn_submit)
    Button btnSubmit;

    @Override
    protected void onInitData(Bundle bundle) {
        etSuggest.addTextChangedListener(new MyTextWatch(){
            @Override
            public void afterTextChanged(Editable s) {
                checkBtnSubmit(btnSubmit,etSuggest,etPhone);
            }
        });
        etPhone.addTextChangedListener(new MyTextWatch(){
            @Override
            public void afterTextChanged(Editable s) {
                checkBtnSubmit(btnSubmit,etSuggest,etPhone);
            }
        });
    }

    @OnClick(R.id.btn_submit)
    public void onClick() {
        String suggest = etSuggest.getText().toString();
        String phone = etPhone.getText().toString();
        if (StringUtils.isEmpty(suggest)){
            shortToast(R.string.suggest_empty_content);
            return;
        }
        if (StringUtils.isEmpty(phone)){
            shortToast(R.string.suggest_empty_phone);
            return;
        }
        if (!StringUtils.isPhone(phone)){
            shortToast(R.string.suggest_empty_phone_error);
            return;
        }
        submit(phone,suggest);
    }

    private void submit(String phone,String message){
        if (mCommonActivity != null){
            mCommonActivity.showLoading();
        }
        ManagerAPI.getAPI().suggestion(phone,message)
                .subscribe(new NHBSubscriber<String>() {
                    @Override
                    public void onError(int code, String msg) {
                        shortToast(R.string.submit_fail);
                    }

                    @Override
                    public void onNext(String s) {
                        if (StringUtils.isEmpty(s)){
                            shortToast(R.string.submit_success);
                            getActivity().finish();
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if (mCommonActivity != null){
                            mCommonActivity.dismissLoading();
                        }
                    }
                });
    }

}
