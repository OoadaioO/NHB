package com.fast.library.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import com.fast.library.R;
import com.fast.library.ui.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class ActivityUtils {

    /**
     * 跳转到google play市场
     */
    public static boolean jumpToGooglePlayByPackage(Context activity, String packageName) {
        return jumpToGooglePlay(activity, StringUtils.getGooglePlayString(activity, packageName));
    }

    /**
     * 跳转到google play市场
     */
    public static boolean jumpToGooglePlay(Context activity, String url) {
        boolean result = false;
        try {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    public static void jumpToMarket(Context activity, String packageName) {
        try {
            Uri uri = Uri.parse(StringUtils.getString("market://details?id=", packageName));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.get().shortToast(R.string.no_app);
        }

    }

    public static void jumpToMarketByOrder(Context activity, String packageName) {
        Uri uri = Uri.parse(StringUtils.getString("market://details?id=", packageName));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PackageManager pm = activity.getPackageManager();
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
        List<String> marketAppNameList = new ArrayList<String>();
        List<String> marketAppClassList = new ArrayList<String>();
        for (ResolveInfo resolveInfo : infos) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            String targetPargetName = activityInfo.packageName;
            String className = activityInfo.name;
            marketAppNameList.add(targetPargetName);
            marketAppClassList.add(className);
        }
        // 应用宝；百度；360；小米；安智；
        if (marketAppNameList.contains("com.tencent.android.qqdownloader")) {
            int index = marketAppNameList.indexOf("com.tencent.android.qqdownloader");
            ComponentName cn = new ComponentName(marketAppNameList.get(index), marketAppClassList.get(index));
            intent.setComponent(cn);
        } else if (marketAppNameList.contains("com.baidu.appsearch")) {
            int index = marketAppNameList.indexOf("com.baidu.appsearch");
            ComponentName cn = new ComponentName(marketAppNameList.get(index), marketAppClassList.get(index));
            intent.setComponent(cn);
        } else if (marketAppNameList.contains("com.qihoo.appstore")) {
            int index = marketAppNameList.indexOf("com.qihoo.appstore");
            ComponentName cn = new ComponentName(marketAppNameList.get(index), marketAppClassList.get(index));
            intent.setComponent(cn);
        } else if (marketAppNameList.contains("com.xiaomi.market")) {
            int index = marketAppNameList.indexOf("com.xiaomi.market");
            ComponentName cn = new ComponentName(marketAppNameList.get(index), marketAppClassList.get(index));
            intent.setComponent(cn);
        } else if (marketAppNameList.contains("cn.goapk.market")) {
            int index = marketAppNameList.indexOf("cn.goapk.market");
            ComponentName cn = new ComponentName(marketAppNameList.get(index), marketAppClassList.get(index));
            intent.setComponent(cn);
        }
        activity.startActivity(intent);
    }

    /**
     * 跳转到指定app
     */
    public static boolean jumpToIKeyBoard(Context context, String packageName, String className) {
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName(packageName, className);
        intent.setComponent(componentName);
        intent.setAction("android.intent.action.MAIN");
        intent.putExtra("source package", "flip font");
        if (isIntentAvailable(context, intent)) {
            context.startActivity(intent);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断intent是否可用
     */
    private static boolean isIntentAvailable(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public static void openBrowser(Context ctx, String url) {
        try {
            Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(it);
        } catch (Exception e2) {

        }
    }
}
