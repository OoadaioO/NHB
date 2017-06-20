package com.nhb.app.manager.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nhb.app.manager.R;

import static android.text.TextUtils.isEmpty;

/**
 * 说明：MoreItemInfo条目
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2016/6/24 18:34
 * <p/>
 * 版本：verson 1.0
 */
public class MoreItemInfo extends FrameLayout{

    private String titleText = "";//标题
    private int leftIcon = 0;//图标
    private boolean rightIcon = true;//箭头
    private String tipText = "";//提示

    private ImageView iv_icon;
    private ImageView iv_right;
    private TextView tv_tip;
    private TextView tv_title;

    public MoreItemInfo(Context context) {
        this(context,null);
    }

    public MoreItemInfo(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MoreItemInfo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取属性
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MoreItem,defStyleAttr,0);
        int count = array.getIndexCount();
        for (int i = 0;i < count;i++){
            int attr = array.getIndex(i);
            switch (attr){
                case R.styleable.MoreItem_leftIcon:
                    leftIcon = array.getResourceId(attr,0);
                    break;
                case R.styleable.MoreItem_rightIconVisibility:
                    rightIcon = array.getBoolean(attr, true);
                    break;
                case R.styleable.MoreItem_tipText:
                    tipText = array.getString(attr);
                    break;
                case R.styleable.MoreItem_titleText:
                    titleText = array.getString(attr);
                    break;
            }
        }
        array.recycle();
        View view = LinearLayout.inflate(context,R.layout.more_item,this);
        iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
        iv_right = (ImageView) view.findViewById(R.id.iv_right);
        tv_tip = (TextView) view.findViewById(R.id.tv_tip);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        initDefaultData();
    }

    private void initDefaultData(){
        setTipText(tipText);
        setLeftIcon(leftIcon);
        setRightIconVisibility(rightIcon);
        setTitleText(titleText);
    }

    /**
     * 设置条目图标
     * @param resId
     */
    public void setLeftIcon(int resId){
        if (resId != 0){
            iv_icon.setImageResource(resId);
        }
    }

    /**
     * 设置点击事件
     * @param listener
     */
    public void setOnItemClickListener(OnClickListener listener){
        if (listener != null){
            setOnClickListener(listener);
        }
    }

    /**
     * 设置箭头图标
     * @param resId
     */
    public void setRightIcon(int resId){
        if (resId != 0){
            iv_icon.setImageResource(resId);
        }
    }

    /**
     * 设置RightIcon显示，隐藏
     * @param visibility
     */
    public void setRightIconVisibility(boolean visibility){
        iv_right.setVisibility(visibility?View.VISIBLE:View.GONE);
    }

    /**
     * 设置标题
     * @param title
     */
    public void setTitleText(String title){
        if (!TextUtils.isEmpty(title)){
            tv_title.setText(title);
        }
    }

    /**
     * 设置标题
     * @param title
     */
    public void setTitleText(int title){
        tv_title.setText(title);
    }

    /**
     * 设置提示信息
     * @param text
     */
    public void setTipText(int text){
        tv_tip.setText(text);
    }

    /**
     * 设置提示信息
     * @param text
     */
    public void setTipText(String text){
        if (tv_tip.getVisibility() == View.GONE){
            tv_tip.setVisibility(View.VISIBLE);
        }
        tv_tip.setText(text);
    }

    /**
     * 设置提示信息
     * @param color
     */
    public void setTipTextColor(int color){
        tv_tip.setTextColor(color);
    }
}
