package com.nhb.app.custom.common.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.nhb.app.custom.R;

public class CommonDialog extends BaseCustomerDialog {

    private String mTitleStr;
    private String mContentStr;
    private String mCancelStr;
    private String mSubmitStr;

    private OnActionListener mOnActionListener;

    public CommonDialog(Context context, String strTitle, String strContent, String strNeg, String strPos, OnActionListener onActionListener) {
        super(context, R.style.CustomDialog);
        setCanceledOnTouchOutside(false);
        this.mTitleStr = strTitle;
        this.mContentStr = strContent;
        this.mCancelStr = strNeg;
        this.mSubmitStr = strPos;
        setOnActionListener(onActionListener);
    }

    @Override
    protected int setLayoutViewId() {
        return R.layout.dialog_common;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
        TextView mTitle = (TextView) findViewById(R.id.dialog_tv_title);
        TextView mContent = (TextView) findViewById(R.id.dialog_tv_content);
        TextView mCancel = (TextView) findViewById(R.id.tv_cancel);
        TextView mSubmit = (TextView) findViewById(R.id.tv_ok);
        mTitle.setText(mTitleStr);
        mContent.setText(mContentStr);
        mCancel.setText(mCancelStr);
        mCancel.setVisibility(!TextUtils.isEmpty(mCancelStr) ? View.VISIBLE : View.GONE);
        mSubmit.setText(mSubmitStr);
        mSubmit.setVisibility(!TextUtils.isEmpty(mSubmitStr) ? View.VISIBLE : View.GONE);
        setCanceledOnTouchOutside(true);
        setOnClickListener(mCancel, mSubmit);
    }

    @Override
    protected void attachData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_ok:
                if (mOnActionListener != null) {
                    mOnActionListener.clickConfirm();
                }
                dismiss();
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
            default:
                dismiss();
                break;
        }
    }

    public void setOnActionListener(OnActionListener listener) {
        mOnActionListener = listener;
    }

    public interface OnActionListener {
        void clickConfirm();
    }
}
