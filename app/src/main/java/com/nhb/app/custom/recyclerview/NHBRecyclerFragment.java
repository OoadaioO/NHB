package com.nhb.app.custom.recyclerview;

import android.databinding.Observable;
import android.graphics.Canvas;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.nhb.app.custom.R;
import com.nhb.app.custom.base.BaseFragment;
import com.nhb.app.custom.base.FetchDataViewModel;
import com.nhb.app.custom.common.dialog.CommonDialog;
import com.nhb.app.custom.databinding.NhbRecyclerViewBinding;
import com.nhb.app.custom.utils.ResourceUtil;

import java.lang.ref.WeakReference;


/**
 * <br>***********************************************************************
 * <br> Author:pengxiaofang
 * <br> CreateData:2016-03-10 11:48
 * <br> Version:1.0
 * <br> Description:RecyclerView的Fragment
 * <br>***********************************************************************
 */
public class NHBRecyclerFragment extends BaseFragment<NHBRecyclerVM, NhbRecyclerViewBinding> {

    protected NHBRecyclerAdapter adapter;
    private int cacheHeaderLayout;
    private int cacheFooterLayout;
    private boolean isItemTouchAnimator;
    private OnActionListener mOnDeleteActionListener;

    /**
     * 设置ViewModel
     *
     * @param recyclerVM<? extend GMRecyclerVM>
     *                     需要为GMRecyclerVM的子类
     * @return
     */
    public NHBRecyclerFragment setViewModel(NHBRecyclerVM recyclerVM) {
        setViewModel(recyclerVM, false);
        return this;
    }

    /**
     * 设置ViewModel
     *
     * @param recyclerVM
     * @param isItemTouchAnimator 设置RecyclerView动画
     * @return
     */
    public NHBRecyclerFragment setViewModel(NHBRecyclerVM recyclerVM, boolean isItemTouchAnimator) {
        viewModel = recyclerVM;
        /**
         * 此时还没有初始化Adapter，先将HeaderLayout缓存起来
         */
        if (0 != viewModel.headerLayout.get()) {
            cacheHeaderLayout = viewModel.headerLayout.get();
        }
        /**
         * 此时还没有初始化Adapter，先将FooterLayout缓存起来
         */
        if (0 != viewModel.footerLayout.get()) {
            cacheFooterLayout = viewModel.footerLayout.get();
        }
        /**
         * 当HeaderLayout属性变化时更新HeaderLayout
         */
        viewModel.headerLayout.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (null != adapter) {
                    adapter.setHeaderViewRes(viewModel.headerLayout.get());
                } else {
                    cacheHeaderLayout = viewModel.headerLayout.get();
                }
            }
        });
        /**
         * 当FooterLayout属性变化时更新FooterLayout
         */
        viewModel.footerLayout.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (null != adapter) {
                    adapter.setFooterViewRes(viewModel.footerLayout.get());
                } else {
                    cacheFooterLayout = viewModel.footerLayout.get();
                }
            }
        });

        this.isItemTouchAnimator = isItemTouchAnimator;
        return this;
    }

    @Override
    protected int loadLayoutId() {
        return R.layout.nhb_recycler_view;
    }

    @Override
    protected NHBRecyclerVM loadViewModel() {
        return viewModel;
    }

    @Override
    protected void initialize() {
        // 设置adapter参数
        adapter = new NHBRecyclerAdapter(getActivity(), viewModel, viewDataBinding.gmRecyclerView);
        if (0 != cacheHeaderLayout) {
            adapter.setHeaderViewRes(cacheHeaderLayout);
        }
        if (0 != cacheFooterLayout) {
            adapter.setFooterViewRes(cacheFooterLayout);
        }
        final RecyclerView.LayoutManager manager = viewModel.getLayoutManager(new WeakReference<>(viewDataBinding.gmRecyclerView));
        // 当列表为GradView时Header需要合并第一行
        if (manager instanceof GridLayoutManager) {
            ((GridLayoutManager) manager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int viewType = adapter.getItemViewType(position);
                    if (viewType == NHBRecyclerAdapter.VIEW_HEADER || viewType == NHBRecyclerAdapter.VIEW_FOOTER || viewType == NHBRecyclerAdapter.VIEW_LOAD_MORE) {
                        return ((GridLayoutManager) manager).getSpanCount();
                    }
                    return 1;
                }
            });
        }
        // 设置LayoutManager（控制item的展示样式）
        viewDataBinding.gmRecyclerView.setLayoutManager(manager);
        // 设置ItemDecoration（控制item的分割线）
        RecyclerView.ItemDecoration decoration = viewModel.getItemDecoration(new WeakReference<>(viewDataBinding.gmRecyclerView));
        if (decoration != null) {
            viewDataBinding.gmRecyclerView.addItemDecoration(decoration);
        }
        // 设置下拉刷新组件
        viewDataBinding.gmRecyclerSwipeRefresh.setColorSchemeResources(R.color.c_main);
        viewDataBinding.gmRecyclerSwipeRefresh.setSize(SwipeRefreshLayout.LARGE);
        viewDataBinding.gmRecyclerView.setItemAnimator(null);
        viewDataBinding.gmRecyclerSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewDataBinding.gmRecyclerSwipeRefresh.setRefreshing(true);
                viewModel.refreshData();
            }
        });
        viewDataBinding.gmRecyclerSwipeRefresh.setEnabled(viewModel.enablePullToRefresh());
        // resultCode，改变下拉刷新及加载更多view的显示
        viewModel.resultCode.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                // 重置loading状态不需要处理
                if (viewModel.resultCode.get() != FetchDataViewModel.CODE_INIT) {
                    viewDataBinding.gmRecyclerSwipeRefresh.setRefreshing(false);
                    adapter.setInLoading(false);
                }
            }
        });
        viewModel.isLastPage.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                adapter.isLastPage(viewModel.isLastPage.get());
            }
        });
        // 获取数据
        viewModel.fetchRemoteData();
        viewDataBinding.gmRecyclerView.setAdapter(adapter);
        if (isItemTouchAnimator) {
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mCallback);
            itemTouchHelper.attachToRecyclerView(viewDataBinding.gmRecyclerView);
        }
