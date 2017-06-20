package com.nhb.app.custom.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fast.library.utils.DeviceUtils;
import com.fast.library.utils.LogUtils;
import com.nhb.app.custom.R;
import com.nhb.app.custom.common.bean.TemplateItem;
import com.nhb.app.custom.common.bean.TemplateSubItem;

import java.util.List;

/**
 * 固定模板区
 */
public class StaticTemplateLayout extends LinearLayout {

    private static final String TAG = StaticTemplateLayout.class.getSimpleName();

    private static final String POSITION_A = "a";
    private static final String POSITION_B = "b";
    private static final String POSITION_C = "c";
    private static final String POSITION_D = "d";
    private static final String POSITION_E = "e";
    private static final String POSITION_F = "f";
    private static final String POSITION_G = "g";
    private static final String POSITION_H = "h";
    private static final String POSITION_I = "i";

    private int mScreenW;
    private boolean isDividerVisible;

    private Context mContext;
    private List<TemplateItem> mBeans;

    private OnActionListener mOnActionListener;

    public StaticTemplateLayout(Context context) {
        super(context);
        init(context, null);
    }

    public StaticTemplateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        setOrientation(VERTICAL);
        mScreenW = DeviceUtils.getWidth();

        // 属性
        if (attrs != null) {
            TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.StaticTemplateLayout);

