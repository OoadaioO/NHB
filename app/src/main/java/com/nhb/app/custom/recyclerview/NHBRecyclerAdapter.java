package com.nhb.app.custom.recyclerview;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhb.app.custom.BR;

import java.lang.ref.WeakReference;


/**
 * <br>***********************************************************************
 * <br> Author:pengxiaofang
 * <br> CreateData:2016-03-10 11:48
 * <br> Version:1.0
 * <br> Description:{@link RecyclerView}的adapter,包含下拉刷新、上拉加载更多、Header、Footer及不同item
 * <br>***********************************************************************
 */
public class NHBRecyclerAdapter extends RecyclerView.Adapter<NHBRecyclerAdapter.RecyclerViewHolder> {

    // 加载更多的阈值,超过此阈值才会显示加载更多
    public static int THRESHOLD_LOAD_MORE = 5;

    //headerView
    public static final int VIEW_HEADER = 1001;
    //footerView
    public static final int VIEW_FOOTER = 1002;
    //loadMoreView
    public static final int VIEW_LOAD_MORE = 1003;

    private NHBRecyclerVM viewModel;
    private RecyclerView recyclerView;
    private int headerViewRes;
    private int footerViewRes;
    private int loadMoreViewRes;
    // 防止列表数据还未填充数据时就被展示出来
    private boolean showHeaderView = false;
    private boolean showFooterView = false;

    private boolean showLoadingMoreView = false;
    private boolean mIsLastPage = false;
    private boolean mInLoading = false;
    private int cacheLoadMoreViewHeight = 0;

    // HeaderLayout的ViewDataBinding
    private ViewDataBinding headerBinding;
    // FooterLayout的ViewDataBinding
    private ViewDataBinding footerBinding;
    // LoadMoreLayout的ViewDataBinding
    private ViewDataBinding loadMoreBinding;
    private Context mContext;

    // header的个数,目前只支持单header模式
    private int headerCount;
    // footer的个数,目前只支持单footer模式
    private int footerCount;
    private final WeakReferenceOnListChangedCallback callback = new WeakReferenceOnListChangedCallback<>(this);

    public NHBRecyclerAdapter(Context context, NHBRecyclerVM viewModel, RecyclerView recyclerView) {
        mContext = context;
        this.viewModel = viewModel;
        this.recyclerView = recyclerView;
        loadMoreViewRes = viewModel.loadMoreView();
        if (0 != loadMoreViewRes) {
            setUpScroll();
        }
    }

