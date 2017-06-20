package com.fast.library.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by pengxiaofang.
 */
public class MPermissionUtil {
    //6.0权限管理
    public static final int PERMISSION_REQ_CODE_READ_STORAGE = 100;
    public static final int PERMISSION_REQ_CODE_WRITE_STORAGE = 101;
    public static final int PERMISSION_REQ_CODE_READ_CAMERA = 102;
    public static final int PERMISSION_REQ_CODE = 103;

    public static void requestAllPermission(Context context, Activity activity) {
        requestPermissions(context, activity);
    }

    public static boolean isAllowedAllPermission(Context context) {
        return isPermission(context, Manifest.permission.CAMERA) && isPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) && isPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) && isPermission(context, Manifest.permission.READ_PHONE_STATE) && isPermission(context, Manifest.permission.WRITE_SETTINGS);
    }

    public static void requestPermissionStorage(Context context, Activity activity) {
        //判断 --6.0以上 该功能需要读写存储权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //6.0运行时权限适配
            if (!isPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) || !isPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //没有授权, 申请授权
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQ_CODE_READ_STORAGE);
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQ_CODE_WRITE_STORAGE);
            }
        }
    }

    public static void requestPermissionCamera(Context context, Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //6.0运行时权限适配
            if (!isPermission(context, Manifest.permission.CAMERA)) {
                //没有授权, 申请授权
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQ_CODE_READ_CAMERA);
            } else {
                //已授权，就直接申请读写存储权限
                requestPermissionStorage(context, activity);
            }
        }
    }

    public static void requestPermissions(Context context, Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //6.0运行时权限适配
            if (!isPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                //没有授权, 申请授权
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_SETTINGS}, PERMISSION_REQ_CODE);
            }
        }
    }

    public static boolean isPermission(Context context, String permission) {
        //6.0运行时权限适配
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }
}
