package com.nhb.app.custom.common.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.nhb.app.custom.R;
import com.nhb.app.custom.databinding.LayoutCommonSearchBinding;
import com.nhb.app.custom.utils.ResourceUtil;
import com.nhb.app.custom.viewmodel.CommonSearchLayoutVM;

import com.nhb.app.custom.BR;

/**
 * 通用搜索框
 */
public class CommonSearchLayout extends RelativeLayout implements OnClickListener, OnEditorActionListener, OnScrollListener {
    public LayoutCommonSearchBinding mDataBinding;

    private Context mContext;
    private OnActionListener mOnActionListener;
    // 默认的搜索key
    private String mContentStr = "";
    // 是否可以搜索
    private boolean mCanAutoComplete = true;

    private CommonSearchLayoutVM mCommonSearchLayoutVM;

    public CommonSearchLayout(Context context) {
        this(context, null);
    }

    public CommonSearchLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initialize(context);
    }

    public void setSearchCallback(OnActionListener onActionListener) {
        mOnActionListener = onActionListener;
    }

    private void initialize(Context context) {
        mDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_common_search, this, true);

        mCommonSearchLayoutVM = new CommonSearchLayoutVM();
        mDataBinding.setVariable(BR.viewModel, mCommonSearchLayoutVM);

        mDataBinding.searchTvBtnRight.setOnClickListener(this);
        mDataBinding.searchEtContent.setOnEditorActionListener(this);
        mDataBinding.searchDefaultModeIvBack.setOnClickListener(this);
        mDataBinding.searchRlDefaultMode.setOnClickListener(this);

        mDataBinding.searchEtContent.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.performClick();
                return false;
            }
        });

        mCommonSearchLayoutVM.content.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                String keyword = mCommonSearchLayoutVM.content.get().toString();
                mCommonSearchLayoutVM.recyclerViewShow.set(!TextUtils.isEmpty(keyword));
                if (TextUtils.isEmpty(keyword)) {
                    hideSoftInput();
                }
                if (mCanAutoComplete) {
                    handleKeywords(keyword);
                }
            }
        });

        mCommonSearchLayoutVM.canAutoComplete.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                mCanAutoComplete = mCommonSearchLayoutVM.canAutoComplete.get();
            }
        });

        mCommonSearchLayoutVM.isItemClick.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                setContent("");
                mCommonSearchLayoutVM.defaultModeShow.set(true);
                hideSoftInput();
                if (mOnActionListener != null) {
                    mOnActionListener.onKeywordConfirmed(mCommonSearchLayoutVM.isItemClick.get());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_tv_btnRight: {
                mCommonSearchLayoutVM.defaultModeShow.set(true);
                hideSoftInput();
                setContent("");
            }
            break;
            case R.id.searchDefaultMode_iv_back: {
                hideSoftInput();
                if (mOnActionListener != null) {
                    mOnActionListener.onClickBtnBack();
                }
            }
            break;
            case R.id.search_rl_defaultMode: {
                mCommonSearchLayoutVM.defaultModeShow.set(false);
                showSoftInput();
                if (!TextUtils.isEmpty(mContentStr)) {
                    setContent(mContentStr);
                }
            }
            break;
            default:
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView arg0, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String keyword = getContent();
            // 如果搜索框内容为空，则直接返回
            if (TextUtils.isEmpty(keyword)) {
                return true;
            }
            setContent("");
            mCommonSearchLayoutVM.defaultModeShow.set(true);
            hideSoftInput();
            if (mOnActionListener != null) {
                mOnActionListener.onKeywordConfirmed(keyword);
            }
        }
        return false;
    }

    // ==============================/滚动回调/==============================

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        hideSoftInput();
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // Just do nothing
    }

    public interface OnActionListener {
        void onKeywordConfirmed(String content);

        void onClickBtnBack();
    }

    private void handleKeywords(String keyword) {
        mCommonSearchLayoutVM.fetchKeyWords(keyword);
    }
    // ====================/Getter & Setter/====================

    public void setHint(int hint) {
        mCommonSearchLayoutVM.hintContent.set(ResourceUtil.getString(hint));
    }

    public void setHint(String hint) {
        mCommonSearchLayoutVM.hintContent.set(hint);
    }

    public void initContent(String content) {
        if (content != null) {
            mContentStr = content;
            mCommonSearchLayoutVM.hintContent.set(content);
        }
    }

    public void setImeOptions(int imeOptions) {
        mCommonSearchLayoutVM.imeOptions.set(imeOptions);
    }

    public String getContent() {
        return mCommonSearchLayoutVM.content.get().trim();
    }

    public void setContent(String content) {
        mCommonSearchLayoutVM.content.set(content);
        mDataBinding.searchEtContent.setText(content);
        mDataBinding.searchEtContent.setSelection(TextUtils.isEmpty(content) ? 0 : content.length());
    }

    /**
     * 手动调出软键盘
     */
    private void showSoftInput() {
        mDataBinding.searchEtContent.requestFocus();
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mDataBinding.searchEtContent, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 手动隐藏软键盘
     */
    private void hideSoftInput() {
        mDataBinding.searchEtContent.clearFocus();
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mDataBinding.searchEtContent.getWindowToken(), 0);
    }
}
