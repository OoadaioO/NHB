package com.nhb.app.custom.ui.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nhb.app.custom.R;
import com.nhb.app.custom.base.NHBAdapter;
import com.nhb.app.custom.common.bean.TemplateSubItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-07-07 17:03
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class HomeItemsAdapter extends NHBAdapter<TemplateSubItem> {
    public HomeItemsAdapter(@NonNull Context context, List<TemplateSubItem> beans) {
        super(context, beans);
    }

    @Override
    protected ViewHolder onCreateViewHolder(int viewType, int position, View convertView, ViewGroup parent) {
        return new ItemsViewHolder(View.inflate(mContext, R.layout.item_home_items, null));
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, int position, TemplateSubItem bean, int viewType) {
        ItemsViewHolder viewHolder = (ItemsViewHolder) holder;
        if (!TextUtils.isEmpty(bean.itemPic)) {
            Picasso.with(mContext).load(bean.itemPic).into(viewHolder.items_img);
//            Picasso.with(mContext).load(bean.itemPic).placeholder(R.drawable.icon_gray_logo).error(R.drawable.icon_gray_logo).into(viewHolder.items_img);
        } else {
            viewHolder.items_img.setImageResource(R.drawable.icon_gray_logo);
        }
    }

    public class ItemsViewHolder extends ViewHolder {

        @Bind(R.id.items_img)
        public ImageView items_img;

        public ItemsViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public View getView() {
            return mItemView;
        }
    }
}
