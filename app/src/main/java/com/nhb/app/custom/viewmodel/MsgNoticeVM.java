package com.nhb.app.custom.viewmodel;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nhb.app.custom.R;
import com.nhb.app.custom.bean.MsgBean;
import com.nhb.app.custom.bean.MsgNoticeBean;
import com.nhb.app.custom.domain.NHBResponse;
import com.nhb.app.custom.domain.RestClient;
import com.nhb.app.custom.recyclerview.DividerItemDecoration;
import com.nhb.app.custom.recyclerview.NHBRecyclerVM;
import com.nhb.app.custom.recyclerview.RecyclerItemVM;
import com.nhb.app.custom.utils.ResourceUtil;
import com.nhb.app.custom.utils.UserInfoUtils;
import com.nhb.app.custom.viewmodel.interfaces.ITitleBarNormal;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-26 17:17
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class MsgNoticeVM extends NHBRecyclerVM<MsgNoticeBean> implements ITitleBarNormal {
    @NonNull
    @Override
    public RecyclerItemVM<MsgNoticeBean> getItemVM(int viewType) {
        return new ItemMsgNoticeVM();
    }

    @Override
    public Call loadApiService() {
        return RestClient.getService().getMsgNoticeData(UserInfoUtils.getUserToken(), String.valueOf(startNum));
    }

    @Override
    public void handleData(int requestCode, Object data, NHBResponse response) {
        super.handleData(requestCode, data, response);
        List<MsgNoticeBean> msgNoticeBeanList = new ArrayList<>();
        List<String> list = (List<String>) data;
        for (String jsonStr : list) {
            if (jsonStr.contains("loginout")) {
                JSONObject jsonObject = JSON.parseObject(jsonStr);
                MsgNoticeBean msgNoticeBean = new MsgNoticeBean();
                JSONObject msgNoticeJson = JSON.parseObject(jsonObject.getString("data"));
                if (null != msgNoticeJson) {
                    msgNoticeBean.notifyTitle = jsonObject.getString("message");
                    msgNoticeBean.msgContent = msgNoticeJson.getString("loginout");
                }
                msgNoticeBeanList.add(msgNoticeBean);
            } else {
                MsgBean msgBean = JSON.parseObject(jsonStr, MsgBean.class);
                msgNoticeBeanList.add(msgBean.data);
            }
        }
        addAll(msgNoticeBeanList);
    }

    @Override
    public void clickFinish(View view) {
        finishActivity();
    }

    @Override
    public ObservableField<String> getTitle() {
        ObservableField<String> title = new ObservableField<>();
        title.set(ResourceUtil.getString(R.string.message_notice));
        return title;
    }

    @Override
    public RecyclerView.ItemDecoration getItemDecoration(WeakReference<RecyclerView> recyclerViewRef) {
        DividerItemDecoration divider = (DividerItemDecoration) super.getItemDecoration(recyclerViewRef);
        divider.setDivider(recyclerViewRef.get().getContext(), R.drawable.list_divider_height01);
        return divider;
    }

    @Override
    public int loadMoreView() {
        return R.layout.load_more;
    }

    @Override
    public boolean enablePullToRefresh() {
        return true;
    }
}
