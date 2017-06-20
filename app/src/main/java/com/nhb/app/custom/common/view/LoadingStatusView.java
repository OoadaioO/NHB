package com.nhb.app.custom.common.view;

import android.content.Context;
import android.support.annotation.Keep;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nhb.app.custom.R;

/**
 * Created by pxfile on 6/21/16.
 */
@Keep
public class LoadingStatusView extends RelativeLayout {

    private Context mContext;

    private View mLoadingView;
    private LinearLayout mLlLoadingLayout;
    private LinearLayout mLlLoadFailureLayout;
    private LinearLayout mLlLoadEmptyLayout;
    private TextView mTvEmpty;
    private TextView mTvFailure;
    private Button mBtnLoadAgain;

    private LoadingCallback callback;
    /**
     * 加载数据为空的文案
     */
    private String mEmptyText;
    /**
     * 加载数据失败的文案
     */
    private String mFailureText;

    public LoadingStatusView(Context context) {
        this(context, null);
    }

    public LoadingStatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView(context);
    }

    private void initView(Context context) {
        mEmptyText = mContext.getString(R.string.loading_empty);
        mFailureText = mContext.getString(R.string.loading_failure);
        removeAllViews();
        mLoadingView = View.inflate(context, R.layout.nhb_layout_loading, null);
        mLlLoadingLayout = (LinearLayout) mLoadingView.findViewById(R.id.loading_ll_loading);
        mLlLoadFailureLayout = (LinearLayout) mLoadingView.findViewById(R.id.loading_ll_failure);
        mLlLoadEmptyLayout = (LinearLayout) mLoadingView.findViewById(R.id.loading_ll_emptydata);
        mTvEmpty = (TextView) mLoadingView.findViewById(R.id.loading_tv_emptydata);
        mTvFailure = (TextView) mLoadingView.findViewById(R.id.loading_tv_failure);
        mBtnLoadAgain = (Button) mLoadingView.findViewById(R.id.loading_btn_loading_again);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(mLoadingView, params);
    }

    public void setCallback(LoadingCallback callback) {
        this.callback = callback;
    }

    /**
     * 调用成功 会调用此方法
     */
    public void loadSuccess() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
            this.setVisibility(View.GONE);
        }
    }

    /**
     * 加载失败会调用此接口
     */
    public void loadFailed() {
        loadFailed(null);
    }

    /**
     * 加载失败会调用此接口
     */
    public void loadFailed(String describe) {
        if (mLoadingView != null) {
            this.setVisibility(View.VISIBLE);
            mLoadingView.setVisibility(View.VISIBLE);
            mLlLoadingLayout.setVisibility(View.GONE);
            mLlLoadFailureLayout.setVisibility(View.VISIBLE);
            mLlLoadEmptyLayout.setVisibility(View.GONE);
            mTvFailure.setText(TextUtils.isEmpty(describe) ? mFailureText : describe);
            mBtnLoadAgain.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (callback != null) {
                        loading();
                        callback.clickReLoading();
                    }
                }
            });
        }
    }

    /**
     * 加载空数据会调用此接口
     */
    public void loadEmptyData() {
        loadEmptyData(null);
    }

    /**
     * 加载空数据会调用此接口
     */
    public void loadEmptyData(String describe) {
        if (mLoadingView != null) {
            this.setVisibility(View.VISIBLE);
            mLoadingView.setVisibility(View.VISIBLE);
            mLlLoadingLayout.setVisibility(View.GONE);
            mLlLoadFailureLayout.setVisibility(View.GONE);
            mLlLoadEmptyLayout.setVisibility(View.VISIBLE);
            mTvEmpty.setText(TextUtils.isEmpty(describe) ? mEmptyText : describe);
            mLoadingView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (callback != null) {
                        loading();
                        callback.clickReLoading();
                    }
                }
            });
        }
    }

    /**
     * 默认情况下是正在加载中
     */
    public void loading() {
        if (mLoadingView != null) {
            this.setVisibility(View.VISIBLE);
            mLoadingView.setVisibility(View.VISIBLE);
            mLlLoadingLayout.setVisibility(View.VISIBLE);
            mLlLoadFailureLayout.setVisibility(View.GONE);
            mLlLoadEmptyLayout.setVisibility(View.GONE);
        }
    }

    public interface LoadingCallback {
        void clickReLoading();
    }

    public void setFailedText(int resId) {
        if (0 == resId) {
            return;
        }
        mFailureText = mContext.getString(resId);
    }

    public void setFailedText(String describe) {
        if (TextUtils.isEmpty(describe)) {
            return;
        }
        mFailureText = describe;
    }

    public void setEmptyText(int resId) {
        if (0 == resId) {
            return;
        }
        mEmptyText = mContext.getString(resId);
    }

    public void setEmptyText(String describe) {
        if (TextUtils.isEmpty(describe)) {
            return;
        }
        mEmptyText = describe;
    }

    public void setBtnText(int resId) {
        if (0 == resId) {
            return;
        }
        mBtnLoadAgain.setText(mContext.getString(resId));
    }

    public void setBtnText(String describe) {
        if (TextUtils.isEmpty(describe)) {
            return;
        }
        mBtnLoadAgain.setText(describe);
    }

    public String getBtnText() {
        return mBtnLoadAgain.getText().toString().trim();
    }
}
