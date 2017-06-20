package com.nhb.app.custom.ui.home.fragment;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

import com.nhb.app.custom.R;
import com.nhb.app.custom.common.bean.TemplateSubItem;
import com.nhb.app.custom.ui.home.adapter.HomeItemsAdapter;
import com.nhb.app.custom.utils.ListViewUtil;
import com.nhb.app.custom.utils.helper.RouteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-07-07 17:01
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class HomeItemsFragment extends BaseHomeListFragment {
    private HomeItemsAdapter mItemsAdapter;
    private List<TemplateSubItem> mBeans = new ArrayList<>();

    public void handleData(List<TemplateSubItem> beans, boolean isUpdateData) {
        if (isUpdateData) {
            mBeans.clear();
            mExitIdMap.clear();
            mBeans.addAll(ListViewUtil.removeDataDuplicate(mExitIdMap, beans, TemplateSubItem.class, "itemId"));
            mItemsAdapter = new HomeItemsAdapter(getActivity(), mBeans);
            if (lv_content != null) {
                lv_content.setAdapter(mItemsAdapter);
            }
            mStatusView.loadSuccess();
        } else {
            mBeans.addAll(ListViewUtil.removeDataDuplicate(mExitIdMap, beans, TemplateSubItem.class, "itemId"));
            mItemsAdapter.notifyDataSetChanged();
            mStatusView.loadSuccess();
        }
        if (mBeans.size() == 0) {
//            ToastUtil.get().shortToast(R.string.no_data_now);
            mStatusView.loadEmptyData(mContext.getString(R.string.no_data_now));
        }
    }

    @Override
    public int getStartNum() {
        return mBeans == null ? 0 : mBeans.size();
    }

    @Override
    protected boolean isEmpty() {
        if (mBeans != null && mBeans.size() > 0) {
            return false;
        }
        return true;
    }

    @Override
    protected void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RouteHelper.getInstance().startItemDetail((Activity) mContext, mBeans.get(position).itemId);
    }
}
