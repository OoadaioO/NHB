package com.nhb.app.custom.ui.personal;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import com.nhb.app.custom.R;
import com.nhb.app.custom.base.BaseActivity;
import com.nhb.app.custom.databinding.ActivityFeedbackBinding;
import com.nhb.app.custom.viewmodel.FeedBackVM;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-09-01 18:14
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class FeedBackActivity extends BaseActivity<FeedBackVM, ActivityFeedbackBinding> {

    @Override
    protected FeedBackVM loadViewModel() {
        return new FeedBackVM();
    }

    @Override
    protected int loadLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initialize() {
        showSoftInput();
    }

    /**
     * 手动调出软键盘
     */
    private void showSoftInput() {
        viewDataBinding.feedbackEditContent.requestFocus();
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(viewDataBinding.feedbackEditContent, InputMethodManager.SHOW_FORCED);
    }
}
