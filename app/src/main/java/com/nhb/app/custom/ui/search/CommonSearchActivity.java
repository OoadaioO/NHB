package com.nhb.app.custom.ui.search;

import android.content.Intent;
import android.databinding.Observable;
import android.text.TextUtils;

import com.nhb.app.custom.R;
import com.nhb.app.custom.base.BaseActivity;
import com.nhb.app.custom.common.view.CommonSearchLayout;
import com.nhb.app.custom.constant.Extras;
import com.nhb.app.custom.databinding.ActivityCommonSearchBinding;
import com.nhb.app.custom.utils.SearchHistoryUtils;
import com.nhb.app.custom.viewmodel.CommonSearchVM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pengxiaofang on 5/31/16.
 */
public class CommonSearchActivity extends BaseActivity<CommonSearchVM, ActivityCommonSearchBinding> implements CommonSearchLayout.OnActionListener {

    private String mFrom;
    /**
     * 搜索历史
     */
    private List<String> mSearchHistoryList = new ArrayList<>();

    @Override
    protected CommonSearchVM loadViewModel() {
        return new CommonSearchVM();
    }

    @Override
    protected int loadLayoutId() {
        return R.layout.activity_common_search;
    }

    @Override
    protected void intentWithNormal(Intent intent) {
        super.intentWithNormal(intent);
        mFrom = getIntent().getStringExtra(Extras.FROM);
    }

    @Override
    protected void initialize() {
        // 搜索框
        if (TextUtils.equals(Extras.FROM_HOME, mFrom)) {
            viewDataBinding.commonSearchCsl.setHint(R.string.common_search_hint);
        } else {
            setHintByFrom(mFrom);
        }
        viewDataBinding.commonSearchCsl.setSearchCallback(this);

        viewModel.isItemClick.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                gotoCommonSearchResult(viewModel.isItemClick.get());
            }
        });

        viewModel.clearHistoryShow.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (!viewModel.clearHistoryShow.get()) {
                    //清除搜索历史记录
                    SearchHistoryUtils.clearAll(mContext);
                    mSearchHistoryList.clear();
                }
                viewModel.searchHistoryList.clear();
                viewModel.searchHistoryList.addAll(mSearchHistoryList);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        initSearchHistory();
        updateClearView();
    }

    /**
     * 根据from设置搜索框里面的hint
     */
    private void setHintByFrom(String from) {
        switch (from) {
            case Extras.FROM_ITEMS_HOME:
                viewDataBinding.commonSearchCsl.setHint(R.string.common_search_hint);
                break;
        }
    }

    @Override
    public void onKeywordConfirmed(String content) {
        gotoCommonSearchResult(content);
    }

    @Override
    public void onClickBtnBack() {
        performClickBack();
    }

    @Override
    public void onBackPressed() {
        performClickBack();
    }

    private void gotoCommonSearchResult(String keyword) {
        // 更新搜索历史
        SearchHistoryUtils.addSearchContent(mContext, keyword);
        initSearchHistory();
        updateClearView();
        Intent intent = new Intent(CommonSearchActivity.this, CommonSearchResultActivity.class);
        intent.putExtra(Extras.SEARCH_CONTENT, keyword);
        intent.putExtra(Extras.FROM, mFrom);
        startActivity(intent);
    }

    private void performClickBack() {
        finish();
    }

    private void initSearchHistory() {
        List<String> contents = SearchHistoryUtils.getSearchHistory(mContext);
        if (contents != null) {
            mSearchHistoryList.clear();
            mSearchHistoryList.addAll(contents);
        }
        viewModel.searchHistoryList.clear();
        viewModel.searchHistoryList.addAll(mSearchHistoryList);
    }

    private void updateClearView() {
        if (mSearchHistoryList != null && mSearchHistoryList.size() > 0) {
            viewModel.clearHistoryShow.set(true);
        } else {
            viewModel.clearHistoryShow.set(false);
        }
    }
}