    /**
     * 设置Header layout
     *
     * @param layoutId
     */
    public void setHeaderViewRes(@LayoutRes int layoutId) {
        if (0 != layoutId) {
            headerViewRes = layoutId;
            headerCount = 1;
            showHeaderView = true;
            headerBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), headerViewRes, null, false);
            viewModel.headerView = headerBinding.getRoot();
        }
    }

    /**
     * 设置Footer layout
     *
     * @param layoutId
     */
    public void setFooterViewRes(@LayoutRes int layoutId) {
        if (0 != layoutId) {
            footerViewRes = layoutId;
            footerCount = 1;
            showFooterView = true;
            footerBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), footerViewRes, null, false);
            viewModel.footerView = footerBinding.getRoot();
        }
    }

    /**
     * 是否在加载更多数据
     *
     * @param inLoading
     */
    public void setInLoading(boolean inLoading) {
        mInLoading = inLoading;
    }

    public void isLastPage(boolean inLastPage) {
        // 如果业务层无加载更多则此处不做处理
        if (null == loadMoreBinding) {
            return;
        }
        mIsLastPage = inLastPage;
        if (mIsLastPage || getItemCount() < THRESHOLD_LOAD_MORE) {
            // 如果到了最后一页则不显示加载更多
            loadMoreBinding.getRoot().getLayoutParams().height = 0;
        } else {
            loadMoreBinding.getRoot().getLayoutParams().height = cacheLoadMoreViewHeight;
        }
        notifyItemChanged(getItemCount() - 1);
    }

    private void setUpScroll() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 当RecyclerView滚动到底部时显示加载更多view同时获取数据
                int lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                int totalItemCount = getItemCount();
                if (lastVisibleItem >= totalItemCount - 1 && dy > 0 && totalItemCount > THRESHOLD_LOAD_MORE) {
                    // loadMore
                    if (!mIsLastPage && !mInLoading) {
                        mInLoading = true;
                        viewModel.loadMoreData();
                    }
                }
            }
        });
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolder holder;
        if (viewType == VIEW_HEADER) {
            // header
            holder = new RecyclerViewHolder(headerBinding.getRoot());
            holder.setBinding(headerBinding);
        } else if (viewType == VIEW_FOOTER) {
            // footer
            holder = new RecyclerViewHolder(footerBinding.getRoot());
            holder.setBinding(footerBinding);
        } else if (viewType == VIEW_LOAD_MORE) {
            // load more
            loadMoreBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), loadMoreViewRes, parent, false);
            holder = new RecyclerViewHolder(loadMoreBinding.getRoot());
            holder.setBinding(loadMoreBinding);
            cacheLoadMoreViewHeight = holder.getBinding().getRoot().getLayoutParams().height;
        } else {
            // normal
            RecyclerItemVM itemVM = viewModel.getItemVM(viewType);
            // 将Activity/Fragment的事件委托给GMViewModel
            itemVM.setOnActivityActionListener(viewModel.mActivityActionListener);
            ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), itemVM.loadItemView(), parent, false);
            holder = new RecyclerViewHolder(binding.getRoot());
            holder.setBinding(binding);
            holder.setItemVM(itemVM);
        }
        return holder;
    }

    @Override
    public int getItemViewType(int position) {

        if (0 == position && 0 != headerViewRes) {
            // headerView
            return VIEW_HEADER;
        }

        if (position == viewModel.beans.size() + headerCount && 0 != footerViewRes) {
            // footerView
            return VIEW_FOOTER;
        }

        if (position == viewModel.beans.size() + headerCount + footerCount && 0 != loadMoreViewRes) {
            // loadMoreView
            return VIEW_LOAD_MORE;
        }

        // customView
        return viewModel.getItemViewType(position);

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        if (null == holder.getBinding()) {
            return;
        }
        if (viewType != VIEW_HEADER && viewType != VIEW_FOOTER && viewType != VIEW_LOAD_MORE) {
            // 正常item的逻辑处理单独提出一个ViewModel
            RecyclerItemVM itemVM = holder.getItemVM();
            itemVM.setData(viewModel.beans, viewModel.beans.get(position - headerCount), position - headerCount);
            holder.getBinding().setVariable(BR.itemViewModel, itemVM);
//			holder.getBinding().setVariable(BR.position, position - headerCount);
            // 为正常item增加点击监听
            holder.getBinding().getRoot().setClickable(true);
            holder.getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewModel.onItemClick(new WeakReference<>(v), viewModel.beans.get(position - headerCount), position - headerCount);
                }
            });
            holder.getBinding().getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    viewModel.onItemLongClick(new WeakReference<>(v), viewModel.beans.get(position - headerCount), position - headerCount);
                    return false;
                }
            });
        } else {
            // header、footer、loadMore的view处理都在外层viewModel中处理
            holder.getBinding().setVariable(BR.viewModel, viewModel);
        }
        holder.getBinding().executePendingBindings();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        if (null == viewModel || null == viewModel.beans) {
            return;
        }
        // 添加数据改变监听，当数据改变时更新view
        viewModel.beans.addOnListChangedCallback(callback);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        if (null == viewModel || null == viewModel.beans) {
            return;
        }
        // 移除监听
        viewModel.beans.removeOnListChangedCallback(callback);
    }

    @Override
    public int getItemCount() {
        int itemCount = viewModel.beans.size();
        if (0 != headerViewRes && showHeaderView) {
            // header
            itemCount++;
        }
        if (0 != footerViewRes && showFooterView) {
            // footer
            itemCount++;
        }
        if (0 != loadMoreViewRes && showLoadingMoreView) {
            // loadMoreView
            itemCount++;
        }
        return itemCount;
    }

    /**
     * 获取Header的个数
     *
     * @return
     */
    public int getHeaderCount() {
        return headerCount;
    }

    /**
     * 获取Footer的个数
     *
     * @return
     */
    public int getFooterCount() {
        return footerCount;
    }

    /**
     * 对数据改动进行监听
     *
     * @param <T>
     */
    private static class WeakReferenceOnListChangedCallback<T> extends ObservableList.OnListChangedCallback<ObservableList<T>> {
        final WeakReference<NHBRecyclerAdapter> adapterRef;

        WeakReferenceOnListChangedCallback(NHBRecyclerAdapter adapter) {
            this.adapterRef = new WeakReference<>(adapter);
        }

        /**
         * 数据改变
         *
         * @param sender
         */
        @Override
        public void onChanged(ObservableList sender) {
            NHBRecyclerAdapter adapter = adapterRef.get();
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
            NHBRecyclerAdapter adapter = adapterRef.get();
            if (adapter == null) {
                return;
            }
            adapter.notifyItemRangeChanged(positionStart + adapter.getHeaderCount(), itemCount);
        }

        /**
         * 列表中插入一段数据
         * @param sender
         * @param positionStart
         * @param itemCount
         */
        @Override
        public void onItemRangeInserted(ObservableList sender, final int positionStart, final int itemCount) {
            NHBRecyclerAdapter adapter = adapterRef.get();
            if (adapter == null) {
                return;
            }
            adapter.showHeaderView = true;
            adapter.showFooterView = true;
            if (adapter.getItemCount() > THRESHOLD_LOAD_MORE) {
                adapter.showLoadingMoreView = true;
            } else {
                adapter.showLoadingMoreView = false;
            }
            adapter.notifyItemRangeInserted(positionStart + adapter.getHeaderCount() + 1, itemCount);
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
            NHBRecyclerAdapter adapter = adapterRef.get();
            if (adapter == null) {
                return;
            }
            for (int i = 0; i < itemCount; i++) {
                adapter.notifyItemMoved(fromPosition + i + adapter.getHeaderCount(), toPosition + i + adapter.getHeaderCount());
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
            NHBRecyclerAdapter adapter = adapterRef.get();
            if (adapter == null) {
                return;
            }
            adapter.notifyItemRangeRemoved(positionStart + adapter.getHeaderCount(), itemCount);
        }
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding mBinding;
        private RecyclerItemVM mItemVM;

        public RecyclerViewHolder(final View itemView) {
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
