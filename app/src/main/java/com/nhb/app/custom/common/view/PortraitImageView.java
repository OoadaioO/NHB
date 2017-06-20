package com.nhb.app.custom.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fast.library.utils.DeviceUtils;
import com.nhb.app.custom.R;
import com.nhb.app.custom.constant.Constants;
import com.squareup.picasso.Picasso;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-29 15:25
 * Version:1.0.0
 * Description:xml中使用时，layout_width和layout_height都设置为wrap_content
 * 头像的大小设置属性 app:portrait_size
 * 标志大小的属性 app:level_size,默认12dp
 * 标志图片的属性 app:level_src, 默认R.drawable.personal_portrait_level_for_small
 * 需要在根节点添加xmlns:app="http://schemas.android.com/apk/res-auto"
 * ***********************************************************************
 */
public class PortraitImageView extends RelativeLayout {

    private Context mContext;
    private ImageView ivPortrait;
    private ImageView ivUserLevel;
    private int mPortraitSize;
    private int mLevelSize;
    private int mMarging;

    private int PORTRAIT_DEFAULT_SIZE = 50;//默认大小，直径
    private int LEVEL_DEFAULT_SIZE = 13;//大V默认大小，直径
    private int mLevelRes = R.drawable.personal_portrait_level_for_small;

    public PortraitImageView(Context context) {
        super(context);
    }

    public PortraitImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttri(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        RelativeLayout parentView = new RelativeLayout(mContext);
        parentView.setLayoutParams(params);

        ivPortrait = new ImageView(mContext);
        LayoutParams portraitParams = new LayoutParams(mPortraitSize, mPortraitSize);
        ivPortrait.setLayoutParams(portraitParams);
        ivPortrait.setPadding(4, 4, 4, 4);
        ivPortrait.setBackgroundResource(R.drawable.bg_portrait_image_view);
        ivPortrait.setImageResource(R.drawable.ic_user_avatar_default_small);

        ivUserLevel = new ImageView(mContext);
        LayoutParams levelParams = new LayoutParams(mLevelSize, mLevelSize);
        levelParams.leftMargin = mMarging;
        levelParams.topMargin = mMarging;
        ivUserLevel.setLayoutParams(levelParams);
        ivUserLevel.setImageResource(mLevelRes);

        parentView.addView(ivPortrait);
        parentView.addView(ivUserLevel);

        addView(parentView);
    }

    private void initAttri(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PortraitImageView);
        mPortraitSize = (int) DeviceUtils.dp2Px(PORTRAIT_DEFAULT_SIZE);
        mLevelSize = (int) DeviceUtils.dp2Px(LEVEL_DEFAULT_SIZE);
        try {
            mPortraitSize = typedArray.getDimensionPixelSize(R.styleable.PortraitImageView_portrait_size, mPortraitSize);
            mLevelSize = typedArray.getDimensionPixelSize(R.styleable.PortraitImageView_level_size, mLevelSize);
            mLevelRes = typedArray.getResourceId(R.styleable.PortraitImageView_level_src, mLevelRes);
        } finally {
            typedArray.recycle();
        }
        mMarging = mPortraitSize * 17 / 20 - mLevelSize / 2;
    }

    /**
     * 设置默认图片
     *
     * @param res
     */
    public void setImageResource(int res) {
        ivPortrait.setImageResource(res);
    }

    /**
     * 设置用户头像和标识
     *
     * @param url   用户头像链接
     * @param level 用户标识
     */
    public void setPortrait(String url, String level) {
        setPortrait(url);
        setLevel(level);
    }

    public void setPortrait(String url) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(mContext).load(url).into(ivPortrait);
        }
    }

    public void setLevel(String level) {
        if (Constants.MEMBERSHIP_LEVEL.NORMAL.equals(level)) {
            ivUserLevel.setVisibility(View.GONE);
        } else if (Constants.MEMBERSHIP_LEVEL.LEVEL.equals(level)) {
            ivUserLevel.setVisibility(View.VISIBLE);
        } else {
            ivUserLevel.setVisibility(View.GONE);
        }
    }
}

