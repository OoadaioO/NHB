package com.nhb.app.custom.ui.items;

import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.fast.library.utils.GsonUtils;
import com.fast.library.utils.StringUtils;
import com.fast.library.utils.ToolUtils;
import com.nhb.app.custom.R;
import com.nhb.app.custom.constant.Constants;
import com.nhb.app.custom.domain.BusinessCallback;
import com.nhb.app.custom.domain.NHBResponse;
import com.nhb.app.custom.domain.RestClient;
import com.nhb.app.custom.ui.personal.WebViewActivity;
import com.nhb.app.custom.utils.ResourceUtil;
import com.nhb.app.custom.utils.UserInfoUtils;
import com.nhb.app.custom.utils.helper.RouteHelper;
import com.nhb.app.custom.utils.helper.UrlHelper;
import retrofit2.Call;

import static com.nhb.app.custom.utils.UserInfoUtils.getUserToken;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-07-23 19:23
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class ItemsDetailActivity extends WebViewActivity {
    public static final int REQUEST_CODE_ADD_COLLECTION = 1;

    private final static String ITEM_ID = "itemId";
    private final static String ITEM_NAME = "itemName";
    private final static String PRICE = "price";

    @Override
    protected void initialize() {
        super.initialize();
        mTitleLayoutView.setVisibility(View.GONE);
        mWebView.addJavascriptInterface(new JsBridge(),"NhbJsBridge");
    }

    @Override
    protected boolean overrideUrlLoading(WebView webView, String url) {
        return handleUrl(webView,url);
    }

    public boolean handleUrl(WebView webView,String url){
        int type = UrlHelper.getUrlType(url);
        boolean result = false;
        switch (type){
            case UrlHelper.UrlType.TEL:
                tel(url);
                break;
            case UrlHelper.UrlType.BUY:
                buy(url);
                break;
            case UrlHelper.UrlType.NORMAL:
                result = true;
                break;
            case UrlHelper.UrlType.SHARE:
                share(url);
                break;
            case UrlHelper.UrlType.STAR:
//                star(url);
                break;
            case UrlHelper.UrlType.UNKNOWN:
                break;
            case UrlHelper.UrlType.BACK:
                finish();
                break;
            default:
                break;
        }
        if (result){
            webView.loadUrl(url);
        }
        return true;
    }

    /**
     * 拨打电话
     * @param url
     */
    private void tel(String url){
        String phone = UrlHelper.getUrlData(url);
        if (!TextUtils.isEmpty(phone)){
            ToolUtils.callPhone(this,phone);
        }
    }

    /**
     * 立即购买
     * @param url
     */
    private void buy(String url){
        if (UserInfoUtils.checkUserLogin()){
            String itemData = UrlHelper.getUrlData(url);
            if (!TextUtils.isEmpty(itemData)){
                String itemId = GsonUtils.optString(itemData, ITEM_ID);
                String itemName = GsonUtils.optString(itemData, ITEM_NAME);
                String price = GsonUtils.optString(itemData, PRICE);
                RouteHelper.getInstance().startCreateOrder(this,itemId,itemName,price);
            }
        }else {
            RouteHelper.getInstance().startLogin(this);
        }
    }

    /**
     * 分享
     * @param url
     */
    private void share(String url){
        showToast("分享");
    }

    /**
     * 收藏
     *
     * @param url
     */
    private void star(String url) {
        String itemData = UrlHelper.getUrlData(url);
        String itemId = GsonUtils.optString(itemData, ITEM_ID);
        starCollection(REQUEST_CODE_ADD_COLLECTION, RestClient.getService().operateCollection(getUserToken(), itemId, Constants.OPERATE_COLLECTION_ADD));
    }

    private void starCollection(int requestCode, Call apiService) {
        apiService.enqueue(new BusinessCallback(requestCode) {

            @Override
            public void onSuccess(int requestCode, Object bean, NHBResponse response) {
                showToast(ResourceUtil.getString(R.string.collection_success));
            }

            @Override
            public void onError(int requestCode, int errorCode, String errorMessage) {
                if (!TextUtils.isEmpty(errorMessage)) {
                    showToast(errorMessage);
                }
            }

            @Override
            public void onComplete(int requestCode, Call call) {
                super.onComplete(requestCode, call);
            }
        });
    }

    class JsBridge{
        @JavascriptInterface
        public String post(String data){
            String response = "";
            if (!StringUtils.isEmpty(data)){
                switch (data){
                    case UrlHelper.TOKEN:
                        if (UserInfoUtils.checkUserLogin()){
                            response = UserInfoUtils.getUserToken();
                        }
                        break;
                    case UrlHelper.LOGIN:
                        showToast("您还未登录，请先登录！");
                        RouteHelper.getInstance().startLogin(ItemsDetailActivity.this);
                        break;
                }
            }
            return response;
        }
    }
}
