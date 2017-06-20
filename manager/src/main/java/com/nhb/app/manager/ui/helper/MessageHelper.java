package com.nhb.app.manager.ui.helper;

import com.nhb.app.library.event.EventCenter;
import com.nhb.app.manager.event.EventType;
import com.nhb.app.manager.model.SpManager;

import de.greenrobot.event.EventBus;

/**
 * 说明：消息工具类
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2016/8/19 9:19
 * <p/>
 * 版本：verson 1.0
 */
public class MessageHelper {

    /**
     * 显示消息的数量
     * @param number
     */
    public static void showMessageNumber(int number){
        if (SpManager.isLogin()){
            EventBus.getDefault().post(new EventCenter<Integer>(EventType.MESSAGE_NUM, number));
        }
    }
    public static void refreshMessage(){
        EventBus.getDefault().post(new EventCenter<String>(EventType.MESSAGE_REFRESH,""));
    }

    /**
     * 清除消息提示
     */
    public static void cleanMessage(){
        showMessageNumber(0);
    }

//    /**
//     * 修改消息的状态
//     * @param context
//     * @param id
//     * @param tradeState
//     */
//    public static void updateMessageState(Context context,int id,String tradeState){
//        MessageDao dao = new MessageDao(context);
//        OrderMessageBean bean = dao.queryById(id);
//        if (bean != null){
//            bean.setTradeState(tradeState);
//            dao.update(bean);
//        }
//    }

//    /**
//     * 获取未读消息数量
//     * @param activity
//     * @param listener
//     */
//    public static void getUnreadMessageNum(final Activity activity,final BLoadListener<Integer> listener){
//        if (listener == null)return;
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                activity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        listener.onStart();
//                    }
//                });
//                MessageDao dao = new MessageDao(activity);
//                Map<String,Object> query = new HashMap<String, Object>();
//                query.put(NmConstant.OrderMessage.COLUM_MY_PHONE,SpManager.getUserInfo().phone);
//                query.put(NmConstant.OrderMessage.COLUM_MESSAGE_STATE,false);
//                final List<OrderMessageBean> list = dao.queryByColumnName(query);
//                activity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (list == null){
//                            listener.onSucc(0);
//                        }else {
//                            listener.onSucc(list.size());
//                        }
//                    }
//                });
//                activity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        listener.onFinish();
//                    }
//                });
//            }
//        }).start();
//    }
}
