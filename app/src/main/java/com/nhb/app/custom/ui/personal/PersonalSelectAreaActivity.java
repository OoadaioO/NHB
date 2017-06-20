package com.nhb.app.custom.ui.personal;

import android.content.Intent;
import android.databinding.Observable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.fast.library.utils.DeviceUtils;
import com.nhb.app.custom.R;
import com.nhb.app.custom.base.BaseActivity;
import com.nhb.app.custom.bean.AreaBean;
import com.nhb.app.custom.common.view.FlowLayout;
import com.nhb.app.custom.constant.Constants;
import com.nhb.app.custom.constant.EventCenter;
import com.nhb.app.custom.constant.Extras;
import com.nhb.app.custom.databinding.ActivitySelectAreaBinding;
import com.nhb.app.custom.viewmodel.SelectAreaVM;
import java.util.List;
import de.greenrobot.event.EventBus;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-07-12 15:16
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class PersonalSelectAreaActivity extends BaseActivity<SelectAreaVM, ActivitySelectAreaBinding> {
    private String mSelectAreaId;
    //标签布局的textview的宽度
    private int mItemWidth;

    @Override
    protected void intentWithNormal(Intent intent) {
        mSelectAreaId = intent.getStringExtra(Extras.SELCT_AREA_ID);
    }

    @Override
    protected SelectAreaVM loadViewModel() {
        return new SelectAreaVM(mSelectAreaId);
    }

    @Override
    protected int loadLayoutId() {
        return R.layout.activity_select_area;
    }

    @Override
    protected void initialize() {
        //控一行只显示三个textview并计算每个textview的宽度
        mItemWidth = (DeviceUtils.getWidth() - 4 * (int) DeviceUtils.dp2Px(15)) / 3;
        viewDataBinding.areaFly.setHorizontalSpacing((int) DeviceUtils.dp2Px(15));
        viewDataBinding.areaFly.setVerticalSpacing((int) DeviceUtils.dp2Px(15));

        viewDataBinding.tvEmpty.setText(R.string.open_area_failed);
        viewDataBinding.llEmpty.setVisibility(TextUtils.isEmpty(mSelectAreaId) ? View.VISIBLE : View.GONE);

        viewModel.fetchRemoteData(false);
        viewModel.selectAreaBean.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                viewDataBinding.areaFly.removeAllViews();
                if (null != viewModel.selectAreaBean.get()) {
                    toAddCityForGroup(viewModel.selectAreaBean.get().areaList, viewDataBinding.areaFly);
                }
            }
        });
        viewModel.areaCityBean.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                viewDataBinding.areaFly.removeAllViews();
                if (null != viewModel.areaCityBean.get()) {
                    toAddCityForGroup(viewModel.areaCityBean.get().areaList, viewDataBinding.areaFly);
                }
            }
        });
        viewModel.loadAreaFailed.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (viewModel.loadAreaFailed.get()) {
                    viewDataBinding.tvEmpty.setText(R.string.loading_failure);
                    viewDataBinding.llEmpty.setVisibility(View.VISIBLE);
                }
            }
        });
        // TODO: 9/3/16 Evenbus 
        viewModel.postSuccess.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (viewModel.postSuccess.get()) {
                    EventBus.getDefault().post(new EventCenter(Constants.EventType.LOCATION_AREA_CHANGE, "success"));
                }
            }
        });
    }

    private void toAddCityForGroup(List<AreaBean> areaBeanList, FlowLayout fl_content) {
        viewDataBinding.llEmpty.setVisibility(areaBeanList == null || areaBeanList.size() == 0 ? View.VISIBLE : View.GONE);
        if (areaBeanList == null) {
            return;
        }
        for (final AreaBean bean : areaBeanList) {
            TextView textView = getTextView();
            textView.setText(bean.name);
            fl_content.addView(textView);
            textView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    viewModel.postUserArea(bean);
                }
            });
        }
    }

    private TextView getTextView() {
        TextView textView = new TextView(mContext);
        textView.setLayoutParams(new FlowLayout.LayoutParams(mItemWidth, FlowLayout.LayoutParams.WRAP_CONTENT));
        textView.setPadding((int) DeviceUtils.dp2Px(5), 0, (int) DeviceUtils.dp2Px(5), 0);
        textView.setSingleLine();
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setTextSize(13);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(R.drawable.common_search_city_bg);
        textView.setTextColor(mContext.getResources().getColor(R.color.c_333333));
        return textView;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK || null == data) {
            return;
        }
        switch (requestCode) {
            case Constants.REQUEST_CODE_SELECT_CITY:
                String cityId = data.getStringExtra(Extras.SELECT_CITY_ID);
                String cityName = data.getStringExtra(Extras.SELECT_CITY_NAME);
                viewDataBinding.areaTvCity.setText(cityName);
                viewModel.selectAreaId.set(cityId);
                viewModel.getUserAreaByCity();
                break;
            default:
                break;
        }
    }
}
