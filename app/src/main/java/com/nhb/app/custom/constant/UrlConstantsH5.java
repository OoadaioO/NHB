package com.nhb.app.custom.constant;

/**
 * Created by skyler on 1/12/16.
 *
 * @since 1.7.0 h5页面都是用http,不使用https
 */
public class UrlConstantsH5 {

    /**
     * 获取app不同环境对应的url
     *
     * @return
     */
    public static String getUrl(String path, Object... args) {
        if (args != null) {
            path = String.format(path, args);
        }
        return Constants.getAppHost().replace("https://", "http://") + path;
    }
    //    用户协议
    public static final String USER_PROTOCOL = "http://jianbao.artxun.com/index.php?module=art&act=setting&op=agreement";
//    用户帮助
    public static final String USING_HELP = "http://jianbao.artxun.com/index.php?module=art&act=setting&op=ranking";
    public static final String ITEM_DETAIL="http://seemgo.com/storeDetail.html?storeId=761831";

}
