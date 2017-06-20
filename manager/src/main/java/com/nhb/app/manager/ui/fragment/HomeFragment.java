package com.nhb.app.manager.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.fast.library.ui.ContentView;
import com.fast.library.utils.UIUtils;
import com.nhb.app.library.adapter.FragmentAdapter;
import com.nhb.app.library.base.NHBFragment;
import com.nhb.app.library.tab.ViewPageInfo;
import com.nhb.app.manager.R;

import java.util.ArrayList;
import butterknife.Bind;

/**
 * 说明：首页
 * <p>
 * 作者：fanly
 * <p>
 * 类型：Class
 * <p>
 * 时间：2016/6/23 9:35
 * <p>
 * 版本：verson 1.0
 */
@ContentView(R.layout.fragment_home)
public class HomeFragment extends NHBFragment {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.homeViewPager)
    ViewPager homeViewPager;
    @Bind(R.id.homeTabLayout)
    TabLayout homeTabLayout;

    private FragmentAdapter mFragmentAdapter;

    public final static String TYPE = "type";
    public final static int SHELVES_LOADING = 0;//待上架
    public final static int SHELVES_GOING = 1;//已上架
    public final static int SHELVES_OFF = 2;//已下架

    @Override
    protected void onFirstUserVisible() {
        tvTitle.setText(R.string.nhb);
        bind(R.id.iv_right,true);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.drawable.qrcode);
        initTabLayout();
    }

    private void initTabLayout(){
        ArrayList<ViewPageInfo> infos = new ArrayList<>();
        String []titles = UIUtils.getStringArray(R.array.home_goods);
        infos.add(new ViewPageInfo(titles[0],HomeListFragment.class,createBundle(SHELVES_LOADING)));
        infos.add(new ViewPageInfo(titles[1],HomeListFragment.class,createBundle(SHELVES_GOING)));
        infos.add(new ViewPageInfo(titles[2],HomeListFragment.class,createBundle(SHELVES_OFF)));
        mFragmentAdapter = new FragmentAdapter(getChildFragmentManager(),getActivity(),infos);
        homeViewPager.setAdapter(mFragmentAdapter);
        homeTabLayout.setTabMode(TabLayout.MODE_FIXED);
        homeTabLayout.setupWithViewPager(homeViewPager);
        homeViewPager.setOffscreenPageLimit(3);
        homeTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                homeViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private Bundle createBundle(int type){
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE,type);
        return bundle;
    }

    @Override
    protected void clickView(View v, int id) {
        switch (id){
            case R.id.iv_right://扫一扫
                showActivity(com.nhb.app.zbar.activity.CaptureActivity.class);
                break;
        }
    }

    @Override
    protected void onUserVisible() {
    }

    @Override
    protected void onUserInvisible() {
    }

}