            try {
                isDividerVisible = typedArray.getBoolean(R.styleable.StaticTemplateLayout_divider_visible, false);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                typedArray.recycle();
            }
        } else {
            LogUtils.d(TAG, "attrs is null");
        }
    }

    public void setData(List<TemplateItem> beans) {
        mBeans = beans;
        notifyDataChanged();
    }

    public void notifyDataChanged() {
        removeAllViews();
        int count = mBeans == null ? 0 : mBeans.size();
        for (int i = 0; i < count; i++) {
            View itemView = null;
            TemplateItem item = mBeans.get(i);
            switch (item.type) {
                case TemplateItem.T1:
                    itemView = createTemplate1View(i, item);
                    break;
                case TemplateItem.T2:
                    itemView = createTemplate2View(i, item);
                    break;
                case TemplateItem.T3:
                    itemView = createTemplate3View(i, item);
                    break;
                case TemplateItem.T4A:
                    itemView = createTemplate4AView(i, item);
                    break;
                case TemplateItem.T4B:
                    itemView = createTemplate4BView(i, item);
                    break;
                case TemplateItem.T5:
                    itemView = createTemplate5View(i, item);
                    break;
                case TemplateItem.T6:
                    itemView = createTemplate6View(i, item);
                    break;
                case TemplateItem.T7:
                    itemView = createTemplate7View(i, item);
                    break;
                case TemplateItem.T9A:
                    itemView = createTemplate9AView(i, item);
                    break;
                case TemplateItem.T9B:
                    itemView = createTemplate9BView(i, item);
                    break;
            }
            if (itemView != null) {
                addView(itemView);
            }
        }
    }

    private View createTemplate1View(int position, TemplateItem bean) {
        View view = View.inflate(mContext, R.layout.template_1, null);

        TextView tv_title = (TextView) view.findViewById(R.id.template1_tv_title);
        setTitle(tv_title, bean.title);

        ImageView iv_image = (ImageView) view.findViewById(R.id.template1_iv_imageA);
        setImage(position, iv_image, bean.id, POSITION_A, bean.a);

        // 根据屏幕宽度设置View高度
        setViewHeight(iv_image, (int) (mScreenW * 300F / 750F));

        return view;
    }

    private View createTemplate2View(int position, TemplateItem bean) {
        View view = View.inflate(mContext, R.layout.template_2, null);

        // 控制分割线显示隐藏
        setViewVisible(view.findViewById(R.id.template2_dividerVCenter), isDividerVisible);

        TextView tv_title = (TextView) view.findViewById(R.id.template2_tv_title);
        setTitle(tv_title, bean.title);

        ImageView iv_imageA = (ImageView) view.findViewById(R.id.template2_iv_imageA);
        ImageView iv_imageB = (ImageView) view.findViewById(R.id.template2_iv_imageB);
        setImage(position, iv_imageA, bean.id, POSITION_A, bean.a);
        setImage(position, iv_imageB, bean.id, POSITION_B, bean.b);

        // 根据屏幕宽度设置View高度
        setViewHeight(view.findViewById(R.id.template2_ll_images), (int) (mScreenW * 300F / 750F));

        return view;
    }

    private View createTemplate3View(int position, TemplateItem bean) {
        View view = View.inflate(mContext, R.layout.template_3, null);

        // 控制分割线显示隐藏
        setViewVisible(view.findViewById(R.id.template3_dividerVCenter), isDividerVisible);
        setViewVisible(view.findViewById(R.id.template3_dividerHRight), isDividerVisible);

        TextView tv_title = (TextView) view.findViewById(R.id.template3_tv_title);
        setTitle(tv_title, bean.title);

        ImageView iv_imageA = (ImageView) view.findViewById(R.id.template3_iv_imageA);
        TextView tv_seckillName = (TextView) view.findViewById(R.id.template3ImageA_tv_seckillName);
        TextView tv_seckillTip = (TextView) view.findViewById(R.id.template3ImageA_tv_seckillTip);
        CountDownView cv_seckillTime = (CountDownView) view.findViewById(R.id.template3ImageA_cv_seckillTime);
        ImageView iv_imageB = (ImageView) view.findViewById(R.id.template3_iv_imageB);
        ImageView iv_imageC = (ImageView) view.findViewById(R.id.template3_iv_imageC);

        setImageAndSecKill(position, iv_imageA, tv_seckillName, tv_seckillTip, cv_seckillTime, bean.id, bean.a);
        setImage(position, iv_imageB, bean.id, POSITION_B, bean.b);
        setImage(position, iv_imageC, bean.id, POSITION_C, bean.c);

        // 根据屏幕宽度设置View高度
        setViewHeight(view.findViewById(R.id.template3_ll_images), mScreenW / 2);

        return view;
    }

    private View createTemplate4AView(int position, TemplateItem bean) {
        View view = View.inflate(mContext, R.layout.template_4a, null);

        // 控制分割线显示隐藏
        setViewVisible(view.findViewById(R.id.template4A_dividerVLeft), isDividerVisible);
        setViewVisible(view.findViewById(R.id.template4A_dividerVCenter), isDividerVisible);
        setViewVisible(view.findViewById(R.id.template4A_dividerVRight), isDividerVisible);

        TextView tv_title = (TextView) view.findViewById(R.id.template4A_tv_title);
        setTitle(tv_title, bean.title);

        ImageView iv_imageA = (ImageView) view.findViewById(R.id.template4A_iv_imageA);
        ImageView iv_imageB = (ImageView) view.findViewById(R.id.template4A_iv_imageB);
        ImageView iv_imageC = (ImageView) view.findViewById(R.id.template4A_iv_imageC);
        ImageView iv_imageD = (ImageView) view.findViewById(R.id.template4A_iv_imageD);
        setImage(position, iv_imageA, bean.id, POSITION_A, bean.a);
        setImage(position, iv_imageB, bean.id, POSITION_B, bean.b);
        setImage(position, iv_imageC, bean.id, POSITION_C, bean.c);
        setImage(position, iv_imageD, bean.id, POSITION_D, bean.d);

        // 根据屏幕宽度设置View高度
        setViewHeight(view.findViewById(R.id.template4A_ll_images), (int) (mScreenW * 200F / 750F));

        return view;
    }

    private View createTemplate4BView(int positon, TemplateItem bean) {
        View view = View.inflate(mContext, R.layout.template_4b, null);

        //控制分割线显示隐藏
        setViewVisible(view.findViewById(R.id.template4B_dividerHTop), isDividerVisible);
        setViewVisible(view.findViewById(R.id.template4B_dividerVLeft), isDividerVisible);
        setViewVisible(view.findViewById(R.id.template4B_dividerVRight), isDividerVisible);

        TextView tv_title = (TextView) view.findViewById(R.id.template4B_tv_title);
        setTitle(tv_title, bean.title);

        ImageView iv_imageA = (ImageView) view.findViewById(R.id.template4B_iv_imageA);
        ImageView iv_imageB = (ImageView) view.findViewById(R.id.template4B_iv_imageB);
        ImageView iv_imageC = (ImageView) view.findViewById(R.id.template4B_iv_imageC);
        ImageView iv_imageD = (ImageView) view.findViewById(R.id.template4B_iv_imageD);
        setImage(positon, iv_imageA, bean.id, POSITION_A, bean.a);
        setImage(positon, iv_imageB, bean.id, POSITION_B, bean.b);
        setImage(positon, iv_imageC, bean.id, POSITION_C, bean.c);
        setImage(positon, iv_imageD, bean.id, POSITION_D, bean.d);

        //根据屏幕宽度设置View高度
        setViewHeight(view.findViewById(R.id.template4B_ll_imagesABCD), (int) (mScreenW * 480F / 750F));
        setViewHeight(view.findViewById(R.id.template4B_iv_imageA), (int) (mScreenW * 320F / 480F));
        setViewHeight(view.findViewById(R.id.template4B_iv_imageB), (int) (mScreenW * 240F / 430F));
        setViewHeight(view.findViewById(R.id.template4B_ll_imagesCD), (int) (mScreenW * 240F / 430F));

        return view;
    }

    private View createTemplate5View(int position, TemplateItem bean) {
        View view = View.inflate(mContext, R.layout.template_5, null);

        // 控制分割线显示隐藏
//        setViewVisible(view.findViewById(R.id.template5_dividerHTop), isDividerVisible);
//        setViewVisible(view.findViewById(R.id.template5_dividerHBottom), isDividerVisible);
//        setViewVisible(view.findViewById(R.id.template5_dividerVCenter), isDividerVisible);
//        setViewVisible(view.findViewById(R.id.template5_dividerVBottom), isDividerVisible);

        TextView tv_title = (TextView) view.findViewById(R.id.template5_tv_title);
        setTitle(tv_title, bean.title);

        ImageView iv_imageA = (ImageView) view.findViewById(R.id.template5_iv_imageA);
        ImageView iv_imageB = (ImageView) view.findViewById(R.id.template5_iv_imageB);
        ImageView iv_imageC = (ImageView) view.findViewById(R.id.template5_iv_imageC);
        ImageView iv_imageD = (ImageView) view.findViewById(R.id.template5_iv_imageD);
        ImageView iv_imageE = (ImageView) view.findViewById(R.id.template5_iv_imageE);
        setImage(position, iv_imageA, bean.id, POSITION_A, bean.a);
        setImage(position, iv_imageB, bean.id, POSITION_B, bean.b);
        setImage(position, iv_imageC, bean.id, POSITION_C, bean.c);
        setImage(position, iv_imageD, bean.id, POSITION_D, bean.d);
        setImage(position, iv_imageE, bean.id, POSITION_E, bean.e);

        // 根据屏幕宽度设置View高度
        int height = (int) (mScreenW * 200F / 750F);
//        setViewHeight(iv_imageA, height);
//        setViewHeight(view.findViewById(R.id.template5_ll_imagesAB), height);
//        setViewHeight(view.findViewById(R.id.template5_ll_imagesCDE), height);
        setViewHeight(view.findViewById(R.id.template5_ll_images), (int) (mScreenW * 300F / 750F));
        return view;
    }

    private View createTemplate6View(int position, TemplateItem bean) {
        View view = View.inflate(mContext, R.layout.template_6, null);

        //控制分割线显示隐藏
        setViewVisible(view.findViewById(R.id.template6_dividerVTopCenter), isDividerVisible);
        setViewVisible(view.findViewById(R.id.template6_dividerHCenter), isDividerVisible);
        setViewVisible(view.findViewById(R.id.template6_dividerVBottomLeft), isDividerVisible);
        setViewVisible(view.findViewById(R.id.template6_dividerVBottomCenter), isDividerVisible);
        setViewVisible(view.findViewById(R.id.template6_dividerVBottomRight), isDividerVisible);

        TextView tv_title = (TextView) view.findViewById(R.id.template6_tv_title);
        setTitle(tv_title, bean.title);

        ImageView iv_imageA = (ImageView) view.findViewById(R.id.template6_iv_imageA);
        ImageView iv_imageB = (ImageView) view.findViewById(R.id.template6_iv_imageB);
        ImageView iv_imageC = (ImageView) view.findViewById(R.id.template6_iv_imageC);
        ImageView iv_imageD = (ImageView) view.findViewById(R.id.template6_iv_imageD);
        ImageView iv_imageE = (ImageView) view.findViewById(R.id.template6_iv_imageE);
        ImageView iv_imageF = (ImageView) view.findViewById(R.id.template6_iv_imageF);
        setImage(position, iv_imageA, bean.id, POSITION_A, bean.a);
        setImage(position, iv_imageB, bean.id, POSITION_B, bean.b);
        setImage(position, iv_imageC, bean.id, POSITION_C, bean.c);
        setImage(position, iv_imageD, bean.id, POSITION_D, bean.d);
        setImage(position, iv_imageE, bean.id, POSITION_E, bean.e);
        setImage(position, iv_imageF, bean.id, POSITION_F, bean.f);

        // 根据屏幕宽度设置View高度
        setViewHeight(view.findViewById(R.id.template6_ll_imagesAB), (int) (mScreenW * 180F / 750F));
        setViewHeight(view.findViewById(R.id.template6_ll_imagesCDEF), (int) (mScreenW * 228F / 750F));

        return view;
    }

    private View createTemplate7View(int position, TemplateItem bean) {
        View view = View.inflate(mContext, R.layout.template_7, null);

        // 控制分割线显示隐藏
        setViewVisible(view.findViewById(R.id.template7_dividerVTopCenter), isDividerVisible);
        setViewVisible(view.findViewById(R.id.template7_dividerHTopRight), isDividerVisible);
        setViewVisible(view.findViewById(R.id.template7_dividerVBottomLeft), isDividerVisible);
        setViewVisible(view.findViewById(R.id.template7_dividerVBottomCenter), isDividerVisible);
        setViewVisible(view.findViewById(R.id.template7_dividerVBottomRight), isDividerVisible);

        TextView tv_title = (TextView) view.findViewById(R.id.template7_tv_title);
        setTitle(tv_title, bean.title);

        ImageView iv_imageA = (ImageView) view.findViewById(R.id.template7_iv_imageA);
        TextView tv_seckillName = (TextView) view.findViewById(R.id.template7ImageA_tv_seckillName);
        TextView tv_seckillTip = (TextView) view.findViewById(R.id.template7ImageA_tv_seckillTip);
        CountDownView cv_seckillTime = (CountDownView) view.findViewById(R.id.template7ImageA_cv_seckillTime);
        ImageView iv_imageB = (ImageView) view.findViewById(R.id.template7_iv_imageB);
        ImageView iv_imageC = (ImageView) view.findViewById(R.id.template7_iv_imageC);
        ImageView iv_imageD = (ImageView) view.findViewById(R.id.template7_iv_imageD);
        ImageView iv_imageE = (ImageView) view.findViewById(R.id.template7_iv_imageE);
        ImageView iv_imageF = (ImageView) view.findViewById(R.id.template7_iv_imageF);
        ImageView iv_imageG = (ImageView) view.findViewById(R.id.template7_iv_imageG);

        setImageAndSecKill(position, iv_imageA, tv_seckillName, tv_seckillTip, cv_seckillTime, bean.id, bean.a);
        setImage(position, iv_imageB, bean.id, POSITION_B, bean.b);
        setImage(position, iv_imageC, bean.id, POSITION_C, bean.c);
        setImage(position, iv_imageD, bean.id, POSITION_D, bean.d);
        setImage(position, iv_imageE, bean.id, POSITION_E, bean.e);
        setImage(position, iv_imageF, bean.id, POSITION_F, bean.f);
        setImage(position, iv_imageG, bean.id, POSITION_G, bean.g);

        // 根据屏幕宽度设置View高度
        setViewHeight(view.findViewById(R.id.template7_ll_imagesABC), mScreenW / 2);
        setViewHeight(view.findViewById(R.id.template7_ll_imagesDEFG), (int) (mScreenW * 200F / 750F));

        return view;
    }

    private View createTemplate9AView(int position, TemplateItem bean) {
        View view = View.inflate(mContext, R.layout.template_9a, null);

        // 控制分割线显示隐藏
        setViewVisible(view.findViewById(R.id.template9A_dividerHTop), isDividerVisible);
        setViewVisible(view.findViewById(R.id.template9A_dividerHBottom), isDividerVisible);
        setViewVisible(view.findViewById(R.id.template9A_dividerVTopLeft), isDividerVisible);
        setViewVisible(view.findViewById(R.id.template9A_dividerVTopRight), isDividerVisible);
        setViewVisible(view.findViewById(R.id.template9A_dividerVCenterLeft), isDividerVisible);
        setViewVisible(view.findViewById(R.id.template9A_dividerVCenterRight), isDividerVisible);
        setViewVisible(view.findViewById(R.id.template9A_dividerVBottomLeft), isDividerVisible);
        setViewVisible(view.findViewById(R.id.template9A_dividerVBottomRight), isDividerVisible);

        TextView tv_title = (TextView) view.findViewById(R.id.template9A_tv_title);
        setTitle(tv_title, bean.title);

        ImageView iv_imageA = (ImageView) view.findViewById(R.id.template9A_iv_imageA);
        ImageView iv_imageB = (ImageView) view.findViewById(R.id.template9A_iv_imageB);
        ImageView iv_imageC = (ImageView) view.findViewById(R.id.template9A_iv_imageC);
        ImageView iv_imageD = (ImageView) view.findViewById(R.id.template9A_iv_imageD);
        ImageView iv_imageE = (ImageView) view.findViewById(R.id.template9A_iv_imageE);
        ImageView iv_imageF = (ImageView) view.findViewById(R.id.template9A_iv_imageF);
        ImageView iv_imageG = (ImageView) view.findViewById(R.id.template9A_iv_imageG);
        ImageView iv_imageH = (ImageView) view.findViewById(R.id.template9A_iv_imageH);
        ImageView iv_imageI = (ImageView) view.findViewById(R.id.template9A_iv_imageI);
        setImage(position, iv_imageA, bean.id, POSITION_A, bean.a);
        setImage(position, iv_imageB, bean.id, POSITION_B, bean.b);
        setImage(position, iv_imageC, bean.id, POSITION_C, bean.c);
        setImage(position, iv_imageD, bean.id, POSITION_D, bean.d);
        setImage(position, iv_imageE, bean.id, POSITION_E, bean.e);
        setImage(position, iv_imageF, bean.id, POSITION_F, bean.f);
        setImage(position, iv_imageG, bean.id, POSITION_G, bean.g);
        setImage(position, iv_imageH, bean.id, POSITION_H, bean.h);
        setImage(position, iv_imageI, bean.id, POSITION_I, bean.i);

        // 根据屏幕宽度设置View高度
        int height = mScreenW / 3;
        setViewHeight(view.findViewById(R.id.template9A_ll_imagesABC), height);
        setViewHeight(view.findViewById(R.id.template9A_ll_imagesDEF), height);
        setViewHeight(view.findViewById(R.id.template9A_ll_imagesGHI), height);

        return view;
    }

    private View createTemplate9BView(int position, TemplateItem bean) {
        View view = View.inflate(mContext, R.layout.template_9b, null);

        // 控制分割线显示隐藏
        setViewVisible(view.findViewById(R.id.template9B_dividerHTop), isDividerVisible);
        setViewVisible(view.findViewById(R.id.template9B_dividerHBottom), isDividerVisible);
        setViewVisible(view.findViewById(R.id.template9B_dividerVCenterLeft), isDividerVisible);
        setViewVisible(view.findViewById(R.id.template9B_dividerVCenterCenter), isDividerVisible);
        setViewVisible(view.findViewById(R.id.template9B_dividerVCenterRight), isDividerVisible);
        setViewVisible(view.findViewById(R.id.template9B_dividerVBottomLeft), isDividerVisible);
        setViewVisible(view.findViewById(R.id.template9B_dividerVBottomCenter), isDividerVisible);
        setViewVisible(view.findViewById(R.id.template9B_dividerVBottomRight), isDividerVisible);

        TextView tv_title = (TextView) view.findViewById(R.id.template9B_tv_title);
        setTitle(tv_title, bean.title);

        ImageView iv_imageA = (ImageView) view.findViewById(R.id.template9B_iv_imageA);
        ImageView iv_imageB = (ImageView) view.findViewById(R.id.template9B_iv_imageB);
        ImageView iv_imageC = (ImageView) view.findViewById(R.id.template9B_iv_imageC);
        ImageView iv_imageD = (ImageView) view.findViewById(R.id.template9B_iv_imageD);
        ImageView iv_imageE = (ImageView) view.findViewById(R.id.template9B_iv_imageE);
        ImageView iv_imageF = (ImageView) view.findViewById(R.id.template9B_iv_imageF);
        ImageView iv_imageG = (ImageView) view.findViewById(R.id.template9B_iv_imageG);
        ImageView iv_imageH = (ImageView) view.findViewById(R.id.template9B_iv_imageH);
        ImageView iv_imageI = (ImageView) view.findViewById(R.id.template9B_iv_imageI);
        setImage(position, iv_imageA, bean.id, POSITION_A, bean.a);
        setImage(position, iv_imageB, bean.id, POSITION_B, bean.b);
        setImage(position, iv_imageC, bean.id, POSITION_C, bean.c);
        setImage(position, iv_imageD, bean.id, POSITION_D, bean.d);
        setImage(position, iv_imageE, bean.id, POSITION_E, bean.e);
        setImage(position, iv_imageF, bean.id, POSITION_F, bean.f);
        setImage(position, iv_imageG, bean.id, POSITION_G, bean.g);
        setImage(position, iv_imageH, bean.id, POSITION_H, bean.h);
        setImage(position, iv_imageI, bean.id, POSITION_I, bean.i);

        // 根据屏幕宽度设置View高度
        int height = (int) (mScreenW * 200F / 750F);
        setViewHeight(iv_imageA, height);
        setViewHeight(view.findViewById(R.id.template9B_ll_imagesBCDE), height);
        setViewHeight(view.findViewById(R.id.template9B_ll_imagesFGHI), height);

        return view;
    }

    /**
     * 设置View高度
     *
     * @param view
     * @param height
     */
    private void setViewHeight(View view, int height) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.height = height;
        view.setLayoutParams(lp);
    }

    /**
     * 设置View显隐
     *
     * @param view
     * @param visible
     */
    private void setViewVisible(View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private void setTitle(TextView tv_title, String title) {
        if (TextUtils.isEmpty(title)) {
            tv_title.setVisibility(View.GONE);
        } else {
            tv_title.setVisibility(View.VISIBLE);
            tv_title.setText(title);
        }
    }

    private void setImage(final int position, ImageView iv_image, final int template_id, final String grid, final TemplateSubItem subItem) {
        if (subItem == null) {
            LogUtils.d(TAG, "subItem is null!");
            return;
        }
        if (!TextUtils.isEmpty(subItem.itemPic)) {
            Glide.with(mContext).load(subItem.itemPic).into(iv_image);
        }
        iv_image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnActionListener != null) {
                    mOnActionListener.onClickStaticTemplateItem(position, template_id, grid, subItem);
                }
            }
        });
    }

    private void setImageAndSecKill(final int position, final ImageView iv_image,
                                    final TextView tv_seckillName, final TextView tv_seckillTip, final CountDownView cv_seckillTime,
                                    final int template_id, final TemplateSubItem subItem) {
        if (subItem == null) {
            LogUtils.d(TAG, "subItem is null!");
            return;
        }
        if (!TextUtils.isEmpty(subItem.itemPic)) {
            Glide.with(mContext).load(subItem.itemPic).into(iv_image);
        }
        iv_image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnActionListener != null) {
                    // 5.9.0 秒杀只出现在位置A
                    mOnActionListener.onClickStaticTemplateItem(position, template_id, "a", subItem);
                }
            }
        });
