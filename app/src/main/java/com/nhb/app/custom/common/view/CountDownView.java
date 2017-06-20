package com.nhb.app.custom.common.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fast.library.utils.DateUtils;
import com.nhb.app.custom.R;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * 倒计时
 */
public class CountDownView extends RelativeLayout {

    private static final String TAG = CountDownView.class.getSimpleName();

    private Context mContext;

    private OnActionListener mOnActionListener;

    private TextView tv_hourLeft, tv_hourRight;// 时
    private TextView tv_minuteLeft, tv_minuteRight;// 分
    private TextView tv_secondLeft, tv_secondRight;// 秒

    private Handler handler = new Handler();

    private CountdownRunnable mCountdownRunnable;

    public CountDownView(Context context) {
        super(context);
        init(context);
    }

    public CountDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CountDownView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        View.inflate(context, R.layout.layout_countdown_view, this);
        tv_hourLeft = (TextView) findViewById(R.id.countdownView_tv_hl);
        tv_hourRight = (TextView) findViewById(R.id.countdownView_tv_hr);
        tv_minuteLeft = (TextView) findViewById(R.id.countdownView_tv_ml);
        tv_minuteRight = (TextView) findViewById(R.id.countdownView_tv_mr);
        tv_secondLeft = (TextView) findViewById(R.id.countdownView_tv_sl);
        tv_secondRight = (TextView) findViewById(R.id.countdownView_tv_sr);
    }

    public interface OnActionListener {
        void onTimeOut();
    }

    public void setOnActionListener(OnActionListener listener) {
        mOnActionListener = listener;
    }

    public void setTime(int leftTime) {
        updateLabels(DateUtils.getCountdownTime(leftTime));
        if (mCountdownRunnable == null) {
            mCountdownRunnable = new CountdownRunnable();
        } else {
            handler.removeCallbacks(mCountdownRunnable);
        }
        mCountdownRunnable.setLeftTime(leftTime);
        handler.postDelayed(mCountdownRunnable, 1000);
    }

    private class CountdownRunnable implements Runnable {

        private int leftTime;// 秒

        public void setLeftTime(int leftTime) {
            this.leftTime = leftTime;
        }

        @Override
        public void run() {
            if (leftTime > 0) {
                leftTime--;
                updateLabels(DateUtils.getCountdownTime(leftTime));
                handler.postDelayed(this, 1000);
            } else {// 时间到
                if (mOnActionListener != null) {
                    mOnActionListener.onTimeOut();
                }
            }
        }
    }

    private void updateLabels(int[] timeData) {
        tv_hourLeft.setText(String.valueOf(timeData[0]));
        tv_hourRight.setText(String.valueOf(timeData[1]));
        tv_minuteLeft.setText(String.valueOf(timeData[2]));
        tv_minuteRight.setText(String.valueOf(timeData[3]));
        tv_secondLeft.setText(String.valueOf(timeData[4]));
        tv_secondRight.setText(String.valueOf(timeData[5]));
    }

    private int getCurrentTimeInSeconds() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        long zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
        long currentTimeInMillis = calendar.getTimeInMillis();
        int currentTimeInSeconds = (int) ((currentTimeInMillis + zoneOffset) / 1000);
        return currentTimeInSeconds;
    }

}
