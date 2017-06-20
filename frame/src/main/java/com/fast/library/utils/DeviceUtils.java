package com.fast.library.utils;

/**
 * Created by pxfile on 6/22/16.
 */

import android.Manifest;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/**
 * 设备相关信息
 */
public class DeviceUtils {

    public static String DEVICE_RELEASE_VERSION = Build.VERSION.RELEASE; // 获取版本号

    public static String DEVICE_MODEL = Build.MODEL;            // 获取手机型号

    public static String DEVICE_ID;

    public static String MAC_ADDRESS;

    private static final String ANDROID_ID_PREFIX = "androidid_";

    private static WeakReference<Context> mContextRef;

    public static void init(Context context) {
        mContextRef = new WeakReference<>(context.getApplicationContext());
        setDeviceInfo();
    }

    public static int getWidth() {
        if (mContextRef == null) {
            return 0;
        }
        return mContextRef.get().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getHeight() {
        if (mContextRef == null) {
            return 0;
        }
        return mContextRef.get().getResources().getDisplayMetrics().heightPixels;
    }

    public static int getStateHeight() {
        if (null == mContextRef) {
            return 0;
        }
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = mContextRef.get().getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight() {
        int result = 0;
        if (null != mContextRef) {
            int resourceId = mContextRef.get().getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = mContextRef.get().getResources().getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    /**
     * 点亮屏幕
     *
     * @param timeout The timeout after which to release the wake lock, in milliseconds
     */
    public static void weekUpScreen(long timeout) {
        if (null == mContextRef) {
            return;
        }
        try {
            PowerManager pm = (PowerManager) mContextRef.get().getSystemService(Context.POWER_SERVICE);
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "gengmei");
            wl.acquire(timeout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static float dp2Px(float dp) {
        if (null == mContextRef) {
            return -1;
        }
        return dp * mContextRef.get().getResources().getDisplayMetrics().density;
    }

    public static float px2Dp(float px) {
        if (null == mContextRef) {
            return -1;
        }
        return px / mContextRef.get().getResources().getDisplayMetrics().density;
    }

    public static int dip2px(float dp) {
        return (int) (dp2Px(dp) + 0.5f);
    }

    public static int px2DpCeilInt(float px) {
        return (int) (px2Dp(px) + 0.5f);
    }

    public static void setDeviceInfo() {
        if (MPermissionUtil.isPermission(mContextRef.get(), Manifest.permission.READ_PHONE_STATE)) {
            TelephonyManager tm = (TelephonyManager) mContextRef.get().getSystemService(Context.TELEPHONY_SERVICE);
            String deviceId = tm.getDeviceId();
            if (TextUtils.isEmpty(deviceId)) {
                String androidId = Settings.Secure.getString(mContextRef.get().getContentResolver(), Settings.Secure.ANDROID_ID);
                DeviceUtils.DEVICE_ID = ANDROID_ID_PREFIX + androidId;
            } else {
                DeviceUtils.DEVICE_ID = deviceId;
            }

            WifiManager wifi = (WifiManager) mContextRef.get().getSystemService(Context.WIFI_SERVICE);
            DeviceUtils.MAC_ADDRESS = wifi.getConnectionInfo().getMacAddress();
        }
    }
}
