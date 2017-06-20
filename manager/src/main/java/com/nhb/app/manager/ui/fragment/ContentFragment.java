package com.nhb.app.manager.ui.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.fast.library.utils.StringUtils;
import com.nhb.app.library.base.NHBFragment;
import com.nhb.app.manager.base.CommonActivity;

/**
 * 说明：ContentFragment
 * <p>
 * 作者：fanly
 * <p>
 * 类型：Class
 * <p>
 * 时间：2016/6/30 13:11
 * <p>
 * 版本：verson 1.0
 */
public abstract class ContentFragment extends NHBFragment{

    public CommonActivity mCommonActivity;

    @Override
    protected void onFirstUserVisible() {
        if (getActivity() instanceof CommonActivity){
            mCommonActivity = (CommonActivity) getActivity();
        }
        if (getArguments() != null){
            onInitData(getArguments());
        }
    }

    protected abstract void onInitData(Bundle bundle);

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    /**
     * 根据EditText改变Button是否可点击
     * @param btn
     * @param et
     */
    protected void checkBtnSubmit(Button btn, EditText...et){
        boolean result = true;
        if (et == null || et.length == 0 || btn == null)return;
        for (int i = 0;i < et.length ;i++){
            if (StringUtils.isEmpty(et[i].getText().toString())){
                result = false;
                break;
            }
        }
        btn.setEnabled(result);
    }

    class MyTextWatch implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
