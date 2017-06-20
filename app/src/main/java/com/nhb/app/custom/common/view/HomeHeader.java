package com.nhb.app.custom.common.view;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.fast.library.utils.DeviceUtils;
import com.nhb.app.custom.R;
import com.nhb.app.custom.bean.CommonBannerBean;
import com.nhb.app.custom.bean.CommonEntranceBean;
import com.nhb.app.custom.common.bean.TemplateSubItem;
import com.nhb.app.custom.databinding.LayoutHomeHeaderBinding;
import com.nhb.app.custom.ui.adapter.BannerAdapter;
import com.nhb.app.custom.viewmodel.HomeHeaderVM;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页--header
 * <p/>
 * 添加固定按钮区，单排横滑模板，固定模板区
 */
public class HomeHeader extends RelativeLayout
        implements BannerAdapter.OnActionListener {

    private LayoutHomeHeaderBinding mViewDataBinding;
    private HomeHeaderVM mHomeHeaderVM;

    private OnActionListener mOnActionListener;

    private List<TemplateSubItem> templateSubItems = new ArrayList<>();

    public HomeHeader(Context context) {
        super(context);
        initContext(context);
    }

    public HomeHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void initContext(Context context) {
        mViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_home_header, this, true);
        mViewDataBinding.homeHeaderBvSlides.setBannerHeight(DeviceUtils.getWidth() * 350 / 750);
        mHomeHeaderVM = new HomeHeaderVM();
        mViewDataBinding.setVariable(com.nhb.app.custom.BR.viewModel, mHomeHeaderVM);
        mHomeHeaderVM.isClickCommonEntrance.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (mOnActionListener != null) {
                    mOnActionListener.onClickButtonItem(mHomeHeaderVM.clickCommonEntranceItem.get());
                }
            }
        });
        mHomeHeaderVM.isClickItem.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (templateSubItems == null || templateSubItems.size() < 3) {
                    return;
                }
                switch (mHomeHeaderVM.clickItem.get()) {
                    case R.id.template3_iv_imageA:
                        if (mOnActionListener != null) {
                            mOnActionListener.onClickStaticTemplateItem(templateSubItems.get(0));
                        }
                        break;
                    case R.id.template3_iv_imageB:
                        if (mOnActionListener != null) {
                            mOnActionListener.onClickStaticTemplateItem(templateSubItems.get(1));
                        }
                        break;
                    case R.id.template3_iv_imageC:
                        if (mOnActionListener != null) {
                            mOnActionListener.onClickStaticTemplateItem(templateSubItems.get(2));
                        }
                        break;
                    default:
                        break;
                }
            }
        });

    }

    /**
     * 设置顶部轮播图
     *
     * @param activity
     * @param beans
     */
    public void setBanners(Activity activity, List<CommonBannerBean> beans) {
        mViewDataBinding.homeHeaderBvSlides.setVisibility(beans != null && beans.size() > 0 ? View.VISIBLE : View.GONE);
        mViewDataBinding.homeHeaderBvSlides.setBanners(activity, beans, this);
    }

    /**
     * 重置顶部轮播图
     *
     * @param activity
     */
    public void resetBanners(Activity activity) {
        if (mViewDataBinding.homeHeaderBvSlides != null) {
            mViewDataBinding.homeHeaderBvSlides.resetBanners(activity, this);
        }
    }

    /**
     * 设置固定按钮区数据
     *
     * @param beans
     */
    public void setButtons(List<CommonEntranceBean> beans) {
        mViewDataBinding.homeHeaderHfgvButtons.setVisibility(beans != null && beans.size() > 0 ? View.VISIBLE : GONE);
        mHomeHeaderVM.commonEntranceBeenList.clear();
        mHomeHeaderVM.commonEntranceBeenList.addAll(beans.size() > 10 ? beans.subList(0, 10) : beans);
    }

    /**
     * 设置固定模板区数据
     *
     * @param beans
     */
    public void setStaticTemplates(List<TemplateSubItem> beans) {
        templateSubItems = beans;
        mViewDataBinding.template3View.template3LlRoot.setVisibility(beans != null && beans.size() >= 3 ? VISIBLE : GONE);
        mHomeHeaderVM.templateSubItems.set(null != beans && beans.size() > 3 ? beans.subList(0, 3) : beans);
    }

    @Override
    public void onClickBanner(int position, CommonBannerBean bean) {
        if (mOnActionListener != null) {
            mOnActionListener.onClickBanner(bean);
        }
    }

    public void setOnActionListener(OnActionListener listener) {
        mOnActionListener = listener;
    }

    public interface OnActionListener {

        void onClickBanner(CommonBannerBean bean);

        void onClickButtonItem(CommonEntranceBean bean);

        void onClickStaticTemplateItem(TemplateSubItem bean);
    }
}