//		viewDataBinding.gmRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
//			@Override
//			public void onItemClick(View view, int position) {
//				int clickItemType = adapter.getItemViewType(position);
//				if (clickItemType == NHBRecyclerAdapter.VIEW_HEADER || clickItemType == NHBRecyclerAdapter.VIEW_FOOTER || clickItemType == NHBRecyclerAdapter.VIEW_LOAD_MORE){
//					return;
//				}
//				int normalItemPosition = position - adapter.getHeaderCount();
//				viewModel.onItemClick(new WeakReference<>(view), viewModel.beans.get(normalItemPosition), normalItemPosition);
//			}
//		}));
    }

    //0则不执行拖动或者滑动
    ItemTouchHelper.Callback mCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {
        /**
         * @param recyclerView
         * @param viewHolder 拖动的ViewHolder
         * @param target 目标位置的ViewHolder
         * @return
         */
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//            int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
//            int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position
//            if (fromPosition < toPosition) {
//                //分别把中间所有的item的位置重新交换
//                for (int i = fromPosition; i < toPosition; i++) {
//                    Collections.swap(viewModel.beans, i, i + 1);
//                }
//            } else {
//                for (int i = fromPosition; i > toPosition; i--) {
//                    Collections.swap(viewModel.beans, i, i - 1);
//                }
//            }
//            adapter.notifyItemMoved(fromPosition, toPosition);
            //返回true表示执行拖动
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            new CommonDialog(mContext, ResourceUtil.getString(R.string.operate_confirm), ResourceUtil.getString(R.string.confirm_delete), ResourceUtil.getString(R.string.cancel), ResourceUtil.getString(R.string.confirm), new CommonDialog.OnActionListener() {
                @Override
                public void clickConfirm() {
                    if (mOnDeleteActionListener != null) {
                        mOnDeleteActionListener.clickConfirm(position);
                    }
                }
            }).show();
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
//                //左右滑动时改变Item的透明度
//                final float alpha = (1 - Math.abs(dX) / (float) viewHolder.itemView.getWidth()) * 0.5f;
//                viewHolder.itemView.setAlpha(alpha);
//                viewHolder.itemView.setTranslationX(dX);
//            }
        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            super.onSelectedChanged(viewHolder, actionState);
            //当选中Item时候会调用该方法，重写此方法可以实现选中时候的一些动画逻辑
            Log.v("zxy", "onSelectedChanged");
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            //当动画已经结束的时候调用该方法，重写此方法可以实现恢复Item的初始状态
            Log.v("zxy", "clearView");
        }
    };

    public NHBRecyclerFragment setOnDeleteActionListener(OnActionListener listener) {
        mOnDeleteActionListener = listener;
        return this;
    }

    public interface OnActionListener {
        void clickConfirm(int position);
    }
}
