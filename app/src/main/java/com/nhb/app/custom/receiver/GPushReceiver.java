package com.nhb.app.custom.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fast.library.ui.ToastUtil;
import com.igexin.sdk.PushConsts;
import com.nhb.app.custom.R;
import com.nhb.app.custom.base.BaseApplication;
import com.nhb.app.custom.base.FetchDataViewModel;
import com.nhb.app.custom.constant.Extras;
import com.nhb.app.custom.domain.BusinessCallback;
import com.nhb.app.custom.domain.NHBResponse;
import com.nhb.app.custom.domain.RestClient;
import com.nhb.app.custom.ui.launch.MainActivity;
import com.nhb.app.custom.utils.UserInfoUtils;

import retrofit2.Call;

/**
 * 个推消息接收
 * Created by fanly on 2016/8/16.
 */
public class GPushReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_CLIENTID:
                String cid = bundle.getString("clientid");
                break;
            case PushConsts.GET_MSG_DATA:
                String taskid = bundle.getString("taskid");
                String messageid = bundle.getString("messageid");
                byte[] payload = bundle.getByteArray("payload");
                if (payload != null) {
                    String data = new String(payload);
                    handleMessageData(context, taskid, messageid, data);
                }
                break;
        }
    }

    /**
     * 解析消息
     *
     * @param context
     * @param taskId
     * @param messageId
     * @param data
     */
    private void handleMessageData(Context context, String taskId, String messageId, String data) {
        JSONObject object = JSON.parseObject(data);
        if (null != object && !TextUtils.isEmpty(object.getString("loginout"))) {
            fetchRemoteData(context, RestClient.getService().toLogOut(UserInfoUtils.getUserToken()));
        }
    }

    private void fetchRemoteData(final Context context, Call apiService) {
        apiService.enqueue(new BusinessCallback(FetchDataViewModel.CODE_INIT) {
            @Override
            public void onSuccess(int requestCode, Object bean, NHBResponse response) {
                PushUtils.unBind(BaseApplication.appContext);
                UserInfoUtils.logOut();
                ToastUtil.get().shortToast(R.string.logout_success);
                context.startActivity(new Intent(BaseApplication.appContext, MainActivity.class).putExtra(Extras.RELAUNCH, true));
            }

            @Override
            public void onError(int requestCode, int errorCode, String errorMessage) {
                ToastUtil.get().shortToast(errorMessage);
            }

            @Override
            public void onComplete(int requestCode, retrofit2.Call call) {
                super.onComplete(requestCode, call);
            }
        });
    }
}
