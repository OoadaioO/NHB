package com.nhb.app.custom.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.widget.EditText;

import com.nhb.app.custom.constant.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by you on 2015/8/19.
 */
public class SMSBroadcastReceiver extends BroadcastReceiver {
    private EditText mTargetView;

    public SMSBroadcastReceiver(EditText targetView) {
        mTargetView = targetView;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Constants.SMS_RECEIVED_ACTION)) {
            Object[] pdus = (Object[]) intent.getExtras().get("pdus");
            if (pdus != null && pdus.length > 0) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[0]);
                Pattern pattern = Pattern.compile("[^0-9]");
                Matcher matcher = pattern.matcher(smsMessage.getDisplayMessageBody());
                mTargetView.setText(matcher.replaceAll(""));
            }
        }
    }
}
