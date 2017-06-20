package com.nhb.app.manager.ui.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;

import com.fast.library.ui.ContentView;
import com.fast.library.utils.StringUtils;
import com.fast.library.utils.ToolUtils;
import com.nhb.app.library.event.EventCenter;
import com.nhb.app.library.http.NHBSubscriber;
import com.nhb.app.manager.R;
import com.nhb.app.manager.event.EventType;
import com.nhb.app.manager.model.ManagerAPI;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 说明：修改密码
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2016/6/30 16:04
 * <p/>
 * 版本：verson 1.0
 */
@ContentView(R.layout.fragment_update_pwd)
public class UpdatePwdFragment extends ContentFragment {

    @Bind(R.id.et_pwd_src)
    EditText etPwdSrc;
    @Bind(R.id.et_pwd_new)
    EditText etPwdNew;
    @Bind(R.id.et_pwd_again)
    EditText etPwdAgain;
    @Bind(R.id.btn_submit)
    Button btnSubmit;

    @Override
    protected void onInitData(Bundle bundle) {
        etPwdNew.addTextChangedListener(new MyTextWatch(){
            @Override
            public void afterTextChanged(Editable s) {
                checkBtnSubmit(btnSubmit,etPwdAgain,etPwdNew,etPwdSrc);
            }
        });
        etPwdAgain.addTextChangedListener(new MyTextWatch(){
            @Override
            public void afterTextChanged(Editable s) {
                checkBtnSubmit(btnSubmit,etPwdAgain,etPwdNew,etPwdSrc);
            }
        });
        etPwdSrc.addTextChangedListener(new MyTextWatch(){
            @Override
            public void afterTextChanged(Editable s) {
                checkBtnSubmit(btnSubmit,etPwdAgain,etPwdNew,etPwdSrc);
            }
        });
        ToolUtils.showSoftInput(etPwdSrc);
    }

    @OnClick(R.id.btn_submit)
    public void onClick() {
        if (checkPwd()){
            submit();
        }
    }

    private void submit(){
        String pwdSrc = etPwdSrc.getText().toString();
        String pwdNew = etPwdNew.getText().toString();
        if (mCommonActivity != null){
            mCommonActivity.showLoading();
        }
        ManagerAPI.getAPI().updatePwd(pwdSrc,pwdNew)
                .subscribe(new NHBSubscriber<String>() {
                    @Override
                    public void onError(int code, String msg) {
                        shortToast(R.string.update_pwd_fail);
                    }

                    @Override
                    public void onNext(String s) {
                        if (StringUtils.isEmpty(s)){
                            shortToast(R.string.update_pwd_success);
                            EventBus.getDefault().post(new EventCenter<String>(EventType.MESSAGE_LOGINOUT,""));
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

    private boolean checkPwd(){
        String pwdSrc = etPwdSrc.getText().toString();
        String pwdNew = etPwdNew.getText().toString();
        String pwdAgain = etPwdAgain.getText().toString();
        if (!StringUtils.isEquals(pwdNew,pwdAgain)){
            shortToast(R.string.update_pwd_again_new_dif);
            return false;
        }
        if (StringUtils.isEquals(pwdNew,pwdSrc)){
            shortToast(R.string.update_pwd_again_src_nodif);
            return false;
        }
        return true;
    }
}
