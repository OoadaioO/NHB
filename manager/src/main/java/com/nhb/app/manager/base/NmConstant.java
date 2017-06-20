package com.nhb.app.manager.base;

/**
 * 说明：年惠宝B端，常用常量
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/7/25 0:09
 * <p/>
 * 版本：verson 1.0
 */
public class NmConstant {

    public interface Page{
        String SIZE = "20";//每页条数
    }

    public interface TradeState{
//        已消费
        String SUCCESS = "已消费";
//        未消费
        String FAIL = "未消费";
//        待确认状态
        String STATE_TO_CONFIRM = "1";
//        已确认状态
        String STATE_CONFIRMED = "2";
//        已取消状态
        String STATE_CANCEL = "3";
//        已完成状态
        String STATE_COMPLETE = "4";
    }

//    public interface OrderMessage{
////        B端用户的手机号码
//        String COLUM_MY_PHONE = "myPhone";
////        C端用户的手机号码
//        String COLUM_LOGIN_PHONE = "customPhone";
////        C端用户的头像
//        String COLUM_CUSTOM_PIC = "customPic";
////        C端用户的姓名
//        String COLUM_CUSTOM_NAME = "customName";
////        订单号，会员卡号
//        String COLUM_ORDERID = "orderId";
////        交易流水号
//        String COLUM_TRADENO = "tradeNo";
////        交易状态
//        String COLUM_TRADE_STATE = "tradeState";
////        交易状态
//        String COLUM_TRADE_MSG = "tradeMsg";
////        消息状态，已经读，未读
////        String COLUM_MESSAGE_STATE = "messageState";
////        通知内容
//        String COLUM_MESSAGE_CONTENT = "msgContent";
////        通知标题
//        String COLUM_NOTIFY_TITLE = "notifyTitle";
////        通知小标题
//        String COLUM_SUB_TITLE = "subTitle";
////        通知创建的时间
//        String COLUM_CREATE_TIME = "createTime";
////        是否可点击
//        String COLUM_IS_CLICKE = "isClick";
////        地址
//        String COLUM_ADDRESS = "address";
////        商品图片
//        String COLUM_ITEM_PIC = "itemPic";
////        商品名称
//        String COLUM_ITEM_NAME = "itemName";
////        商品价格
//        String COLUM_ITEM_PRICE = "itemPrice";
//    }
}
