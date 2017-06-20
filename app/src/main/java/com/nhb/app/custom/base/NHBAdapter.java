package com.nhb.app.custom.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;

/**
 * Created by michaelliu on 11/11/15.
 *
 * @since 5.5.0
 * Adapter基类,view的绑定在ViewHolder中进行
 */
public abstract class NHBAdapter<T> extends BaseAdapter {

    /**
     * 上下文
     */
    protected Context mContext;
    /**
     * 数据
     */
    public List<T> mBeans;
    /**
     * 去重key集合
     */
    private Set<String> hashSet;
    /**
     * 去重的key
     */
    private String mDeduplicationKey;
    /**
     * 获取数据起始位置
     */
    private int startNum = 0;

    public NHBAdapter(@NonNull Context context, List<T> beans) {
        mContext = context;
        mBeans = null == beans ? new ArrayList<T>() : beans;
        startNum = mBeans.size();
    }

    /**
     * @param context
     * @param beans
     * @param deduplicationKey 去重的key
     */
    public NHBAdapter(@NonNull Context context, List<T> beans, String deduplicationKey) {
        mContext = context;
        mBeans = null == beans ? new ArrayList<T>() : beans;
        startNum = mBeans.size();
        mDeduplicationKey = deduplicationKey;
        initializeSourceData(beans);
    }

    /**
     * 插入一个item
     *
     * @param location
     * @param item
     * @return
     */
    public boolean insertItem(int location, T item) {
        try {
            mBeans.add(location, item);
            notifyDataSetChanged();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除一个item
     *
     * @param location
     * @return
     */
    public boolean deleteItem(int location) {
        try {
            mBeans.remove(location);
            notifyDataSetChanged();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除一个item
     *
     * @param item
     * @return
     */
    public boolean deleteItem(T item) {
        try {
            mBeans.remove(item);
            notifyDataSetChanged();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新一个item
     *
     * @param location
     * @param item
     * @return
     */
    public boolean updateItem(int location, T item) {
        try {
            mBeans.set(location, item);
            notifyDataSetChanged();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 初始化去重数据源
     *
     * @param data
     */
    private void initializeSourceData(List<T> data) {
        hashSet = new HashSet<>();
        for (T item : data) {
            try {
                hashSet.add(item.getClass().getDeclaredField(mDeduplicationKey).get(item).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 添加数据并去重
     *
     * @param data
     */
    public void addWithoutDuplicate(List<T> data) {
        if (null == data || data.size() == 0) {
            return;
        }
        if (TextUtils.isEmpty(mDeduplicationKey)) {
            new RuntimeException("please input the deduplicationKey in Constructor").printStackTrace();
            mBeans.addAll(data);
            notifyDataSetChanged();
            return;
        }
        startNum += data.size();
        for (T item : data) {
            try {
                String keyValue = item.getClass().getDeclaredField(mDeduplicationKey).get(item).toString();
                if (hashSet.add(keyValue)) {
                    mBeans.add(item);
                }
            } catch (Exception e) {
                e.printStackTrace();
                mBeans.add(item);
            }
        }
        notifyDataSetChanged();
    }

    /**
     * 添加数据（去重）
     *
     * @param location
     * @param item
     * @return 是否插入成功
     */
    public boolean addWithoutDuplicate(int location, T item) {
        if (TextUtils.isEmpty(mDeduplicationKey)) {
            throw new RuntimeException("please input the deduplicationKey in Constructor");
        }
        if (null == item) {
            return false;
        }
        try {
            if (hashSet.add(item.getClass().getDeclaredField(mDeduplicationKey).get(item).toString())) {
                mBeans.add(location, item);
                notifyDataSetChanged();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            mBeans.add(location, item);
        }
        return false;
    }

    /**
     * 增加数据
     *
     * @param beans
     */
    public void addData(List<T> beans) {
        if (null == beans || mBeans.size() == 0) {
            return;
        }
        mBeans.addAll(beans);
        notifyDataSetChanged();
    }

    /**
     * 获取数据起始位置
     *
     * @return
     */
    public int getStartNum() {
        return startNum;
    }

    /**
     * 重置数据起始位置
     */
    public void resetStartNum() {
        startNum = 0;
    }

    @Override
    public int getCount() {
        return null == mBeans ? 0 : mBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return mBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        T bean = mBeans.get(position);
        ViewHolder viewHolder;
        if (null == convertView) {
            viewHolder = onCreateViewHolder(getItemViewType(position), position, convertView, parent);
            convertView = viewHolder.getView();
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.setPosition(position);
        onBindViewHolder(viewHolder, position, bean, getItemViewType(position));
        return convertView;
    }

    /**
     * 创建ViewHolder
     *
     * @return
     * @see NHBAdapter
     */
    protected abstract ViewHolder onCreateViewHolder(int viewType, int position, View convertView, ViewGroup parent);

    protected abstract void onBindViewHolder(ViewHolder holder, int position, T bean, int viewType);

    /**
     * View holder
     */
    public static abstract class ViewHolder {

        public int mPosition;
        public View mItemView;

        public ViewHolder(View itemView) {
            if (itemView == null) {
                throw new IllegalArgumentException("itemView may not be null");
            }
            mItemView = itemView;
            ButterKnife.bind(this, itemView);
        }

        public abstract View getView();

        /**
         * 设置当前位置
         *
         * @param position
         */
        public void setPosition(int position) {
            mPosition = position;
        }

        /**
         * 获取当前位置
         *
         * @return
         */
        public int getPosition() {
            return mPosition;
        }
    }
}
