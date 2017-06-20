package com.nhb.app.custom.viewmodel;

import com.nhb.app.custom.R;
import com.nhb.app.custom.bean.MsgNoticeBean;
import com.nhb.app.custom.recyclerview.RecyclerItemVM;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-26 17:01
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class ItemMsgNoticeVM extends RecyclerItemVM<MsgNoticeBean> {
    @Override
    public int loadItemView() {
        return R.layout.item_msg_notice;
    }
}
