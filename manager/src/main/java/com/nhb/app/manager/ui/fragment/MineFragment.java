package com.nhb.app.manager.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fast.library.glide.GlideLoader;
import com.fast.library.glide.MulBitmapTransformation;
import com.fast.library.ui.ContentView;
import com.fast.library.utils.ToolUtils;
import com.fast.library.utils.UIUtils;
import com.nhb.app.library.base.NHBFragment;
import com.nhb.app.library.event.EventCenter;
import com.nhb.app.manager.R;
import com.nhb.app.manager.base.ContentFragmentFactory;
import com.nhb.app.manager.bean.AppUpdateBean;
import com.nhb.app.manager.event.EventType;
import com.nhb.app.manager.model.AppUpdateUtils;
import com.nhb.app.manager.model.SpManager;
import com.nhb.app.manager.push.PushUtils;
import com.nhb.app.manager.ui.activity.ActivityAbout;
import com.nhb.app.manager.ui.activity.ContentActivity;
import com.nhb.app.manager.view.MoreItemInfo;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 说明：我的信息
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2016/6/23 9:40
 * <p/>
 * 版本：verson 1.0
 */
@ContentView(R.layout.fragment_mine)
public class MineFragment extends NHBFragment {

    @Bind(R.id.iv_head_pic)
    ImageView iv_head_pic;
    @Bind(R.id.tv_shop_name)
    TextView tv_shop_name;
    @Bind(R.id.tv_user_phone)
    TextView tv_user_phone;
    @Bind(R.id.tv_login_out)
    TextView tv_login_out;

    @Bind(R.id.item_use_agreement)
    MoreItemInfo item_use_agreement;//使用协议
    @Bind(R.id.item_about)
    MoreItemInfo item_about;//关于
    @Bind(R.id.item_update_app)
    MoreItemInfo item_update_app;//版本更新
    @Bind(R.id.item_suggest)
    MoreItemInfo item_suggest;//意见反馈
    @Bind(R.id.item_update_pwd)
    MoreItemInfo item_update_pwd;//修改密码

    @Override
    protected void onFirstUserVisible() {
        bind(R.id.rl_userinfo, true);
        bind(R.id.item_use_agreement, true);
        bind(R.id.item_about, true);
        bind(R.id.item_suggest, true);
        bind(R.id.item_update_pwd, true);
        initMineInfo();
    }

    private void initMineInfo(){
//        门店名称
        tv_shop_name.setText(SpManager.getUserInfo().shopName);
//        门店账号
        tv_user_phone.setText(String.format(UIUtils.getString(R.string.shop_phone), SpManager.getUserInfo().phone));
//        设置头像
        GlideLoader.load(getActivity(), SpManager.getUserInfo().shopHeadImg,R.drawable.default_user_icon,R.drawable.default_user_icon).transform(new MulBitmapTransformation(getActivity(), MulBitmapTransformation.TYPE_CIRCLE)).into(iv_head_pic);
    }

    private void checkAppUpdate(){
//        检查版本更新
        AppUpdateUtils.getInstance().checkApp(new AppUpdateUtils.OnAppUpdateListener() {
            @Override
            public void onUpdate(boolean isUpdate, final AppUpdateBean bean) {
                if (isUpdate){
                    item_update_app.setTipText(R.string.update_app_tip);
                    item_update_app.setTipTextColor(UIUtils.getColor(R.color.appColor));
                    item_update_app.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ToolUtils.openBrowser(getActivity(),bean.url);
                        }
                    });
                }else {
                    item_update_app.setOnClickListener(null);
                    item_update_app.setTipText(R.string.update_app_tip_no);
                    item_update_app.setTipTextColor(UIUtils.getColor(R.color.c_999999));
                }
            }
        });
    }

    @Override
    protected void clickView(View v, int id) {
        Bundle bundle = null;
        switch (id) {
            case R.id.rl_userinfo://个人信息
                break;
            case R.id.item_update_pwd://修改密码
                bundle = new Bundle();
                bundle.putInt("index", ContentFragmentFactory.INDEX_UPDATE_PWD);
                bundle.putString("title",getString(R.string.update_pwd_title));
                showActivity(ContentActivity.class,bundle);
                break;
            case R.id.item_about://关于
                showActivity(ActivityAbout.class);
                break;
            case R.id.item_use_agreement://用户协议
                bundle = new Bundle();
                bundle.putInt("index",ContentFragmentFactory.INDEX_H5);
                bundle.putString("title",getString(R.string.use_agreement));
                bundle.putString("url",getXyUrl());
                showActivity(ContentActivity.class,bundle);
                break;
            case R.id.item_suggest://意见反馈
                bundle = new Bundle();
                bundle.putInt("index",ContentFragmentFactory.INDEX_SUGGESTION);
                bundle.putString("title",getString(R.string.suggestion));
                showActivity(ContentActivity.class,bundle);
                break;
        }
    }

    private String getXyUrl(){
        return UIUtils.getString(R.string.base_url)+"app_h5/app_license_b.html";
    }

    @OnClick(R.id.tv_login_out)
    public void loginOut(View view) {
        PushUtils.unBind(view.getContext());
        EventBus.getDefault().post(new EventCenter<String>(EventType.MESSAGE_LOGINOUT,""));
    }

    @Override
    protected void onUserVisible() {
//        检查软件版本
        checkAppUpdate();
    }

    @Override
    protected void onUserInvisible() {
    }

}
