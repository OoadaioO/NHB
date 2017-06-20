package com.nhb.app.manager.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.TextView;

import com.fast.library.ui.ContentView;
import com.fast.mvp.loader.PresenterFactory;
import com.fast.mvp.loader.PresenterLoader;
import com.nhb.app.manager.R;
import com.nhb.app.manager.base.CommonActivity;
import com.nhb.app.manager.base.ContentFragmentFactory;
import com.nhb.app.manager.contract.ContentContract;
import com.nhb.app.manager.presenter.ContentPresenterImpl;
import com.nhb.app.manager.ui.fragment.ContentFragment;

/**
 * 说明：ContentActivity
 * <p>1.H5页面
 * <p>
 * <p>
 * <p>
 * 作者：fanly
 * <p>
 * 类型：Class
 * <p>
 * 时间：2016/6/30 13:06
 * <p>
 * 版本：verson 1.0
 */
@ContentView(R.layout.activity_content)
public class ContentActivity extends CommonActivity<ContentContract.Presenter> implements ContentContract.View{

    private ContentFragment contentFragment;
    private ContentFragmentFactory factory;

    private boolean isShowTitle = true,isShowBackIcon = true;
    private int index = -1;
    private String title;

    @Override
    public Loader<ContentContract.Presenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(this, new PresenterFactory<ContentContract.Presenter>() {
            @Override
            public ContentContract.Presenter create() {
                return new ContentPresenterImpl();
            }
        });
    }

    @Override
    public void getIntentData(Intent intent) {
        isShowTitle = intent.getBooleanExtra("showTitleBar",true);
        isShowBackIcon = intent.getBooleanExtra("showBack",true);
        index = intent.getIntExtra("index",-1);
        title = intent.getStringExtra("title");
    }

    @Override
    public void onInitCreate(Bundle bundle) {
        super.onInitCreate(bundle);
        factory = new ContentFragmentFactory();
        contentFragment = factory.create(index,this,getIntent().getExtras());
    }

    @Override
    protected void initTitleBar(View titleView, TextView tvTitle) {
        super.initTitleBar(titleView,tvTitle);
        tvTitle.setText(title);
    }

    @Override
    protected boolean isShowBackIcon() {
        return isShowBackIcon;
    }

    @Override
    protected int setTitleBarResID() {
        return isShowTitle ? 0:-1;
    }

    @Override
    public void onInitStart() {
        super.onInitStart();
        changeFragment(R.id.fl_content,contentFragment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        contentFragment = null;
        factory = null;
    }
}
