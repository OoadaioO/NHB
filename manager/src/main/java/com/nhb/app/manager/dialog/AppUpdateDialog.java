package com.nhb.app.manager.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.fast.library.utils.ToolUtils;
import com.nhb.app.library.dialog.BaseDialog;
import com.nhb.app.manager.R;
import com.nhb.app.manager.bean.AppUpdateBean;

/**
 * 说明：AppUpdateDialog
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/8/20 22:55
 * <p/>
 * 版本：verson 1.0
 */
public class AppUpdateDialog extends BaseDialog {

    private TextView tvCancel,tvConfirm;
    private View line;
    private AppUpdateBean mAppUpdateBean;
    private Activity mActivity;

    public AppUpdateDialog(Context context) {
        super(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onInit() {
        tvCancel = (TextView) getDialogView().findViewById(R.id.tv_cancel);
        tvConfirm = (TextView) getDialogView().findViewById(R.id.tv_confirm);
        line = getDialogView().findViewById(R.id.view_line);

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAppUpdateBean != null){
                    ToolUtils.openBrowser(mActivity,mAppUpdateBean.url);
                }
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void showDialog(AppUpdateBean bean){
        mAppUpdateBean = bean;
        if (bean != null){
            if (bean.forced){
                tvCancel.setVisibility(View.GONE);
                line.setVisibility(View.GONE);
                setCanceledOnTouchOutside(false);
                setCancelable(false);
            }else {
                setCanceledOnTouchOutside(true);
                setCancelable(true);
            }
            show();
        }
    }

    @Override
    public int setDialogView() {
        return R.layout.dialog_app_update;
    }
}
