package com.nhb.app.custom.base;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fast.library.utils.LogUtils;
import com.nhb.app.custom.BR;
import com.nhb.app.custom.recyclerview.ItemVMFactory;
import com.nhb.app.custom.recyclerview.RecyclerItemVM;

/**
 * <p>***********************************************************************
 * <p> Author: pengxiaofang
 * <p> CreateData: 2016-08-03 14:44
 * <p> Version: 1.0
 * <p> Description: 继承自BaseAdapter,绑定了ViewMode,适用于需要使用BaseAdapter的View
 * <p>
 * <p>***********************************************************************
 */

public class NHBBaseAdapter extends BaseAdapter {

    private static final String TAG = "GMBaseAdapter";

    public ObservableArrayList beans;
    public OnItemClickListener onItemClickListener;
    public ItemVMFactory itemVMFactory;

    public NHBBaseAdapter(ObservableArrayList beans, ItemVMFactory itemVMFactory, OnItemClickListener onItemClickListener) {
        this.beans = beans;
        this.itemVMFactory = itemVMFactory;
        this.onItemClickListener = onItemClickListener;
        setDataObserver();
    }

    /**
     * 设置数据改变监听,数据改变后刷新页面
     */
    private void setDataObserver(){
        if (null == beans){
            return;
        }
        beans.addOnListChangedCallback(new ObservableList.OnListChangedCallback() {
            @Override
            public void onChanged(ObservableList observableList) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList observableList, int i, int i1) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeInserted(ObservableList observableList, int i, int i1) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeMoved(ObservableList observableList, int i, int i1, int i2) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeRemoved(ObservableList observableList, int i, int i1) {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getCount() {
        return null == beans ? 0 : beans.size();
    }

    @Override
    public int getItemViewType(int position) {
        return itemVMFactory.getItemViewType(position);
    }

    @Override
    public Object getItem(int position) {
        return beans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GMBaseViewHolder viewHolder;
        if (null == convertView) {
            viewHolder = createViewHolder(position, convertView, parent);
            convertView = viewHolder.getItemView();
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GMBaseViewHolder) convertView.getTag();
        }
        bindViewHolder(viewHolder, position);
        return convertView;
    }

    private GMBaseViewHolder createViewHolder(int position, View convertView, final ViewGroup parent) {
        RecyclerItemVM itemVM = itemVMFactory.getItemVM(getItemViewType(position));
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), itemVM.loadItemView(), parent, false);
        GMBaseViewHolder holder = new GMBaseViewHolder(binding.getRoot());
        holder.setBinding(binding);
        holder.setItemVM(itemVM);
        itemVM.setOnActivityActionListener(new NHBViewModel.ActivityActionListener() {
            @Override
            public void startActivity(Intent intent) {
                parent.getContext().startActivity(intent);
            }

            @Override
            public void startActivityForResult(Intent intent, int requestCode) {
                Context context = parent.getContext();
                if (context instanceof BaseActivity) {
                    ((BaseActivity) context).startActivityForResult(intent, requestCode);
                } else {
                    LogUtils.d(TAG, "please make the activity extend BaseActivity {@link BaseActivity}");
                }
            }

            @Override
            public void finishActivity() {
                Context context = parent.getContext();
                if (context instanceof BaseActivity) {
                    ((BaseActivity) context).finish();
                } else {
                    LogUtils.d(TAG, "please make the activity extend BaseActivity {@link BaseActivity}");
                }
            }

            @Override
            public void finishActivityForResult(int resultCode) {
                Context context = parent.getContext();
                if (context instanceof BaseActivity) {
                    ((BaseActivity) context).setResult(resultCode);
                    ((BaseActivity) context).finish();
                } else {
                    LogUtils.d(TAG, "please make the activity extend BaseActivity {@link BaseActivity}");
                }
            }

            @Override
            public void finishActivityForResult(int resultCode, Intent intent) {
                Context context = parent.getContext();
                if (context instanceof BaseActivity) {
                    ((BaseActivity) context).setResult(resultCode, intent);
                    ((BaseActivity) context).finish();
                } else {
                    LogUtils.d(TAG, "please make the activity extend BaseActivity {@link BaseActivity}");
                }
            }
        });
        return holder;
    }

    private void bindViewHolder(GMBaseViewHolder holder, final int position) {
        if (null == holder.getBinding()) {
            return;
        }
        RecyclerItemVM itemVM = holder.getItemVM();
        itemVM.setData(beans, beans.get(position), position);
        holder.getBinding().setVariable(BR.itemViewModel, itemVM);
        // 为正常item增加点击监听
        if (null != onItemClickListener) {
            holder.getBinding().getRoot().setClickable(true);
            holder.getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, position);
                }
            });
        }
    }

    private static class GMBaseViewHolder {

        private ViewDataBinding mBinding;
        private RecyclerItemVM mItemVM;
        private View itemView;

        public GMBaseViewHolder(final View itemView) {
            this.itemView = itemView;
        }

        public ViewDataBinding getBinding() {
            return mBinding;
        }

        public void setBinding(ViewDataBinding binding) {
            mBinding = binding;
        }

        public RecyclerItemVM getItemVM() {
            return mItemVM;
        }

        public void setItemVM(RecyclerItemVM itemVM) {
            mItemVM = itemVM;
        }

        public View getItemView() {
            return itemView;
        }
    }
}
