package com.nhb.app.custom.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.fast.library.utils.GsonUtils;
import com.pingplusplus.android.Pingpp;
import com.pingplusplus.android.PingppLog;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by fanly on 2016/8/7.
 */

public class PayUtils {

    private final static String URL = "http://218.244.151.190/demo/charge";
    /**
     * 银联支付渠道
     */
    private static final String CHANNEL_UPACP = "upacp";
    /**
     * 微信支付渠道
     */
    private static final String CHANNEL_WECHAT = "wx";
    /**
     * 支付支付渠道
     */
    private static final String CHANNEL_ALIPAY = "alipay";
    /**
     * 百度支付渠道
     */
    private static final String CHANNEL_BFB = "bfb";


    private static PayUtils sPayUtils;

    private PayUtils(){
        PingppLog.DEBUG = true;
    }

    public synchronized static PayUtils getInstanse(){
        if (sPayUtils == null){
            sPayUtils = new PayUtils();
        }
        return sPayUtils;
    }

    /**
     * 支付结果回调
     */
    public interface PayListener {
        //        支付成功
        void onPaySuccess();

        //        取消支付
        void onPayCancel();

        //        支付失败
        void onPayFail();

        //        微信客户端未安装
        void onPayInvalid();
    }

    /**
     * 去支付
     *
     * @param activity
     * @param price
     */
    public void payment(Activity activity, String price) {
        PaymentRequest paymentRequest = new PaymentRequest(CHANNEL_ALIPAY,price);
        new PaymentTask(activity).execute(paymentRequest);
    }

    /**
     * 支付页面返回数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void payResult(int requestCode, int resultCode, Intent data, PayListener listener) {
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                /* 处理返回值
                 * "success" - 支付成功
                 * "fail"    - 支付失败
                 * "cancel"  - 取消支付
                 * "invalid" - 支付插件未安装（一般是微信客户端未安装的情况）
                 */
                if (listener != null) {
                    if ("success".equals(result)) {
                        listener.onPaySuccess();
                    } else if ("fail".equals(result)) {
                        listener.onPayFail();
                    } else if ("cancel".equals(result)) {
                        listener.onPayCancel();
                    } else if ("invalid".equals(result)) {
                        listener.onPayInvalid();
                    }
                }
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
            }
        }
    }

    class PaymentRequest {
        String channel;
        String amount;

        public PaymentRequest(String channel, String amount) {
            this.channel = channel;
            this.amount = amount;
        }
    }

    class PaymentTask extends AsyncTask<PaymentRequest,Void,String>{

        private Activity activity;

        public PaymentTask(Activity activity){
            this.activity = activity;
        }

        @Override
        protected void onPostExecute(String s) {
            if (null == s){
                return;
            }
            Pingpp.createPayment(activity,s);
        }

        @Override
        protected String doInBackground(PaymentRequest... params) {
            PaymentRequest paymentRequest = params[0];
            String data = null;
            try {
                MediaType type = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(type, GsonUtils.toJson(paymentRequest));
                Request request = new Request.Builder().url(URL).post(body).build();

                OkHttpClient client = new OkHttpClient();
                Response response = client.newCall(request).execute();
                data = response.body().string();
            }catch (Exception e){
                e.printStackTrace();
            }
            return data;
        }
    }
}
