package com.nhb.app.custom.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fast.library.utils.DeviceUtils;
import com.nhb.app.custom.R;
import com.nhb.app.custom.ui.launch.MainActivity;

/**
 * Created by pengxiaofang on 6/20/16.
 */
public class GuidePageAdapter extends PagerAdapter {

    public static int[] mImages = new int[]{R.drawable.ic_guide_1, R.drawable.ic_guide_2, R.drawable.ic_guide_3};
    public static int[] mTopImages = new int[]{R.drawable.ic_guide_top_1, R.drawable.ic_guide_top_2, R.drawable.ic_guide_top_3};
    public static int[] mBottomImages = new int[]{R.drawable.ic_guide_bottom_1, R.drawable.ic_guide_bottom_2, R.drawable.ic_guide_bottom_3};
    public static int[] mBgColor = new int[]{R.color.c_guide_one, R.color.c_guide_two, R.color.c_guide_three};

    public static int[] mTopParams = new int[]{84, 87, 81};
    public static int[] mBottomParams = new int[]{46, 18, 10};
    public static int[] mBottomParams2 = new int[]{15, 20, 18};

    private Activity mActivity;

    public GuidePageAdapter(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public int getCount() {
        return mImages == null ? 0 : mImages.length;
    }

    /**
     * judge the view
     */
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (container != null) {
            container.removeView((View) object);
        }
    }

    /**
     * add a pic
     */
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_guide_page, null);
        final RelativeLayout imageBg = (RelativeLayout) view.findViewById(R.id.rl_bg);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        ImageView topImageView = (ImageView) view.findViewById(R.id.image_top);
        ImageView bottomImageView = (ImageView) view.findViewById(R.id.image_bottom);

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        lp.setMargins((int) DeviceUtils.dp2Px(28), mTopParams[position], (int) DeviceUtils.dp2Px(27), mBottomParams[position]);
        imageView.setLayoutParams(lp);

        RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams) topImageView.getLayoutParams();
        lp2.setMargins(0, 0, 0, mBottomParams2[position]);
        topImageView.setLayoutParams(lp2);

        imageBg.setBackgroundColor(ContextCompat.getColor(mActivity, mBgColor[position]));
        imageView.setImageResource(mImages[position]);
        topImageView.setImageResource(mTopImages[position]);
        bottomImageView.setImageResource(mBottomImages[position]);

        if (position == mImages.length - 1) {
            //最后一张
            imageBg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.startActivity(new Intent(mActivity, MainActivity.class));
                    mActivity.finish();
                    imageBg.setOnClickListener(null);
                }
            });
        } else {
            imageBg.setOnClickListener(null);
        }
        container.addView(view);
        return view;
    }
}
