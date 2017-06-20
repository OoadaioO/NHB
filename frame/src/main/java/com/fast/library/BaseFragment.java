package com.fast.library;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fast.library.http.HttpTaskKey;
import com.fast.library.ui.SupportFragment;
import com.fast.library.ui.ToastUtil;

/**
 * 说明：Fragment基类(V4)
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/3/26 1:23
 * <p/>
 * 版本：verson 1.0
 */
public abstract class BaseFragment extends SupportFragment implements HttpTaskKey {

    private FragmentActivity mActivity;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mActivity = getActivity();
        return inflater.inflate(getRootViewResID(),null);
    }

    @Override
    public String getHttpTaskKey() {
        return "key"+hashCode();
    }

    /***************************************************************************************/

    public void shortToast(int res){
        ToastUtil.get().shortToast(res);
    }
    public void shortToast(String res){
        ToastUtil.get().shortToast(res);
    }
    public void longToast(String res){
        ToastUtil.get().longToast(res);
    }
    public void longToast(int res){
        ToastUtil.get().longToast(res);
    }
    public void cancelToast(){
        ToastUtil.get().cancelToast();
    }

    /***************************************************************************************/

    /***************************************************************************************/

    public void skipActivity(Class<?> cls) {
        skipActivity(cls);
        mActivity.finish();
    }

    public void skipActivity(Intent intent) {
        skipActivity(intent);
        mActivity.finish();
    }

    public void skipActivity(Class<?> cls, Bundle bundle) {
        skipActivity(cls, bundle);
        mActivity.finish();
    }

    public void showActivity(Class<?> cls) {
        Intent intent = new Intent(mActivity,cls);
        mActivity.startActivity(intent);
    }

    public void showActivity(Intent intent) {
        mActivity.startActivity(intent);
    }

    public void showActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(mActivity,cls);
        intent.putExtras(bundle);
        mActivity.startActivity(intent);
    }

    public void showActivity(Class<?> cls,String...args){
        if (args == null){
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

    /***************************************************************************************/

    /***************************************************************************************/

    public FragmentManager getSupportFragmentManager() {
        if (mActivity != null){
            return mActivity.getSupportFragmentManager();
        }else {
            return null;
        }
    }

    /***************************************************************************************/
}
