package com.nhb.app.custom.receiver;

import android.content.Context;
import com.igexin.sdk.PushManager;
import com.nhb.app.custom.utils.UserInfoUtils;

/**
 * 说明：PushUtils
 * <p>
 * 作者：fanly
 * <p>
 * 类型：Class
 * <p>
 * 时间：2016/8/20 8:49
 * <p>
 * 版本：verson 1.0
 */
public class PushUtils {

    public static void bind(Context context) {
        if (UserInfoUtils.checkUserLogin()){
//            PushManager.getInstance().bindAlias(context,UserInfoUtils.getPhone());
        }
    }

    public static void unBind(Context context) {
        if (UserInfoUtils.checkUserLogin()){
//            PushManager.getInstance().unBindAlias(context, UserInfoUtils.getPhone(), false);
        }
    }

    public static void init(Context context){
        PushManager.getInstance().initialize(context.getApplicationContext());
        PushManager.getInstance().turnOnPush(context);
    }
}
