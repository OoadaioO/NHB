package com.nhb.app.library.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.nhb.app.library.tab.ViewPageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明：FragmentAdapter
 * <p>
 * 作者：fanly
 * <p>
 * 类型：Class
 * <p>
 * 时间：2016/6/23 19:09
 * <p>
 * 版本：verson 1.0
 */
public class FragmentAdapter extends FragmentStatePagerAdapter{

    private List<ViewPageInfo> infos;
    private Context context;

    public FragmentAdapter(FragmentManager fm, Context context,List<ViewPageInfo> infos) {
        super(fm);
        this.context = context;
        this.infos = infos == null ? new ArrayList<ViewPageInfo>(0) : infos;
    }

    @Override
    public Fragment getItem(int position) {
        ViewPageInfo info = infos.get(position);
        return Fragment.instantiate(context,info.clazz.getName(),info.params);
    }

    @Override
    public int getCount() {
        return infos.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return infos.get(position).title;
    }
}