//        if (subItem.is_seckill) {
//            if (!TextUtils.isEmpty(subItem.msgTitle)) {
//                tv_seckillName.setText(subItem.msgTitle);
//                tv_seckillName.setVisibility(View.VISIBLE);
//            } else {
//                tv_seckillName.setVisibility(View.GONE);
//            }
//            if (!TextUtils.isEmpty(subItem.desc)) {
//                tv_seckillTip.setText(subItem.desc);
//                tv_seckillTip.setVisibility(View.VISIBLE);
//            } else {
//                tv_seckillTip.setVisibility(View.GONE);
//            }
//            if (subItem.countdown > 0) {
//                cv_seckillTime.setTime(subItem.countdown);
//                cv_seckillTime.setVisibility(View.VISIBLE);
//                cv_seckillTime.setOnActionListener(new CountDownView.OnActionListener() {
//                    @Override
//                    public void onTimeOut() {
//                        LogUtils.d(TAG, "onTimeOut");
//                        if (mOnActionListener != null) {
//                            mOnActionListener.onCountdownTimeOut();
//                        }
//                    }
//                });
//            } else {
//                cv_seckillTime.setVisibility(View.GONE);
//            }
//        } else {
//            tv_seckillName.setVisibility(View.GONE);
//            tv_seckillTip.setVisibility(View.GONE);
//            cv_seckillTime.setVisibility(View.GONE);
//        }
    }

    public void setDividerVisible(boolean visible) {
        isDividerVisible = visible;
        notifyDataChanged();
    }

    public void setOnActionListener(OnActionListener listener) {
        mOnActionListener = listener;
    }

    public interface OnActionListener {
        void onClickStaticTemplateItem(int position, int template_id, String grid, TemplateSubItem bean);

        void onCountdownTimeOut();
    }

}
