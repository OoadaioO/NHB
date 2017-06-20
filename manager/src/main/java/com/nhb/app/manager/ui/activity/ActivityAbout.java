package com.nhb.app.manager.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.TextView;

import com.fast.library.span.SpanSetting;
import com.fast.library.span.SpanTextUtils;
import com.fast.library.ui.ContentView;
import com.fast.library.utils.AndroidInfoUtils;
import com.fast.library.utils.UIUtils;
import com.nhb.app.manager.R;
import com.nhb.app.manager.base.CommonActivity;

import butterknife.Bind;

/**
 * 说明：关于我们
 * <p>
 * 作者：fanly
 * <p>
 * 类型：Class
 * <p>
 * 时间：2016/8/22 8:50
 * <p>
 * 版本：verson 1.0
 */
@ContentView(R.layout.activity_about)
public class ActivityAbout extends CommonActivity{

    @Bind(R.id.tv_about_version)
    TextView tvVersion;

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

    }

    @Override
    protected void initTitleBar(View titleView, TextView tvTitle) {
        super.initTitleBar(titleView, tvTitle);
        tvTitle.setText("关于我们");
    }

    @Override
    public void onInitStart() {
        super.onInitStart();
        SpanSetting xxshow = new SpanSetting().setCharSequence("年惠宝");
        SpanSetting version = new SpanSetting().setCharSequence(String.format(UIUtils.getString(R.string.about_me_sersion_name), AndroidInfoUtils.versionName())).setColor(Color.parseColor("#434343"));
        SpanTextUtils.setText(tvVersion,xxshow,version);
    }
}
