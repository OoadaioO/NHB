package com.nhb.app.manager.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.fast.library.ui.ActivityStack;
import com.fast.library.utils.GsonUtils;
import com.fast.library.utils.StringUtils;
import com.igexin.sdk.PushConsts;
import com.nhb.app.manager.model.SpManager;
import com.nhb.app.manager.ui.activity.LoginActivity;

/**
 * 说明：个推消息推送
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2016/6/28 18:03
 * <p/>
 * 版本：verson 1.0
 */
public class GeTuiPushReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        switch (bundle.getInt(PushConsts.CMD_ACTION)){
            case PushConsts.GET_CLIENTID:
                String cid = bundle.getString("clientid");
                break;
            case PushConsts.GET_MSG_DATA:
                String taskid = bundle.getString("taskid");
                String messageid = bundle.getString("messageid");
                byte[] payload = bundle.getByteArray("payload");
                if (payload != null) {
                    String data = new String(payload);
                    handleMessageData(context,taskid,messageid,data);
                }
                break;
        }
    }

    /**
     * 解析消息
     * @param taskId
     * @param messgeId
     * @param data
     */
    private void handleMessageData(Context context,String taskId,String messgeId,String data){
        String dataInfos = GsonUtils.optString(data,"data");
        if (!StringUtils.isEmpty(dataInfos)){
            if (!StringUtils.isEmpty(GsonUtils.optString(dataInfos,"loginout"))){
                //退出登录
                SpManager.exit();
                ActivityStack.create().finishOtherActivity(LoginActivity.class);
                PushUtils.showNotify(context,"年惠宝","您的账号在别的设备登录，请重新登录！");
            }else {
                PushUtils.showNotify(context,"会员卡消息",GsonUtils.optString(dataInfos,"msgContent"));
            }
        }

    }
}
