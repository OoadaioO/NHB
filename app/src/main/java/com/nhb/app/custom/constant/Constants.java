package com.nhb.app.custom.constant;

import com.fast.library.utils.UIUtils;
import com.nhb.app.custom.R;

/**
 * 常量类
 *
 * @author pengxiaofang
 * @version 1.0
 * @since 2015-05-05
 */
public final class Constants {
    // 获取短信通知
    public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
    //客服电话
    public static final String CUSTOM_PHONE_NUMBER = "17710367328";

    public static String getAppHost() {
        return UIUtils.getString(R.string.base_url);
    }

    /**
     * 来源页面名称
     *
     * @since 5.9.1
     */
    public static final String REFERRER_PAGE_NAME = "referrer_page_name";

    /**
     * 缓存的一些key值
     *
     * @since 6.0.0
     */
    public static final class CacheKey {
        //是否显示引导页
        public static final String GUIDE_PAGE_VERSION = "guide_page_version";
        //是否已登录
        public static final String IS_LOGIN = "is_login";
        public static final String PIC_CACHE = "pic";
    }

    /**
     * Banner类型
     *
     * @since 6.0.0
     */
    public static final class BannerType {
        //商品
        public static final String SHOP_ID = "13";
        //H5页面
        public static final String URL = "11";
    }

    public static final int FIRST_VISIBLE_ITEM = 2;

    public static final class MEMBERSHIP_LEVEL {
        /**
         * 普通人
         */
        public static final String NORMAL = "0";
        /**
         * 会员
         */
        public static final String LEVEL = "1";
    }

    public static final class ITEM_SERVICE_TYPE {
        /**
         * 到家服务
         */
        public static final String SERVICE_HOME = "0";
        /**
         * 到店服务
         */
        public static final String SERVICE_STORE = "1";
    }

    /**
     * 商品
     */
    public static final int FIRST_VISIBLE_ITEMS_ITEM = 2;

    public static final int LOAD_QR_CODE_IMG = 1;
    public static final int LOAD_QR_CODE_IMG_FAIL = 2;

    public static final String SAVE_THE_QR_CODE_TIME = "save_the_qr_code_time_";
    public static final String QR_CODE_IMG_UPLOAD_FILE = "save_the_qr_code_img_";

    public static final String WEB_URL = "web_url";
    public static final String WEB_TITLE = "web_title";
    public static final String ITEM_ID = "id";

    public static final String B_PACKAGENAME = "com.nhb.app.manager";

    /**
     * 登录
     */
    public static final class LOGIN {
        public static final int PHONE_LENGTH = 11;
        public static final int AUTH_CODE_LENGTH_FOUR = 4;
        public static final int AUTH_CODE_LENGTH_SIX = 6;
        public static final int AUTH_CODE_TIME_OUT = 60000;
        public static final String CHINA = "86";
        public static final String APPKEY = "1412bf6790c86";
        public static final String APPSECRET = "8861ffea751db72cc8d7ba782cc59b24";
    }

    /**
     * 跳转至选择城市界面
     */
    public static final int REQUEST_CODE_SELECT_CITY = 5000;

    /**
     * 默认切换的城市
     */
    public static final String DEFAULT_SWITCH_CITY = "";
    /**
     * 默认选择的区域
     */
    public static final String DEFAULT_SELECT_AREA = "";
    /**
     * 定位城市
     */
    public static final String LOCATION_PROVINCE_ID = "location_province_id";
    public static final String LOCATION_PROVINCE_NAME = "location_province_name";
    public static final String LOCATION_CITY_ID = "location_city_id";
    public static final String LOCATION_CITY_NAME = "location_city_name";
    public static final String LOCATION_AREA_ID = "location_area_id";
    public static final String LOCATION_AREA_NAME = "location_area_name";

    public interface EventType{
        String LOCATION_AREA_CHANGE = "location_area_change";//切换地址事件
    }

    public static final String SEARCH_HISTORY = "search_history";

    /**
     * 0表示搜索
     */
    public static final String SEARCH_ITEMS = "0";
    /**
     * 1表示查询商品分类二级分类
     */
    public static final String SELECT_ITEMS = "1";
    /**
     * 2表示查询商品一级分类
     */
    public static final String SELECT_ITEMS_ONE = "2";

    /**
     * 会员卡是否可用【0：不可使用，1：可使用】
     */
    public static final String MEMBER_CARD_DISABLE = "0";
    /**
     * 会员卡是否可用【0：不可使用，1：可使用】
     */
    public static final String MEMBER_CARD_ENABLE = "1";

    /**
     * "0"：删除收藏商品
     */
    public static final String OPERATE_COLLECTION_DELETE = "0";
    /**
     * "1"：添加收藏商品
     */
    public static final String OPERATE_COLLECTION_ADD = "1";

    /**
     * 客户端标识（1：iOS，2：Android）
     */
    public static final String LOGIN_APPCLIENT = "2";
}
