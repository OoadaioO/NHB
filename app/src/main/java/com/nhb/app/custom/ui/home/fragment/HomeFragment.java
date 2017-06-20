package com.nhb.app.custom.ui.home.fragment;

import android.app.Activity;
import android.content.Intent;
import android.databinding.Observable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import com.fast.library.utils.NetUtils;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.nhb.app.custom.R;
import com.nhb.app.custom.base.BaseFragment;
import com.nhb.app.custom.bean.CommonBannerBean;
import com.nhb.app.custom.bean.CommonEntranceBean;
import com.nhb.app.custom.bean.HomeDataBean;
import com.nhb.app.custom.common.bean.TemplateSubItem;
import com.nhb.app.custom.common.view.HomeHeader;
import com.nhb.app.custom.common.view.LoadingStatusView;
import com.nhb.app.custom.common.view.MatchListPullToRefreshScrollView;
import com.nhb.app.custom.common.view.MatchListScrollView;
import com.nhb.app.custom.constant.Constants;
import com.nhb.app.custom.constant.EventCenter;
import com.nhb.app.custom.databinding.FragmentHomeBinding;
import com.nhb.app.custom.location.LocationServiceutils;
import com.nhb.app.custom.ui.launch.GuidePageActivity;
import com.nhb.app.custom.ui.personal.PersonalSelectAreaActivity;
import com.nhb.app.custom.ui.personal.WebViewActivity;
import com.nhb.app.custom.utils.SpCustom;
import com.nhb.app.custom.utils.helper.RouteHelper;
import com.nhb.app.custom.viewmodel.HomeFragmentVM;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by pxfile on 6/20/16.
 */
public class HomeFragment extends BaseFragment<HomeFragmentVM, FragmentHomeBinding> implements HomeHeader.OnActionListener, LoadingStatusView.LoadingCallback, ViewPager.OnPageChangeListener, OnTabSelectListener, MatchListPullToRefreshScrollView.ScrollListener, PullToRefreshBase.OnRefreshListener2<ScrollView>, BaseHomeListFragment.OnNestedListViewScrollListener {

    private ViewTreeObserver.OnPreDrawListener scrollViewOnPreDrawListener;
    private int mCacheViewPagerHeight = 0;
    private final float DEFAULT_TITLE_BAR_ALPHA = 0.3f;
    private boolean isRefreshMain = false;

    private HomeHeader mHeaderView;
    private View mBannerView;

    private HomeListPagerAdapter mPagerAdapter;

    /**
     * 记录当前的 tab_type
     */
    private String mCurrentTabType;
    private int mStartPage;

    @Override
    protected int loadLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected HomeFragmentVM loadViewModel() {
        return new HomeFragmentVM();
    }

