package com.nhb.app.manager.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.nhb.app.manager.ui.fragment.ContentFragment;
import com.nhb.app.manager.ui.fragment.H5Fragment;
import com.nhb.app.manager.ui.fragment.SuggetionFragment;
import com.nhb.app.manager.ui.fragment.UpdatePwdFragment;

/**
 * 说明：ContentFragmentFactory
 * <p>
 * 作者：fanly
 * <p>
 * 类型：Class
 * <p>
 * 时间：2016/6/30 15:24
 * <p>
 * 版本：verson 1.0
 */
public class ContentFragmentFactory {

    public final static int INDEX_H5 = 1;//H5页面
    public final static int INDEX_SUGGESTION= 2;//意见反馈
    public final static int INDEX_UPDATE_PWD= 3;//修改密码

    public ContentFragment create(int index, Context context,Bundle bundle){
        ContentFragment fragment = null;
        switch (index){
            case INDEX_H5:
                fragment = (H5Fragment)Fragment.instantiate(context,H5Fragment.class.getName(),bundle);
                break;
            case INDEX_SUGGESTION:
                fragment = (SuggetionFragment)Fragment.instantiate(context,SuggetionFragment.class.getName(),bundle);
                break;
            case INDEX_UPDATE_PWD:
                fragment = (UpdatePwdFragment)Fragment.instantiate(context,UpdatePwdFragment.class.getName(),bundle);
                break;
            default:
                throw new IllegalArgumentException("index="+index+" 没有指定的Fragment!");
        }
        return fragment;
    }
}
