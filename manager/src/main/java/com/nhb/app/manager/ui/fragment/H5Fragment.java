package com.nhb.app.manager.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.fast.library.ui.ContentView;
import com.fast.library.ui.WebViewLoader;
import com.fast.library.utils.StringUtils;
import com.nhb.app.manager.R;

import butterknife.Bind;

/**
 * 说明：H5Fragment
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2016/6/30 14:35
 * <p/>
 * 版本：verson 1.0
 */
@ContentView(R.layout.fragment_h5)
public class H5Fragment extends ContentFragment{

    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.pb_refresh)
    ProgressBar pbRefresh;

    private String url;
    private WebViewLoader webViewLoader;
    private WebViewLoader.Adapter mAdapter = new WebViewLoader.Adapter() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100){
                pbRefresh.setVisibility(View.GONE);
            }else {
                if (pbRefresh.getVisibility() == View.GONE){
                    pbRefresh.setVisibility(View.VISIBLE);
                }
                pbRefresh.setProgress(newProgress);
            }
        }
    };

    @Override
    protected void onInitData(Bundle bundle) {
        url = bundle.getString("url");
        webViewLoader = new WebViewLoader(webView,mAdapter);
        webViewLoader.init();
        if (!StringUtils.isEmpty(url)){
            webViewLoader.loadUrl(url);
        }
    }

}
