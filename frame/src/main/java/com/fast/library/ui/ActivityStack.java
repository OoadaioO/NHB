package com.fast.library.ui;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.Stack;

/**
 * 说明：应用程序Activity管理类：用于Activity管理和应用程序退出
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/10/28 21:39
 * <p/>
 * 版本：verson 1.0
 */

public class ActivityStack {

    private static Stack<WeakReference<Activity>> sActivityStack;
    private static final ActivityStack instance = new ActivityStack();

    private ActivityStack(){
        sActivityStack =  new Stack<>();
    }

    public static ActivityStack create(){
        return instance;
    }

    /**
     * 说明：获取当前Activity栈中数量
     * @return
     */
    public int getCount(){
        return sActivityStack.size();
    }

    /**
     * 说明：将activity添加到栈中
     * @param activity
     */
    public void addActivity(Activity activity){
        if (activity != null) {
            sActivityStack.add(new WeakReference<Activity>(activity));
        }
    }

    /**
     * 说明：获取栈顶的Activity
     * @return
     */
    public Activity topActivity(){
        if (sActivityStack == null || sActivityStack.isEmpty()) {
            throw new NullPointerException(
                    "Activity stack is Null,your Activity must extend FlyActivity");
        }
        return sActivityStack.lastElement().get();
    }

    /**
     * 说明：查找activity,没有则返回null
     * @param cls
     * @return
     */
    public Activity findActivity(Class<?> cls){
        Activity activity = null;
        for (WeakReference<Activity> aty : sActivityStack) {
            if (aty.get().getClass().equals(cls)) {
                activity = aty.get();
                break;
            }
        }
        return activity;
    }

    public void finishActivity(){
        if (sActivityStack != null && !sActivityStack.isEmpty()) {
            finishActivity(sActivityStack.lastElement().get());
        }
    }

    /**
     * 说明：结束指定activity
     * @param activity
     */
    public void finishActivity(Activity activity){
        if (activity != null && !activity.isFinishing()) {
            sActivityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 说明：结束指定activity
     * @param cls
     */
    public void finishActivity(Class<?> cls){
        if (sActivityStack != null && !sActivityStack.isEmpty()) {
            for (WeakReference<Activity> aty : sActivityStack) {
                if (aty.get().getClass().equals(cls)) {
                    finishActivity(aty.get());
                }
            }
        }
    }

    /**
     * 说明：清除cls外其他所有activity
     * @param cls
     */
    public void finishOtherActivity(Class<?> cls){
        if (sActivityStack != null && !sActivityStack.isEmpty()) {
            for (WeakReference<Activity> aty : sActivityStack) {
                if (!aty.get().getClass().equals(cls)) {
                    finishActivity(aty.get());
                }
            }
        }
    }

    /**
     * 说明：结束所有activity
     */
    public void finishAllActivity(){
        if (sActivityStack != null && !sActivityStack.isEmpty()) {
            for(int i = 0,size = sActivityStack.size();i<size;i++){
                if (null != sActivityStack.get(i)) {
                    (sActivityStack.get(i).get()).finish();
                }
            }
            sActivityStack.clear();
        }
    }

    /**
     * 说明：退出应用
     */
    public void AppExit(){
        try {
            finishAllActivity();
            Runtime.getRuntime().exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            Runtime.getRuntime().exit(-1);
        }
    }
}

