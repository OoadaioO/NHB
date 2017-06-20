package com.nhb.app.custom.ui.search;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.nhb.app.custom.R;
import com.nhb.app.custom.base.BaseActivity;
import com.nhb.app.custom.common.view.CommonSearchLayout;
import com.nhb.app.custom.constant.Extras;
import com.nhb.app.custom.databinding.ActivityCommonSearchResultBinding;
import com.nhb.app.custom.recyclerview.NHBRecyclerFragment;
import com.nhb.app.custom.utils.SearchHistoryUtils;
import com.nhb.app.custom.viewmodel.CommonSearchResultVM;
import com.nhb.app.custom.viewmodel.ItemsSearchResultListVM;


/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-06 16:02
 * Version:1.0.0
 * Description:搜索结果页
 * ***********************************************************************
 */
public class CommonSearchResultActivity extends BaseActivity<CommonSearchResultVM, ActivityCommonSearchResultBinding> implements CommonSearchLayout.OnActionListener {
    private static final int TAB_ITEMS = 0;

    private NHBRecyclerFragment mItemsFragment;

    private ItemsSearchResultListVM mItemsSearchResultListVM;

    private String mSearchContent;
    private boolean isSearchContentChange;

    @Override
    protected void intentWithNormal(Intent intent) {
        super.intentWithNormal(intent);
        mSearchContent = intent.getStringExtra(Extras.SEARCH_CONTENT);
    }

    @Override
    protected CommonSearchResultVM loadViewModel() {
        return new CommonSearchResultVM();
    }

    @Override
    protected int loadLayoutId() {
        return R.layout.activity_common_search_result;
    }

    @Override
    protected void initialize() {
        viewDataBinding.commonSearchResultCsl.setSearchCallback(this);
        viewDataBinding.commonSearchResultCsl.initContent(mSearchContent);
        if (null == mItemsFragment) {
            mItemsSearchResultListVM = new ItemsSearchResultListVM();
            mItemsSearchResultListVM.setSearchContent(mSearchContent);
            mItemsFragment = new NHBRecyclerFragment().setViewModel(mItemsSearchResultListVM);
        }
        switchFragment(mItemsFragment, String.valueOf(TAB_ITEMS));
        if (this.isSearchContentChange) {
            mItemsSearchResultListVM.setSearchContent(mSearchContent);
            mItemsFragment.viewModel.refreshData(false);
        }
    }

    @IdRes
    private int getFragmentLayoutId() {
        return R.id.commonSearchResult_ll_content;
    }

    @Override
    public void onKeywordConfirmed(String content) {
        if (content != null) {
            content = content.trim();
        }
        if (TextUtils.isEmpty(content)) {
            return;
        }
        if (content.equals(mSearchContent)) {
            return;
        } else {
            //搜索条件改变了
            isSearchContentChange = true;
        }
        SearchHistoryUtils.addSearchContent(mContext, content);
        mSearchContent = content;
        viewDataBinding.commonSearchResultCsl.initContent(mSearchContent);
        if (null == mItemsFragment) {
            mItemsSearchResultListVM = new ItemsSearchResultListVM();
            mItemsSearchResultListVM.setSearchContent(mSearchContent);
            mItemsFragment = new NHBRecyclerFragment().setViewModel(mItemsSearchResultListVM);
            switchFragment(mItemsFragment, String.valueOf(TAB_ITEMS));
        } else {
            mItemsSearchResultListVM.setSearchContent(mSearchContent);
            mItemsFragment.viewModel.refreshData(false);
        }
    }

    /**
     * 切换Fragment
     *
     * @param fragment
     * @param fragTag
     */
    private void switchFragment(Fragment fragment, String fragTag) {
        replaceFragmentByTag(getFragmentLayoutId(), fragment, fragTag);
    }

    @Override
    public void onClickBtnBack() {
        finish();
    }
}
