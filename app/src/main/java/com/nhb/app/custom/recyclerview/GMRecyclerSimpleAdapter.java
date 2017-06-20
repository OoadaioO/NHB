package com.nhb.app.custom.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fast.library.utils.LogUtils;
import com.nhb.app.custom.BR;
import com.nhb.app.custom.base.BaseActivity;
import com.nhb.app.custom.base.NHBViewModel;
import com.nhb.app.custom.base.OnItemClickListener;

import java.lang.ref.WeakReference;

/**
 * <p>***********************************************************************
 * <p> Author: pengxiaofang
 * <p> CreateData: 2016-07-06 10:41
 * <p> Version: 1.0
 * <p> Description: 简单的RecyclerAdapter,不支持下拉刷新,不支持上拉加载更多
 * <p>***********************************************************************
 */

public class GMRecyclerSimpleAdapter extends RecyclerView.Adapter<GMRecyclerSimpleAdapter.RecyclerSimpleViewHolder> {

    final String TAG = "GMRecyclerSimpleAdapter";

    public ObservableArrayList beans;
    public OnItemClickListener onItemClickListener;
    public ItemVMFactory itemVMFactory;
    private final GMRecyclerSimpleAdapter.WeakReferenceOnListChangedCallback callback = new GMRecyclerSimpleAdapter.WeakReferenceOnListChangedCallback<>(this);

    public GMRecyclerSimpleAdapter(ObservableArrayList beans, ItemVMFactory itemVMFactory, OnItemClickListener onItemClickListener) {
        this.beans = beans;
        this.itemVMFactory = itemVMFactory;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerSimpleViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        RecyclerItemVM itemVM = itemVMFactory.getItemVM(viewType);
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), itemVM.loadItemView(), parent, false);
        RecyclerSimpleViewHolder holder = new GMRecyclerSimpleAdapter.RecyclerSimpleViewHolder(binding.getRoot());
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

    @Override
    public void onBindViewHolder(RecyclerSimpleViewHolder holder, final int position) {
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

    @Override
    public int getItemCount() {
        return null == beans ? 0 : beans.size();
    }

    @Override
    public int getItemViewType(int position) {
        return itemVMFactory.getItemViewType(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        if (null == beans) {
            return;
        }
        // 添加数据改变监听，当数据改变时更新view
        beans.addOnListChangedCallback(callback);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        if (null == beans) {
            return;
        }
        // 移除监听
        beans.removeOnListChangedCallback(callback);
    }

    /**
     * 对数据改动进行监听
     *
     * @param <T>
     */
    private static class WeakReferenceOnListChangedCallback<T> extends ObservableList.OnListChangedCallback<ObservableList<T>> {
        final WeakReference<GMRecyclerSimpleAdapter> adapterRef;

        WeakReferenceOnListChangedCallback(GMRecyclerSimpleAdapter adapter) {
            this.adapterRef = new WeakReference<>(adapter);
        }

        /**
         * 数据改变
         *
         * @param sender
         */
        @Override
        public void onChanged(ObservableList sender) {
            GMRecyclerSimpleAdapter adapter = adapterRef.get();
            if (adapter == null) {
                return;
            }
            adapter.notifyDataSetChanged();
        }

        /**
         * 列表中一段数据发生改变
         *
         * @param sender
         * @param positionStart
         * @param itemCount
         */
        @Override
        public void onItemRangeChanged(ObservableList sender, final int positionStart, final int itemCount) {
            GMRecyclerSimpleAdapter adapter = adapterRef.get();
            if (adapter == null) {
                return;
            }
            adapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        /**
         * 列表中插入一段数据
         *
         * @param sender
         * @param positionStart
         * @param itemCount
         */
        @Override
        public void onItemRangeInserted(ObservableList sender, final int positionStart, final int itemCount) {
            GMRecyclerSimpleAdapter adapter = adapterRef.get();
            if (adapter == null) {
                return;
            }
            adapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        /**
         * 列表中移动一段数据
         *
         * @param sender
         * @param fromPosition
         * @param toPosition
         * @param itemCount
         */
        @Override
        public void onItemRangeMoved(ObservableList sender, final int fromPosition, final int toPosition, final int itemCount) {
            GMRecyclerSimpleAdapter adapter = adapterRef.get();
            if (adapter == null) {
                return;
            }
            for (int i = 0; i < itemCount; i++) {
                adapter.notifyItemMoved(fromPosition + i, toPosition + i);
            }
        }

        /**
         * 列表中移除一段数据
         *
         * @param sender
         * @param positionStart
         * @param itemCount
         */
        @Override
        public void onItemRangeRemoved(ObservableList sender, final int positionStart, final int itemCount) {
            GMRecyclerSimpleAdapter adapter = adapterRef.get();
            if (adapter == null) {
                return;
            }
            adapter.notifyItemRangeRemoved(positionStart, itemCount);
        }
    }

    class RecyclerSimpleViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding mBinding;
        private RecyclerItemVM mItemVM;

        public RecyclerSimpleViewHolder(final View itemView) {
            super(itemView);
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
    }
}
