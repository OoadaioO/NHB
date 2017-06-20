package com.nhb.app.manager.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.NotificationCompat;

import com.fast.library.ui.ToastUtil;
import com.igexin.sdk.PushManager;
import com.nhb.app.manager.R;
import com.nhb.app.manager.model.SpManager;
import com.nhb.app.manager.ui.activity.MainActivity;

import static com.igexin.push.core.a.i;

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
        if (SpManager.isLogin()){
//            PushManager.getInstance().bindAlias(context,SpManager.getUserInfo().phone);
        }
    }

    public static void unBind(Context context) {
        if (SpManager.isLogin()){
//            PushManager.getInstance().unBindAlias(context, SpManager.getUserInfo().phone, false);
        }
    }

    public static void init(Context context){
        PushManager.getInstance().initialize(context.getApplicationContext());
        PushManager.getInstance().turnOnPush(context);
    }

    /**
     * 说明：显示默认通知
     * @return 通知的ID
     */
    public static void showNotify(Context context,String title,String text){
        NotificationManager mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(MainActivity.TYPE_FROM_NOTIFY_TO_MESSAGE,MainActivity.TYPE_FROM_NOTIFY_TO_MESSAGE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(title)
                .setContentText(text)
                .setTicker(title)
                .setWhen(SystemClock.currentThreadTimeMillis())
                .setAutoCancel(true)
                .setOngoing(false)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.push)
                .setContentIntent(pendingIntent);
        mManager.notify(Integer.parseInt((SystemClock.elapsedRealtime()/1000)+""),builder.build());
    }

    private static PendingIntent sendBroadcast(Context context){
        Intent intents = new Intent(context,ShowNotifyReceiver.class);
        PendingIntent intent = PendingIntent.getBroadcast(context,
                Integer.parseInt((SystemClock.elapsedRealtime()/1000)+""), intents,
                PendingIntent.FLAG_UPDATE_CURRENT);
        return intent;
    }
}
