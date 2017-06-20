package com.nhb.app.custom.viewmodel;

import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

import com.nhb.app.custom.R;
import com.nhb.app.custom.base.FetchDataViewModel;
import com.nhb.app.custom.bean.CityBean;
import com.nhb.app.custom.bean.ProvinceBean;
import com.nhb.app.custom.constant.Constants;
import com.nhb.app.custom.constant.Extras;
import com.nhb.app.custom.domain.NHBResponse;
import com.nhb.app.custom.domain.RestClient;
import com.nhb.app.custom.recyclerview.ItemVMFactory;
import com.nhb.app.custom.recyclerview.RecyclerItemVM;
import com.nhb.app.custom.utils.ResourceUtil;
import com.nhb.app.custom.utils.SpCustom;
import com.nhb.app.custom.viewmodel.interfaces.ITitleBarNormal;

import java.util.List;

import retrofit2.Call;

import static android.app.Activity.RESULT_OK;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-07-11 16:28
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class SwitchCityVM extends FetchDataViewModel implements ITitleBarNormal {
    public final ObservableArrayList<ProvinceBean> provinceList = new ObservableArrayList<>();
    public final ObservableArrayList<CityBean> cityList = new ObservableArrayList<>();

    public ObservableField<String> selectProvinceId = new ObservableField<>(Constants.DEFAULT_SWITCH_CITY);
    public ObservableField<String> selectCityId = new ObservableField<>(Constants.DEFAULT_SWITCH_CITY);

    // 用于记录点选的项目
    private ObservableInt mSelectedProvince = new ObservableInt(0);
    private ObservableInt mSelectedCity = new ObservableInt(0);

    public ItemVMFactory provinceFactory = new ItemVMFactory() {
        @NonNull
        @Override
        public RecyclerItemVM getItemVM(int viewType) {
            return new ItemProvinceVM(mSelectedProvince);
        }
    };

    public ItemVMFactory cityFactory = new ItemVMFactory() {
        @NonNull
        @Override
        public RecyclerItemVM getItemVM(int viewType) {
            return new ItemCityVM();
        }
    };

    public SwitchCityVM(String selectProvinceId, String selectCityId) {
        this.selectProvinceId.set(selectProvinceId);
        this.selectCityId.set(selectCityId);
    }

    public void clickProvince(View view, int position) {
        // 记录所选的一级分类项
        ProvinceBean provinceBean = provinceList.get(position);

        SpCustom.get().write(Constants.LOCATION_PROVINCE_ID, provinceBean.id);
        SpCustom.get().write(Constants.LOCATION_PROVINCE_NAME, provinceBean.name);

        // 如有二级分类项则展开
        if (provinceList.get(position).cityList != null) {
            cityList.clear();
            cityList.addAll(provinceBean.cityList);
        }
        mSelectedProvince.set(position);
    }

    public void clickCity(View view, int position) {
        // 一般情况下，点选二级分类才代表操作完成，所以这时才记录用户点选的一二级分类项
        SpCustom.get().write(Constants.LOCATION_CITY_ID, cityList.get(position).id);
        SpCustom.get().write(Constants.LOCATION_CITY_NAME, cityList.get(position).name);
        selectCityId.set(cityList.get(position).id);
        mSelectedCity.set(position);
        Intent intent = new Intent();
        intent.putExtra(Extras.SELECT_CITY_ID, cityList.get(position).id);
        intent.putExtra(Extras.SELECT_CITY_NAME, cityList.get(position).name);
        finishActivityForResult(RESULT_OK, intent);
    }

    @Override
    public void clickFinish(View view) {
        finishActivity();
    }

    @Override
    public ObservableField<String> getTitle() {
        return new ObservableField<>(ResourceUtil.getString(R.string.switch_city));
    }

    @Override
    public Call loadApiService() {
        return RestClient.getService().getCity();
    }

    @Override
    public void handleData(int requestCode, Object data, NHBResponse response) {
        provinceList.clear();
        provinceList.addAll((List<ProvinceBean>) data);

        if (mSelectedProvince.get() < provinceList.size() && provinceList.get(mSelectedProvince.get()).cityList != null) {
            cityList.clear();
            cityList.addAll(provinceList.get(mSelectedCity.get()).cityList);
        }
    }
}
