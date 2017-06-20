package com.nhb.app.manager.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fast.library.utils.StringUtils;
import com.fast.library.view.CircleProgressView;
import com.nhb.app.manager.R;

/**
 * 说明：空白页面
 * <p>
 * 作者：fanly
 * <p>
 * 类型：Class
 * <p>
 * 时间：2016/6/27 12:49
 * <p>
 * 版本：verson 1.0
 */
public class EmptyView extends FrameLayout{

    //状态
    public static final int EMPTY = 0x00000001;//数据为空
    public static final int ERROR = 0x00000002;//加载错误
    public static final int NET_ERROR = 0x00000003;//网络错误
    public static final int LOADING = 0x00000004;//加载中
    public static final int SUSSCESS = 0x00000005;//加载成功

    private int currentState = SUSSCESS;//当前状态

    //默认属性
    private static int defaultErrorIcon;//默认错误图标
    private static int defaultNetErrorIcon;//默认网络错误图标
    private static int defaultEmptyIcon;//默认空白图标

    //属性
    private int errorIcon;//错误图标
    private int netErrorIcon;//网络错误图标
    private int emptyIcon;//空白图标

    private View userView;
    private ImageView iv_icon;
    private TextView tv_desc;
    private CircleProgressView cpv;

    public EmptyView(Context context) {
        this(context,null);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取属性
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.EmptyView,defStyleAttr,0);
        int count = array.getIndexCount();
        for (int i = 0;i < count;i++){
            int attr = array.getIndex(i);
            switch (attr){
                case R.styleable.EmptyView_errorIcon:
                    errorIcon = array.getResourceId(attr,defaultErrorIcon);
                    break;
                case R.styleable.EmptyView_emptyIcon:
                    emptyIcon = array.getResourceId(attr,defaultEmptyIcon);
                    break;
                case R.styleable.EmptyView_netErrorIcon:
                    netErrorIcon = array.getResourceId(attr,defaultNetErrorIcon);
                    break;
            }
        }
        array.recycle();
        View view = LinearLayout.inflate(context,R.layout.empty_view,this);
        tv_desc = (TextView) view.findViewById(R.id.tv_desc);
        iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
        cpv = (CircleProgressView) view.findViewById(R.id.cpv);
        setVisibility(View.GONE);
    }

    /**
     * 绑定View
     * @param view
     */
    public void bindView(View view){
        userView = view;
    }

    /**
     * 设置默认网络错误图标
     * @param resId
     */
    public static void setDefaultNetErrorIcon(int resId){
        defaultNetErrorIcon = resId;
    }

    /**
     * 设置默认空白图标
     * @param resId
     */
    public static void setDefaultEmptyIcon(int resId){
        defaultEmptyIcon = resId;
    }
    /**
     * 设置默认加载错误图标
     * @param resId
     */
    public static void setDefaultErrorIcon(int resId){
        defaultErrorIcon = resId;
    }

    public int getCurrentState() {
        return currentState;
    }

    public EmptyView setNetError(String desc,OnClickListener listener) {
        if (listener != null){
            setOnClickListener(listener);
        }
        return setState(NET_ERROR,desc);
    }

    public EmptyView setError(String desc,OnClickListener listener) {
        if (listener != null){
            setOnClickListener(listener);
        }
        return setState(ERROR,desc);
    }

    public EmptyView setEmpty(String desc){
        return setEmpty(desc,null);
    }

    public EmptyView setEmpty(String desc,OnClickListener listener) {
        if (listener != null){
            setOnClickListener(listener);
        }
        return setState(EMPTY,desc);
    }

    public EmptyView setSuccess() {
        return setState(SUSSCESS,"");
    }

    public EmptyView setLoading() {
        return setState(LOADING,"");
    }

    private void setUserViewGone(boolean isGone){
        if (userView != null){
            userView.setVisibility(isGone?View.GONE:View.VISIBLE);
        }
    }

    public EmptyView setState(final int state,final String desc) {
        currentState = state;
        setDesc(desc);
        switch (state){
            case EMPTY://数据为空
                setIcon(emptyIcon);
                cpv.setVisibility(View.GONE);
                setVisibility(View.VISIBLE);
                setUserViewGone(true);
                break;
            case ERROR://加载错误
                setIcon(errorIcon);
                cpv.setVisibility(View.GONE);
                setVisibility(View.VISIBLE);
                setUserViewGone(true);
                break;
            case NET_ERROR://网络错误
                setIcon(netErrorIcon);
                cpv.setVisibility(View.GONE);
                setVisibility(View.VISIBLE);
                setUserViewGone(true);
                break;
            case LOADING://加载中
                setIcon(0);
                cpv.setVisibility(View.VISIBLE);
                setVisibility(View.VISIBLE);
                setUserViewGone(true);
                break;
            default://加载成功
                setVisibility(View.GONE);
                currentState = SUSSCESS;
                setUserViewGone(false);
                break;
        }
        return this;
    }

    public EmptyView setErrorIcon(int resId) {
        errorIcon = resId;
        setIcon(resId);
        return this;
    }

    public EmptyView setEmptyIcon(int resId) {
        emptyIcon = resId;
        setIcon(resId);
        return this;
    }

    public EmptyView setNetErrorIcon(int resId) {
        netErrorIcon = resId;
        setIcon(resId);
        return this;
    }

    public EmptyView setDesc(String desc) {
        if (StringUtils.isEmpty(desc)){
            tv_desc.setVisibility(View.GONE);
        }else {
            if (tv_desc.getVisibility() == View.GONE){
                tv_desc.setVisibility(View.VISIBLE);
            }
            tv_desc.setText(desc);
        }
        return null;
    }

    private void setIcon(int resId){
        if (resId == 0){
            if (iv_icon.getVisibility() != View.GONE){
                iv_icon.setVisibility(View.GONE);
            }
        }else {
            if (iv_icon.getVisibility() != View.VISIBLE){
                iv_icon.setVisibility(View.VISIBLE);
            }
            iv_icon.setImageResource(resId);
        }
    }

//    interface IEmptyView{
//        //获取当前状态
//        int  getCurrentState();
//        //设置网络错误状态
//        EmptyView setNetError(String desc,OnClickListener listener);
//        //设置错误状态
//        EmptyView setError(String desc,OnClickListener listener);
//        //设置空白状态
//        EmptyView setEmpty(String desc,OnClickListener listener);
//        //设置成功状态
//        EmptyView setSuccess();
//        //设置加载中
//        EmptyView setLoading();
//        //设置错误的图标
//        EmptyView setErrorIcon(int resId);
//        //设置错误的图标
//        EmptyView setEmptyIcon(int resId);
//        //设置网络错误图标
//        EmptyView setNetErrorIcon(int resId);
//        //设置描述
//        EmptyView setDesc(String desc);
//    }
}
