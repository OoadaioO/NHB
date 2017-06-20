package com.nhb.app.custom.common.dialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fast.library.ui.ToastUtil;
import com.fast.library.utils.DateUtils;
import com.fast.library.utils.FileUtils;
import com.fast.library.utils.StringUtils;
import com.nhb.app.custom.R;
import com.nhb.app.custom.constant.Constants;
import com.nhb.app.custom.utils.ResourceUtil;
import com.nhb.app.custom.utils.UserInfoUtils;
import com.nhb.app.library.qr_code.EncodingHandler;

/**
 * Created by pengxiaofang.
 */
public class QRCodeDialog extends BaseCustomerDialog {
    private View mLoadingUI;
    private View mQrCodeView;
    private ImageView mShowQRCodeView;
    private TextView mCodeNumTv;

    private Context mContext;
    private String mUserInfoStr;
    private String mSaveTimePath;
    private String mSaveImgPath;
    private Bitmap mQrCodeBitmap;

    private boolean mLoadFromService;
    private String mCurrentLongTime;
    private String mPreLongTime;
    private String mCardNum;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == Constants.LOAD_QR_CODE_IMG) {
                mLoadingUI.setVisibility(View.INVISIBLE);
                mQrCodeView.setVisibility(View.VISIBLE);
                mShowQRCodeView.setImageBitmap(mQrCodeBitmap);
                if (mLoadFromService) {
                    //保存刷新时间
                    FileUtils.writeStringToFileCache(mContext, mSaveTimePath, mCurrentLongTime);
                    //保存新的用户信息
                    FileUtils.writeStringToFileCache(mContext, mSaveImgPath, mUserInfoStr);
                }
            } else if (msg.what == Constants.LOAD_QR_CODE_IMG_FAIL) {
                ToastUtil.get().shortToast(R.string.generate_qr_code_failed);
            }
        }
    };

    public QRCodeDialog(Context context, String cardNum, String userInfoStr) {
        super(context, R.style.CustomDialog);
        mContext = context;
        mCardNum = cardNum;
        mUserInfoStr = userInfoStr;
    }

    @Override
    protected int setLayoutViewId() {
        return R.layout.dialog_qr_code;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
        mLoadingUI = findViewById(R.id.ll_loading);
        mQrCodeView = findViewById(R.id.ll_qr_code);
        mShowQRCodeView = (ImageView) findViewById(R.id.img_qr_code);
        mCodeNumTv = (TextView) findViewById(R.id.tv_member_code);
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void attachData() {
        mCardNum = String.format(ResourceUtil.getString(R.string.card_number), mCardNum);
        mCodeNumTv.setText(mCardNum);
        mSaveTimePath = StringUtils.getString(Constants.SAVE_THE_QR_CODE_TIME, UserInfoUtils.getUserId());
        mSaveImgPath = StringUtils.getString(Constants.QR_CODE_IMG_UPLOAD_FILE, UserInfoUtils.getUserId());
        mCurrentLongTime = DateUtils.currentTimeMillis();
        mPreLongTime = FileUtils.readStringFromFileCache(mContext, StringUtils.getString(Constants.SAVE_THE_QR_CODE_TIME, UserInfoUtils.getUserId()));
        if (!DateUtils.isOneDayApartOfMillis(mCurrentLongTime, mPreLongTime)) {
            mLoadFromService = true;
        }
        mLoadingUI.setVisibility(View.VISIBLE);
        mQrCodeView.setVisibility(View.INVISIBLE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(mUserInfoStr)) {
                    mUserInfoStr = FileUtils.readStringFromFileCache(mContext, mSaveImgPath);
                }
                if (!TextUtils.isEmpty(mUserInfoStr)) {
                    //根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
                    mQrCodeBitmap = EncodingHandler.createQRCode(mUserInfoStr, 350);
                    if (mQrCodeBitmap != null) {
                        mHandler.sendEmptyMessage(Constants.LOAD_QR_CODE_IMG);
                    } else {
                        mHandler.sendEmptyMessage(Constants.LOAD_QR_CODE_IMG_FAIL);
                    }
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {

    }
}