    @Override
    protected void initialize() {
        EventBus.getDefault().register(this);

        viewDataBinding.homeSv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        viewDataBinding.homeSv.setScrollListener(this);
        viewDataBinding.homeSv.setOnRefreshListener(this);

        // loading
        viewDataBinding.commonListLoading.setVisibility(View.VISIBLE);
        viewDataBinding.commonListLoading.setCallback(this);

        mHeaderView = new HomeHeader(mContext);
        mHeaderView.setOnActionListener(this);

        viewDataBinding.homeHeader.addView(mHeaderView);

        viewDataBinding.homeVp.setOnPageChangeListener(this);
        viewDataBinding.homeStlTabs.setOnTabSelectListener(this);

        mBannerView = mHeaderView.findViewById(R.id.homeHeader_banner);

        viewDataBinding.homeTitle.rlRoot.setAlpha(DEFAULT_TITLE_BAR_ALPHA);

        // scrollview OnPreDrawListener
        scrollViewOnPreDrawListener = new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                // 这里不要移除监听,小米手机上Scrollview高度测量值会变
                setViewPagerHeight();
                return true;
            }
        };
        viewDataBinding.homeSv.getViewTreeObserver().addOnPreDrawListener(scrollViewOnPreDrawListener);

        viewModel.dataObservable.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                viewDataBinding.homeSv.onRefreshComplete();
                dismissLD();
                // 请求完毕就可以切换Tab了
                viewDataBinding.homeStlTabs.setEnabled(true);
                viewDataBinding.homeVp.setEnabled(true);
                handleInitData(viewModel.homeBean.get());
            }
        });

        viewModel.fetchRemoteData();
    }

    /**
     * 设置ViewPager的高度（ScrollView高度 - tabLayout高度 - titleBar高度）
     */
    private void setViewPagerHeight() {
        int viewPagerHeight = viewDataBinding.homeSv.getHeight() - viewDataBinding.homeStlTabs.getHeight() - viewDataBinding.homeTitle.rlRoot.getHeight();
        if (viewPagerHeight != mCacheViewPagerHeight) {
            viewDataBinding.homeVp.getLayoutParams().height = viewPagerHeight;
            mCacheViewPagerHeight = viewPagerHeight;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mHeaderView != null) {
            mHeaderView.resetBanners(getActivity());
        }
        if (null != viewDataBinding.homeVp) {
            viewDataBinding.homeVp.setOnPageChangeListener(this);
        }
        if (isRefreshMain){
            isRefreshMain = false;
            viewModel.fetchRemoteData();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != viewDataBinding.homeVp) {
            viewDataBinding.homeVp.removeOnPageChangeListener(this);
        }
        try {
            // remove the scrollview OnPreDraw listener
            if (null != scrollViewOnPreDrawListener && null != viewDataBinding.homeSv) {
                viewDataBinding.homeSv.getViewTreeObserver().removeOnPreDrawListener(scrollViewOnPreDrawListener);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO: 9/3/16 Evenbus 
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void refreshHomeData(EventCenter<String> center) {
        if (Constants.EventType.LOCATION_AREA_CHANGE.equals(center.type)){
            String locationName = TextUtils.isEmpty(SpCustom.get().readString(Constants.LOCATION_AREA_NAME)) ? LocationServiceutils.getInstance().locationName : SpCustom.get().readString(Constants.LOCATION_AREA_NAME);
            viewDataBinding.homeTitle.titleBarHomeTvCity.setText(locationName);
            isRefreshMain = true;
        }
    }

    @Override
    public void onClickBanner(CommonBannerBean bean) {
        if (!TextUtils.isEmpty(bean.bannerData) && !TextUtils.isEmpty(bean.type)) {
            switch (bean.type){
                case Constants.BannerType.SHOP_ID:
                    RouteHelper.getInstance().startItemDetail(getActivity(),bean.bannerData);
                    break;
                case Constants.BannerType.URL:
                    startActivity(new Intent(mContext, WebViewActivity.class).putExtra(Constants.WEB_URL, bean.bannerData));
                    break;
            }
        }
    }

    /**
     * 分类
     *
     * @param bean
     */
    @Override
    public void onClickButtonItem(CommonEntranceBean bean) {
//        startActivity(new Intent(mContext, ItemsListActivity.class).putExtra(Extras.SHOP_CATEGORY_ID, bean.shopCategoryId));
    }

    /**
     * 热门
     *
     * @param bean
     */
    @Override
    public void onClickStaticTemplateItem(TemplateSubItem bean) {
        RouteHelper.getInstance().startItemDetail((Activity) mContext, bean.itemId);
    }

    @Override
    public void clickReLoading() {
        if (TextUtils.equals(getString(R.string.please_select_city), viewDataBinding.commonListLoading.getBtnText())) {
            startActivity(new Intent(mContext, PersonalSelectAreaActivity.class));
        } else {
            viewModel.fetchRemoteData(true);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    // ==============================/ViewPager.OnPageChangeListener/==============================
    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
//        if (mTabList != null && position < mTabList.size()) {
//            mCurrentTabType = mTabList.get(position).tab_type;
//        }
//        mCurrentTabType = "items";
//        BaseHomeListFragment fragment = (BaseHomeListFragment) mPagerAdapter.getItem(state);
//        if (fragment.isEmpty()) {// 没有数据时请求数据
//            mStartNum = 0;
//            toGetTabData(position);
//        }
//        fragment.updateScrollStatus();
    }


    @Override
    public void onTabSelect(int position) {

    }

    @Override
    public void onTabReselect(int position) {

    }

    @OnClick({R.id.titleBarHome_tv_city, R.id.titleBarHome_ll_Search})
    public void onBtnClick(View view) {
        switch (view.getId()) {
            case R.id.titleBarHome_tv_city:
                startActivity(new Intent(mContext, GuidePageActivity.class));
                break;
        }
    }

    private void handleInitData(final HomeDataBean homeDataBean) {
        if (homeDataBean == null) {
            if (!NetUtils.isNetConnected()) {
                viewDataBinding.commonListLoading.setBtnText(R.string.loading_again);
                viewDataBinding.commonListLoading.loadFailed();
                return;
            } else if (TextUtils.isEmpty(SpCustom.get().readString(Constants.LOCATION_AREA_NAME))) {
                viewDataBinding.commonListLoading.setBtnText(R.string.please_select_city);
                viewDataBinding.commonListLoading.loadFailed(getString(R.string.location_area_failed));
                return;
            }
        } else if (TextUtils.equals("false", homeDataBean.isOpen)) {
            viewDataBinding.commonListLoading.setBtnText(R.string.please_select_city);
            viewDataBinding.commonListLoading.loadFailed(getString(R.string.location_failed));
            return;
        }
        // header
        mHeaderView.setBanners(getActivity(), homeDataBean.bannerList);
//        mHeaderView.setButtons(homeDataBean.featureList);
        mHeaderView.setStaticTemplates(homeDataBean.featureList);
        //处理猜你喜欢
        List<Fragment> fragmentList = new ArrayList<>();
        HomeItemsFragment homeHotFragment = new HomeItemsFragment();
        homeHotFragment.setTabType("items");
        homeHotFragment.setOnNestedListViewScrollListener(this);
        fragmentList.add(homeHotFragment);

        mPagerAdapter = new HomeListPagerAdapter(getChildFragmentManager(), fragmentList, new String[]{getString(R.string.home_hot_area)});
        viewDataBinding.homeVp.setAdapter(mPagerAdapter);
        viewDataBinding.homeStlTabs.setViewPager(viewDataBinding.homeVp);
        handleTabListData(0, homeDataBean);
        viewDataBinding.commonListLoading.loadSuccess();
    }

    /**
     * 处理列表数据
     *
     * @param bean
     */
    private void handleTabListData(int tab, HomeDataBean bean) {
        HomeItemsFragment fragment = (HomeItemsFragment) mPagerAdapter.getItem(tab);
        fragment.handleData(bean.hotList, true);
    }

    @Override
    public void onScrollChanged(MatchListPullToRefreshScrollView scrollView, int x, int y, int oldx, int oldy) {

    }

    @Override
    public void onNestedScrollChanged(MatchListScrollView scrollView, int x, int y, int oldx, int oldy) {
        // 实现背景和title透明
        int viewHeight = mBannerView.getHeight();
        float alpha = (3f *(float) y / viewHeight) + DEFAULT_TITLE_BAR_ALPHA;
        viewDataBinding.homeTitle.rlRoot.setAlpha(alpha);
//        float alpha = 1.5f * (float) y / viewHeight;
//        viewDataBinding.homeTitle.rlRoot.setAlpha(alpha);
//        viewDataBinding.homeTitle.rlRoot.setBackgroundColor(ColorUtils.getColorWithAlpha(alpha, ContextCompat.getColor(getContext(), R.color.white)));
    }

    // ==============================/PullToRefreshBase.OnRefreshListener2<ScrollView>/==============================

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        mStartPage = 0;
        viewModel.fetchRemoteData(true);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        int tabIndex = viewDataBinding.homeStlTabs.getCurrentTab();
        mStartPage = ((BaseHomeListFragment) mPagerAdapter.getItem(tabIndex)).getStartNum();
        viewModel.fetchRemoteData(true);
    }

    // ==============================/BaseHomeListFragment.OnNestedListViewScrollListener/==============================

    @Override
    public void onNestedListViewScrolledToTop(boolean scrolledToTop) {
        viewDataBinding.homeSv.setListViewScrollTop(scrolledToTop);
    }

    @Override
    public void onNestedListViewScrolledToBottom(boolean scrolledToBottom) {
        viewDataBinding.homeSv.setListViewScrollBottom(scrolledToBottom);
    }

    @Override
    public void onClickBackTop() {
        viewDataBinding.homeSv.getRefreshableView().scrollTo(0, 0);
    }

    private class HomeListPagerAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> mFragments;
        private String[] mTitles;

        public HomeListPagerAdapter(FragmentManager manager, List<Fragment> fragments, String[] titles) {
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

    public String getCurrentTabType() {
        return mCurrentTabType;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
