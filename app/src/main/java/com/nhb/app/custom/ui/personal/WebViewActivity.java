package com.nhb.app.custom.ui.personal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nhb.app.custom.R;
import com.nhb.app.custom.base.NHBActivity;
import com.nhb.app.custom.constant.Constants;

import butterknife.Bind;


public class WebViewActivity extends NHBActivity {
    protected String mUrl;
    protected String mTitle;
    protected String mSavePath;

    @Bind(R.id.iv_back)
    public ImageView mBackView;
    @Bind(R.id.tv_title)
    public TextView mTitleView;
    @Bind(R.id.layout_title)
    public View mTitleLayoutView;
    @Bind(R.id.pb_refresh)
    public ProgressBar mPbRefresh;

    @Bind(R.id.wv)
    public WebView mWebView;

    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return overrideUrlLoading(view, url);
        }
    };

    private WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mPbRefresh.setVisibility(View.GONE);
            } else {
                if (mPbRefresh.getVisibility() == View.GONE) {
                    mPbRefresh.setVisibility(View.VISIBLE);
                }
                mPbRefresh.setProgress(newProgress);
            }
        }
    };

    @Override
    protected void intentWithNormal(Intent intent) {
        super.intentWithNormal(intent);
        mUrl = getIntent().getStringExtra(Constants.WEB_URL);
        mTitle = getIntent().getStringExtra(Constants.WEB_TITLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected int loadLayoutId() {
        return R.layout.activiy_webview;
    }

    @Override
    protected void initialize() {
        setOnClickListener(mBackView);
        mTitleLayoutView.setVisibility(!TextUtils.isEmpty(mTitle) ? View.VISIBLE : View.GONE);
        mTitleView.setText(mTitle);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebView.setWebViewClient(webViewClient);
        mWebView.setWebChromeClient(webChromeClient);

        WebSettings settings = mWebView.getSettings();
        //支持获取手势焦点
        mWebView.requestFocusFromTouch();
        //支持JS
        settings.setJavaScriptEnabled(true);
        //支持插件
        settings.setPluginState(WebSettings.PluginState.ON);
        //设置适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //支持缩放
        settings.setSupportZoom(false);
        //隐藏原生的缩放控件
        settings.setDisplayZoomControls(false);
        //支持内容重新布局
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.supportMultipleWindows();
        //设置缓存模式
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(mWebView.getContext().getCacheDir().getAbsolutePath());
        //设置可访问文件
        settings.setAllowFileAccess(true);
        //当webview调用requestFocus时为webview设置节点
        settings.setNeedInitialFocus(true);
        //支持自动加载图片
        settings.setNeedInitialFocus(true);
        //设置编码格式
        settings.setDefaultTextEncodingName("UTF-8");

        mWebView.loadUrl(mUrl);
    }

    protected boolean overrideUrlLoading(WebView webView, String url) {
        return false;
    }

}
