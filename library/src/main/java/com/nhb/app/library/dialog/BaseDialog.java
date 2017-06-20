package com.nhb.app.library.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.fast.library.utils.UIUtils;
import com.nhb.app.library.R;

/**
 * 说明：对话框
 * <p>
 * 作者：fanly
 * <p>
 * 类型：Class
 * <p>
 * 时间：2016/7/14 16:19
 * <p>
 * 版本：verson 1.0
 */
public abstract class BaseDialog extends Dialog {

    private View mDialogView;

    public BaseDialog(Context context) {
        this(context, R.style.MyDialogStyle);
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
        init(setDialogView());
    }

    public BaseDialog(Context context, int themeResId,int layoutId) {
        super(context, themeResId);
        init(layoutId);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    /**
     * 说明：初始化
     */
    private void init(int layoutId) {
        mDialogView = UIUtils.inflate(layoutId);
        if (setParams() != null){
            addContentView(mDialogView, setParams());
        }else {
            setContentView(mDialogView);
        }
        onInit();
    }

    public LinearLayout.LayoutParams setParams(){
        return null;
    }

    /**
     * 说明：初始化
     */
    public abstract void onInit();

    /**
     * 说明：设置自定义布局
     * @return
     */
    public abstract int setDialogView();

    /**
     * 说明：获取自定义布局
     * @return
     */
    public View getDialogView(){
        return mDialogView;
    }

}
