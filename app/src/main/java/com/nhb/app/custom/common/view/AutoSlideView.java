package com.nhb.app.custom.common.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nhb.app.custom.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 轮播图
 *
 * @author jingjiejie zhangmao
 * @version 3.8.0
 * @since 2014-09-12
 */
public class AutoSlideView extends RelativeLayout implements OnPageChangeListener {

    private static final int TIME_INTERVAL = 3000;// 间隔时间3s
    private Context mContext;
    private ViewPager mVPImages;
    private List<ImageView> mIVPoints = new ArrayList<ImageView>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                if (mIVPoints.size() != 0) {
                    int index = (mVPImages.getCurrentItem() + 1) % mIVPoints.size();
                    if (index < mIVPoints.size()) {
                        mVPImages.setCurrentItem(index);
                    }
                }
                handler.removeMessages(0);
                handler.sendEmptyMessageDelayed(0, TIME_INTERVAL);
            }
        }
    };

    public AutoSlideView(Context context) {
        super(context);
        initialize(context);
    }

    public AutoSlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public AutoSlideView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }

    private void initialize(Context context) {
        mContext = context;
        View.inflate(context, R.layout.layout_auto_slide, this);
        mVPImages = (ViewPager) findViewById(R.id.autoSlide_vp_images);
        mVPImages.setOnPageChangeListener(this);
//        mVPImages.setOnTouchListener(this);
    }

    public void setAdapter(PagerAdapter adapter) {
        mVPImages.setAdapter(adapter);
        addPoints(adapter.getCount());
//        startScroll();
    }

    private void addPoints(int size) {
        LinearLayout mLLPointsContainer = (LinearLayout) findViewById(R.id.autoSlide_ll_points);
        mLLPointsContainer.removeAllViews();
        mIVPoints.clear();
        for (int i = 0; i < size; i++) {
            ImageView ivPoint = new ImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 0, 5, 0);
            ivPoint.setLayoutParams(params);
            mLLPointsContainer.addView(ivPoint);
            mIVPoints.add(ivPoint);
        }
        updatePoints(0);
    }

    private void updatePoints(int position) {
        for (int i = 0; i < mIVPoints.size(); i++) {
            if (i == position) {
                mIVPoints.get(i).setBackgroundResource(R.drawable.ic_welcome_point_selected);
            } else {
                mIVPoints.get(i).setBackgroundResource(R.drawable.ic_welcome_point_unselected);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onPageScrolled(int position, float offset, int offsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        updatePoints(position);
    }

//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//            case MotionEvent.ACTION_MOVE:
//                mVPImages.requestDisallowInterceptTouchEvent(true);
//                stopScroll();
//                break;
//            case MotionEvent.ACTION_CANCEL:
//            case MotionEvent.ACTION_UP:
//                mVPImages.requestDisallowInterceptTouchEvent(false);
//                startScroll();
//                v.performClick();
//                break;
//            default:
//                break;
//        }
//        return false;
//    }

    public void startScroll() {
        handler.sendEmptyMessageDelayed(0, TIME_INTERVAL);
    }

    public void stopScroll() {
        handler.removeMessages(0);
    }

    public void setIndicatorVisible(boolean isVisible) {
        findViewById(R.id.autoSlide_ll_points).setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

}