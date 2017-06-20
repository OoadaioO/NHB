package com.nhb.app.manager.dialog;

import android.content.Context;

import com.nhb.app.library.dialog.BaseDialog;
import com.nhb.app.manager.R;

/**
 * 说明：等待对话框
 * <p>
 * 作者：fanly
 * <p>
 * 类型：Class
 * <p>
 * 时间：2016/7/14 16:22
 * <p>
 * 版本：verson 1.0
 */
public class LoadingDialog extends BaseDialog{

    public LoadingDialog(Context context) {
        super(context);
    }

    @Override
    public void onInit() {

    }

    @Override
    public int setDialogView() {
        return R.layout.dialog_loading;
    }
}
