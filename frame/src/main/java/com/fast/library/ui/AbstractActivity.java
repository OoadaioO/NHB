package com.fast.library.ui;

import android.content.Intent;
import android.os.Bundle;
import com.fast.library.utils.LogUtils;

/**
 * 说明：Activity基类
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/10/29 0:19
 * <p/>
 * 版本：verson 1.0
 */
public abstract class AbstractActivity extends FrameActivity{

    @Override
    protected void onCreate(Bundle bundle) {
        LogUtils.v(this.getClass().getName(), "-->onCreate");
        ActivityStack.create().addActivity(this);
        super.onCreate(bundle);
    }

    @Override
    protected void onStart() {
        LogUtils.v(this.getClass().getName(), "-->onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        LogUtils.v(this.getClass().getName(), "-->onResume");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        LogUtils.v(this.getClass().getName(), "-->onRestart");
        super.onRestart();
    }

    @Override
    protected void onStop() {
        LogUtils.v(this.getClass().getName(), "-->onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        LogUtils.v(this.getClass().getName(), "-->onDestroy");
        ActivityStack.create().finishActivity(this);
        super.onDestroy();
    }


    @Override
    public void skipActivity(Class<?> cls) {
        showActivity(cls);
        finish();
    }

    @Override
    public void skipActivity(Intent intent) {
        showActivity(intent);
        finish();
    }

    @Override
    public void skipActivity(Class<?> cls, Bundle bundle) {
        showActivity(cls, bundle);
        finish();
    }

    /**
     * 说明：显示Activity,但不finish
     */
    @Override
    public void showActivity(Class<?> cls) {
        Intent intent = new Intent(this,cls);
        startActivity(intent);
    }

    /**
     * 说明：显示Activity,但不finish
     */
    @Override
    public void showActivityForResult(Class<?> cls,int requestCode) {
        Intent intent = new Intent(this,cls);
        startActivityForResult(intent,requestCode);
    }

    /**
     * 说明：显示Activity,但不finish
     */
    @Override
    public void showActivity(Intent intent) {
        startActivity(intent);
    }

    /**
     * 说明：显示Activity,但不finish
     */
    @Override
    public void showActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this,cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 说明：显示Activity，传递简单参数
     * @param cls
     * @param args
     */
    public void showActivity(Class<?> cls,String...args){
        if (args == null || args.length == 0){
            showActivity(cls);
        }else {
            if (args.length % 2 != 0){
                throw new RuntimeException(args + " 参数不正确！");
            }
            Bundle bundle = new Bundle();
            for (int i=0;i < args.length;i+=2){
                bundle.putString(args[i],args[i+1]);
            }
            showActivity(cls,bundle);
        }
    }
}

