package com.nhb.app.custom.common.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.fast.library.ui.ToastUtil;
import com.fast.library.utils.StringUtils;
import com.nhb.app.custom.R;
import com.nhb.app.custom.bean.AppConfig;
import com.nhb.app.custom.utils.ResourceUtil;


public class UpdateDialog extends BaseCustomerDialog {
    private Context mContext;
    private TextView mNewVersionView;
    private TextView mDesView;
    private TextView mOkView;
    private TextView mCancelView;
    private AppConfig mUpdateInfo;

    public UpdateDialog(Context context, AppConfig updateInfo) {
        super(context, R.style.CustomDialog);
        mContext = context;
        mUpdateInfo = updateInfo;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_ok:
                if (mUpdateInfo.isUpgrade) {
                    try {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(mUpdateInfo.url));
                        mContext.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    ToastUtil.get().shortToast(R.string.is_new_version);
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

    @Override
    protected int setLayoutViewId() {
        return R.layout.dialog_app_update;
    }

    @Override
    protected void initTitle() {
        TextView mTitleView = (TextView) findViewById(R.id.tv_title);
        mTitleView.setText(R.string.find_new_version);
    }

    @Override
    protected void initView() {
        mNewVersionView = (TextView) findViewById(R.id.tv_new_version);
        mDesView = (TextView) findViewById(R.id.tv_umeng_update_content);
        mCancelView = (TextView) findViewById(R.id.tv_cancel);
        mOkView = (TextView) findViewById(R.id.tv_ok);
        setOnClickListener(mCancelView, mOkView);
        setCancelable(false);
    }

    @Override
    protected void attachData() {
        mNewVersionView.setText(StringUtils.getString(ResourceUtil.getString(R.string.new_version), mUpdateInfo.version));
        mDesView.setText(mUpdateInfo.upgradeDesc);
        mCancelView.setVisibility(mUpdateInfo.forced ? View.GONE : View.VISIBLE);
        setCancelable(!mUpdateInfo.forced);
    }
}
