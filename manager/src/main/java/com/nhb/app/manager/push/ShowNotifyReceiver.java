package com.nhb.app.manager.push;


import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.nhb.app.manager.ui.activity.LoginActivity;

import java.util.List;

/**
 * 
 * 说明：收到通知的广播
 * 
 * 作者：fanly
 * 
 * 时间：2015-12-2 下午12:59:36
 * 
 * 版本：verson 1.0
 * 
 */

public class ShowNotifyReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(final Context context, Intent intent) {
		isBackground(context, new IsBackgroundListener() {
			
			@Override
			public void isForeground() {
				
			}
			
			@Override
			public void isBackground() {
				//后台
				Intent splashIntent = new Intent(context,LoginActivity.class);
				splashIntent.setFlags(
	                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
				context.startActivity(splashIntent);
			}
		});
	}

	/**
	 * 说明：判断应用是前台还是后台
	 * @return
	 */
	public void isBackground(final Context context,final IsBackgroundListener listener){
		try {
			new Thread(new Runnable() {

				@Override
				public void run() {
					ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
					List<ActivityManager.RunningAppProcessInfo> appProcesses = manager.getRunningAppProcesses();
					for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
						if (appProcess.processName.equals(context.getPackageName())) {
							if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
								if (listener != null) {
									listener.isBackground();
								}
							}else{
								if (listener != null) {
									listener.isForeground();
								}
							}
						}
					}
				}
			}).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public interface IsBackgroundListener{
		void isBackground();
		void isForeground();
	}
}
