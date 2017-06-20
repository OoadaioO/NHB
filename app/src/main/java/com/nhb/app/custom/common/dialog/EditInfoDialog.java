package com.nhb.app.custom.common.dialog;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.nhb.app.custom.R;

/**
 * Created by you on 2015/6/1.
 */
public class EditInfoDialog extends BaseCustomerDialog {

    private EditText mEditView;
    private String mTitleRes;
    private String mHintRes;

    private OnActionListener mOnActionListener;

    public EditInfoDialog(Context context, String titleRes, String hintRes, OnActionListener onActionListener) {
        super(context, R.style.CustomDialog);
        mTitleRes = titleRes;
        mHintRes = hintRes;
        setOnActionListener(onActionListener);
    }

    @Override
    protected int setLayoutViewId() {
        return R.layout.dialog_edit_info;
    }

    @Override
    protected void initTitle() {
        TextView titleView = (TextView) findViewById(R.id.tv_title);
        titleView.setText(mTitleRes);
    }

    @Override
    protected void initView() {
        View cancelButton = findViewById(R.id.tv_cancel);
        View confirmButton = findViewById(R.id.tv_ok);
        mEditView = (EditText) findViewById(R.id.et_dialog_info);
        setOnClickListener(cancelButton, confirmButton);
    }

    @Override
    protected void attachData() {
        mEditView.setHint(mHintRes);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_ok:
                if (mOnActionListener != null) {
                    mOnActionListener.clickConfirm(String.valueOf(mEditView.getText()));
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
        void clickConfirm(String editStr);
    }
}
