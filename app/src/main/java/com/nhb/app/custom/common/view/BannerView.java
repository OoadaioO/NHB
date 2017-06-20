package com.nhb.app.custom.common.view;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.fast.library.utils.DeviceUtils;
import com.nhb.app.custom.R;
import com.nhb.app.custom.bean.CommonBannerBean;
import com.nhb.app.custom.ui.adapter.BannerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页轮播图
 *
 * @author zhangmao
 * @version 4.0.0
 * @since 2015-02-11
 */
public class BannerView extends RelativeLayout implements OnPageChangeListener, OnTouchListener {

    private static final String TAG = BannerView.class.getSimpleName();

    public static final int BANNER_MAX_COUNT = 1024;

    private static final int TIME_INTERVAL = 5000;// 间隔时间3s
    private Context mContext;
    private ViewPager mViewPager;
    private LinearLayout mLlPoints;//轮播图指示器
    private BannerAdapter mBannerAdapter;
    private List<ImageView> mIVPoints = new ArrayList<ImageView>();
    private boolean mIsAutoPlaying;
    private int mBannerCount;
    private Handler mPagerHandler = new Handler();

    private Runnable mAutoPlayTask = new Runnable() {
        @Override
        public void run() {
            if (mBannerCount > 1) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                mPagerHandler.postDelayed(mAutoPlayTask, TIME_INTERVAL);
            }
        }
    };

    public BannerView(Context context) {
        super(context);
        initialize(context);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }

    private void initialize(Context context) {
        mContext = context;
        View.inflate(context, R.layout.layout_banner_view, this);
        mViewPager = (ViewPager) findViewById(R.id.autoSlide_vp_images);
        mViewPager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, DeviceUtils.getWidth() * 428 / 750));
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                if (android.os.Build.VERSION.SDK_INT >= 11) {
                    float normalizedPosition = Math.abs(Math.abs(position) - 1);
                    page.setAlpha(normalizedPosition);
                }
            }
        });
        mLlPoints = (LinearLayout) findViewById(R.id.autoSlide_ll_points);
    }

    /**
     * 设置Banner的高度
     *
     * @param height
     */
    public void setBannerHeight(int height) {
        mViewPager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, height));
    }

    public void setBanners(Activity activity, List<CommonBannerBean> beans, BannerAdapter.OnActionListener onActionListener) {
        if (mBannerAdapter == null) {
            mBannerAdapter = new BannerAdapter(activity, beans, onActionListener);
            mViewPager.setAdapter(mBannerAdapter);
        } else {
            mBannerAdapter.setData(beans, onActionListener);
            mBannerAdapter.notifyDataSetChanged();
        }

        mBannerCount = beans.size();
        // LogUtil.d(TAG, "mBannerCount = " + mBannerCount);

        if (beans.size() > 1) {
            mViewPager.setOnTouchListener(this);
            mViewPager.setCurrentItem(BANNER_MAX_COUNT / 2 - BANNER_MAX_COUNT / 2 % mBannerCount);//默认在中间，使用户看不到边界
            addPoints(mBannerCount);
            startAutoPlay();
        } else {// 只有一张轮播图时
            mViewPager.setOnTouchListener(null);
            mViewPager.setCurrentItem(0);
            mLlPoints.removeAllViews();
            mIVPoints.clear();
        }
    }

    private void addPoints(int size) {
        mLlPoints.removeAllViews();
        mIVPoints.clear();
        for (int i = 0; i < size; i++) {
            ImageView ivPoint = new ImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 0);// 这里修改间距
            ivPoint.setLayoutParams(params);
            mLlPoints.addView(ivPoint);
            mIVPoints.add(ivPoint);
        }
        updatePoints(0);
    }

    private void updatePoints(int position) {
        for (int i = 0; i < mIVPoints.size(); i++) {
            if (i == position) {// 这里修改图片
                mIVPoints.get(i).setBackgroundResource(R.drawable.banner_dot_selected);
            } else {
                mIVPoints.get(i).setBackgroundResource(R.drawable.banner_dot_normal);
            }
        }
    }

    private void startAutoPlay() {
        if (!mIsAutoPlaying) {
            mIsAutoPlaying = true;
            mPagerHandler.postDelayed(mAutoPlayTask, TIME_INTERVAL);
        }
    }

    private void stopAutoPlay() {
        if (mIsAutoPlaying) {
            mIsAutoPlaying = false;
            mPagerHandler.removeCallbacks(mAutoPlayTask);
        }
    }

    // ====================/OnTouchListener/====================

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                mViewPager.requestDisallowInterceptTouchEvent(true);
                stopAutoPlay();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mViewPager.requestDisallowInterceptTouchEvent(false);
                startAutoPlay();
                break;
            default:
                break;
        }
        return false;
    }

    // ====================/OnPageChangeListener/====================

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                // 开始滑动
                break;
            case ViewPager.SCROLL_STATE_SETTLING:
                // 当松开手时
                // 如果没有其他页显示出来：SCROLL_STATE_DRAGGING --> SCROLL_STATE_IDLE
                // 如果有其他页有显示出来（不管显示了多少），就会触发正在设置页码
                // 页码没有改变时：SCROLL_STATE_DRAGGING --> SCROLL_STATE_SETTLING --> SCROLL_STATE_IDLE
                // 页码有改变时：SCROLL_STATE_DRAGGING --> SCROLL_STATE_SETTLING --> onPageSelected --> SCROLL_STATE_IDLE
                break;
            case ViewPager.SCROLL_STATE_IDLE:
                // 停止滑动
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float offset, int offsetPixels) {
        // LogUtil.d("BannerView", "onPageScrolled:  position=" + position + "  offset=" + offset + "  offsetPixels=" + offsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        updatePoints(position % mBannerCount);
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE) {
            startAutoPlay();
        } else if (visibility == INVISIBLE) {
            stopAutoPlay();
        }
    }

    public void resetBanners(Activity activity, BannerAdapter.OnActionListener onActionListener) {
        if (mBannerAdapter != null && mBannerAdapter.getBeans() != null && mBannerAdapter.getBeans().size() > 0) {
            setBanners(activity, mBannerAdapter.getBeans(), onActionListener);
        }
    }

}
