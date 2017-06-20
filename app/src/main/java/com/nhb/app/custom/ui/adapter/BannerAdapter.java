package com.nhb.app.custom.ui.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fast.library.utils.LogUtils;
import com.nhb.app.custom.R;
import com.nhb.app.custom.bean.CommonBannerBean;
import com.nhb.app.custom.common.view.BannerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 广告轮播图适配器
 */
public class BannerAdapter extends PagerAdapter {

    private static final String TAG = BannerAdapter.class.getSimpleName();

    private Activity mActivity;
    private List<CommonBannerBean> mBeans;
    private List<View> mViews;

    private OnActionListener mOnActionListener;

    public BannerAdapter(Activity activity, List<CommonBannerBean> bean) {
        mBeans = bean;
        mActivity = activity;
        initBanners();
    }

    public BannerAdapter(Activity activity, List<CommonBannerBean> bean, OnActionListener onActionListener) {
        this(activity, bean);
        mOnActionListener = onActionListener;
    }

    public void setData(List<CommonBannerBean> beans) {
        mBeans = beans;
    }

    public void setData(List<CommonBannerBean> beans, BannerAdapter.OnActionListener onActionListener) {
        setData(beans);
        mOnActionListener = onActionListener;
    }

    private void initBanners() {
        if (mBeans == null) {
            return;
        }
        mViews = new ArrayList<>();
        if (mBeans.size() == 2) {// 解决只有2个banner时从右向左滑动右边没有及时加载而出现灰块的问题（妥协的解决方案）
            mViews.add(getImageView(0));
            mViews.add(getImageView(1));
            mViews.add(getImageView(0));
            mViews.add(getImageView(1));
        } else {
            for (int i = 0; i < mBeans.size(); i++) {
                mViews.add(getImageView(i));
            }
        }
    }

    private ImageView getImageView(final int position) {
        final CommonBannerBean banner = mBeans.get(position);
        ImageView imageView = (ImageView) View.inflate(mActivity, R.layout.item_home_banner, null);
        if (!TextUtils.isEmpty(banner.picUrl)) {
            Glide.with(mActivity).load(banner.picUrl).into(imageView);
        }
        imageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                LogUtils.d(TAG, "slide_url = " + banner.picUrl);
                if (TextUtils.isEmpty(banner.picUrl)) {
                    return;
                }
                try {
//                    mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(banner.picUrl)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (mOnActionListener != null) {
                    mOnActionListener.onClickBanner(position, banner);
                }
            }
        });
        return imageView;
    }

    @Override
    public void notifyDataSetChanged() {
        initBanners();
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mBeans.size() > 1 ? BannerView.BANNER_MAX_COUNT : mBeans.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //对ViewPager页号求模取出View列表中要显示的项
        int size = mViews.size();
        position %= size;
        if (position < 0) {
            position += size;
        }
        View view = mViews.get(position);
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp = view.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public boolean isViewFromObject(View v, Object o) {
        return v == o;
    }

    public List<CommonBannerBean> getBeans() {
        return mBeans;
    }

    public interface OnActionListener {
        void onClickBanner(int position, CommonBannerBean banner);
    }

    public void setOnActionListener(OnActionListener listener) {
        mOnActionListener = listener;
    }

}
