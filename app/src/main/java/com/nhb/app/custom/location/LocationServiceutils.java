package com.nhb.app.custom.location;

import android.content.Context;
import android.os.Handler;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.fast.library.utils.LogUtils;
import com.nhb.app.custom.constant.Constants;
import com.nhb.app.custom.utils.SpCustom;

public class LocationServiceutils implements BDLocationListener {

    private final int TIMEOUT_VALUE = 1000 * 60 * 5;
    private LocationListener mListener;
    private LocationClient mLocationClient;
    private static LocationServiceutils mLocationService = null;

    public double lat;
    public double lng;
    private Handler mTimerHandler;
    private Runnable mTimerRunnable;

    public String areaCode;
    public String locationName;

    public static LocationServiceutils getInstance() {
        if (mLocationService == null) {
            mLocationService = new LocationServiceutils();
        }
        return mLocationService;
    }

    public LocationClient initBaiduLocaiton(Context context) {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
        option.setAddrType("all");// 返回的定位结果包含地址信息
        option.setCoorType("bd09ll");
        option.setPriority(LocationClientOption.NetWorkFirst); //设置定位优先级
        // 设置获取地址信息
        option.setIsNeedAddress(true);
        mLocationClient = new LocationClient(context);
        mLocationClient.setLocOption(option);
        mLocationClient.registerLocationListener(LocationServiceutils.this);
        return mLocationClient;
    }

    /**
     * 停止百度服务
     *
     * @Description:
     */
    public void stopBaiduLocationService() {
        if (mLocationClient != null) {
            mLocationClient.stop();
        }
    }

    public LocationServiceutils setListener(LocationListener listener) {
        this.mListener = listener;
        return this;

    }

    /**
     * 开启百度服务
     *
     * @param context
     * @Description:
     */
    public void startBaiduLocationService(Context context) {
        if (mLocationClient != null) {
            mLocationClient.start();
        } else {
            initBaiduLocaiton(context);
            mLocationClient.start();
        }
        startTimeOutTimer();
    }

    /**
     * 5分钟倒计时获取不到位置信息就停止获取，避免过度耗费电量
     *
     * @since 5.3.0
     */
    private void startTimeOutTimer() {
        if (null == mTimerHandler) {
            mTimerHandler = new Handler();
            mTimerRunnable = new Runnable() {
                @Override
                public void run() {
                    stopBaiduLocationService();
                }
            };
        }
        mTimerHandler.removeCallbacks(mTimerRunnable);
        mTimerHandler.postDelayed(mTimerRunnable, TIMEOUT_VALUE);
    }

    @Override
    public void onReceiveLocation(BDLocation location) {
        if (location == null) {
            return;
        }
        stopBaiduLocationService();
        if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
            if (mListener != null) {
                mListener.location(location.getLatitude(), location.getLongitude(), location.getAddrStr());
            }
            this.lat = location.getLatitude();
            this.lng = location.getLongitude();
            this.areaCode = location.getAddress().cityCode;
            this.locationName = location.getAddress().district;
            SpCustom.get().write(Constants.LOCATION_PROVINCE_NAME, location.getProvince());
            SpCustom.get().write(Constants.LOCATION_CITY_NAME, location.getCity());
            SpCustom.get().write(Constants.LOCATION_AREA_NAME, this.locationName);
            LogUtils.e("areaCode", "areaCode--->" + areaCode + "---locationName--->" + locationName);
        }
    }

    public interface LocationListener {
        /**
         * 反地理编码
         *
         * @param lat
         * @param lon
         * @param address
         */
        void location(double lat, double lon, String address);
    }
}
