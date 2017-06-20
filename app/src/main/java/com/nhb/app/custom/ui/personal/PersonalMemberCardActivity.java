package com.nhb.app.custom.ui.personal;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.TextUtils;

import com.fast.library.utils.DateUtils;
import com.fast.library.utils.MD5;
import com.fast.library.utils.StringUtils;
import com.fast.library.utils.UIUtils;
import com.nhb.app.custom.R;
import com.nhb.app.custom.base.BaseActivity;
import com.nhb.app.custom.common.dialog.EditInfoDialog;
import com.nhb.app.custom.constant.Extras;
import com.nhb.app.custom.databinding.ActivityPersonalMemberCardBinding;
import com.nhb.app.custom.recyclerview.NHBRecyclerFragment;
import com.nhb.app.custom.utils.UserInfoUtils;
import com.nhb.app.custom.viewmodel.MemberCardListVM;
import com.nhb.app.custom.viewmodel.MemberCardVM;

import java.util.ArrayList;
import java.util.List;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-07-02 00:09
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class PersonalMemberCardActivity extends BaseActivity<MemberCardVM, ActivityPersonalMemberCardBinding> implements TabLayout.OnTabSelectedListener, EditInfoDialog.OnActionListener {
    private static final boolean IS_ENABLE = true;
    public static final int REQUEST_CODE_SELECT_ADDRESS = 1;

    private String mSelectAddressId;
    private String mOrderId;
    private String mItemsType;

    @Override
    protected MemberCardVM loadViewModel() {
        return new MemberCardVM();
    }

    @Override
    protected int loadLayoutId() {
        return R.layout.activity_personal_member_card;
    }

    @Override
    protected void initialize() {
        String[] titles = UIUtils.getStringArray(R.array.tab_member_card);
        List<Fragment> fragmentList = new ArrayList<>();
        NHBRecyclerFragment enEnableFragment = new NHBRecyclerFragment().setViewModel(new MemberCardListVM(IS_ENABLE));
        NHBRecyclerFragment unEnableFragment = new NHBRecyclerFragment().setViewModel(new MemberCardListVM(!IS_ENABLE));
        fragmentList.add(enEnableFragment);
        fragmentList.add(unEnableFragment);
        viewDataBinding.memberCardViewPager.setAdapter(new MemberCardListPagerAdapter(getSupportFragmentManager(), fragmentList, titles));
        viewDataBinding.memberCardTabLayout.setTabMode(TabLayout.MODE_FIXED);
        viewDataBinding.memberCardTabLayout.setupWithViewPager(viewDataBinding.memberCardViewPager);
        viewDataBinding.memberCardViewPager.setOffscreenPageLimit(3);
        viewDataBinding.memberCardTabLayout.setOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewDataBinding.memberCardViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void clickConfirm(String editStr) {
        String consumCode = MD5.getMD5(StringUtils.getString(mOrderId, UserInfoUtils.getUserToken(), DateUtils.currentTimeMillis()));
        viewModel.userMemberCard(mOrderId, mSelectAddressId, mItemsType, consumCode, editStr);
    }

    private class MemberCardListPagerAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> mFragments;
        private String[] mTitles;

        public MemberCardListPagerAdapter(FragmentManager manager, List<Fragment> fragments, String[] titles) {
            super(manager);
            mFragments = fragments;
            mTitles = titles;
        }


        public Fragment getItem(int position) {
            return null == mFragments ? null : mFragments.get(position);
        }

        @Override
        public int getCount() {
            return null == mFragments ? 0 : mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null == mTitles ? "" : mTitles[position];
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK || null == data) {
            return;
        }
        mSelectAddressId = data.getStringExtra(Extras.SELECT_ADDRESS_ID);
        mOrderId = data.getStringExtra(Extras.ORDER_ID);
        mItemsType = data.getStringExtra(Extras.ITEMS_SERVER_TYPE);
        if (!TextUtils.isEmpty(mSelectAddressId)) {
            new EditInfoDialog(mContext, getString(R.string.hint_to_store), getString(R.string.edit_hint_to_store), this).show();
        }
    }
}
