package com.nhb.app.custom.common.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.nhb.app.custom.BR;
import com.nhb.app.custom.R;
import com.nhb.app.custom.bean.ItemCategoryChildBean;
import com.nhb.app.custom.bean.ShopCategoryGroupBean;
import com.nhb.app.custom.databinding.LayoutFilterBinding;
import com.nhb.app.custom.recyclerview.NHBRecyclerAdapter;
import com.nhb.app.custom.viewmodel.CommonFilterVM;
import com.nhb.app.custom.viewmodel.FilterChildVM;
import com.nhb.app.custom.viewmodel.FilterGroupVM;

import java.util.List;


/**
 * 筛选器
 *
 * @author pengxiaofang
 *         统一筛选器
 */
public class CommonFilter extends LinearLayout implements View.OnClickListener {
    public static final int DEFAULT_FIRST_ITEM_GROUP = 0;//综合排序
    public static final String DEFAULT_FIRST_ITEM_GROUP_ID = "";//综合排序
    public static final String SALES_PREFERRED = "0";//销量优先
    public static final String SALES_PREFERRED_DESC = "1";//销量优先
    public static final String ORDER_LIST = "order_list";//列表排列展示
    public static final String ORDER_GRID = "order_grid";//表格排列展示
    private Context mContext;
    private LayoutFilterBinding mDataBinding;
    private CommonFilterVM mCommonFilterVM;

    private FilterGroupVM mItemsGroupVM;
    private FilterChildVM mItemsChildVM;

    private NHBRecyclerAdapter mItemsGroupAdapter;
    private NHBRecyclerAdapter mItemsChildAdapter;

    private List<ItemCategoryChildBean> mItemsChildList;
    private List<ShopCategoryGroupBean> mItemsGroupList;

    public OnTabItemSelectedListener mOnTabItemSelectedListener;

    // 默认筛选条件
    private String mSelectedItemsId = "";//默认为全部
    private String mSelectedTagId = SALES_PREFERRED;//默认为全部
    private String mSelectedOrderId = ORDER_LIST;//默认为第一个

    // 用于记录点选的项目
    private int mSelectedGroupIndex = 1;
    private int mSelectedChildIndex = -1;
    // 是否已经初始化了所选项
    private boolean mIsSelectedItemsInited;
    private String mItemsTitle;

    public CommonFilter(Context context) {
        this(context, null);

    }

    public CommonFilter(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initialize(context);
    }

    private void initialize(final Context context) {
        mDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_filter, this, true);
        mCommonFilterVM = new CommonFilterVM();
        mDataBinding.setVariable(BR.viewModel, mCommonFilterVM);

        mItemsGroupVM = new FilterGroupVM();
        mItemsChildVM = new FilterChildVM();

        mItemsGroupAdapter = new NHBRecyclerAdapter(context, mItemsGroupVM, mDataBinding.filterRcvItemsGroup);
        mDataBinding.filterRcvItemsGroup.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.filterRcvItemsGroup.setAdapter(mItemsGroupAdapter);

        mItemsChildAdapter = new NHBRecyclerAdapter(context, mItemsChildVM, mDataBinding.filterRcvItemsChild);
        mDataBinding.filterRcvItemsChild.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.filterRcvItemsChild.setAdapter(mItemsChildAdapter);

        mDataBinding.filterRlContent.setVisibility(GONE);
        mDataBinding.filterRlItems.setOnClickListener(this);
        mDataBinding.filterRlContent.setOnClickListener(this);
        mDataBinding.filterRlList.setOnClickListener(this);
        mDataBinding.filterRlOrder.setOnClickListener(this);
        mDataBinding.filterRlSales.setOnClickListener(this);

        fetchData();

        mItemsGroupVM.selectItemGroup.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                //刷新一级分类选项
                mItemsGroupAdapter.notifyDataSetChanged();
                ShopCategoryGroupBean tempSelectedItemsGroup = mItemsGroupList.get(mItemsGroupVM.selectItemGroup.get());
                if (DEFAULT_FIRST_ITEM_GROUP == mItemsGroupVM.selectItemGroup.get()) {
                    mSelectedItemsId = DEFAULT_FIRST_ITEM_GROUP_ID;
                    // 更新标题
                    mDataBinding.filterTvItems.setText(tempSelectedItemsGroup.shopCategoryName);
                    if (mOnTabItemSelectedListener != null) {
                        mOnTabItemSelectedListener.onItemSelected(mSelectedItemsId, mSelectedTagId, mSelectedOrderId);
                        hideFilterContent();
                    }
                } else {
                    // 如有二级分类项则展开
                    mItemsChildList = tempSelectedItemsGroup.itemCategory;
                    mItemsChildVM.beans.clear();
                    mItemsChildVM.addAll(mItemsChildList);
                    mItemsChildAdapter.notifyDataSetChanged();
                }
            }
        });

        mItemsChildVM.selectItemChild.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                // 一般情况下，点选二级分类才代表操作完成，所以这时才记录用户点选的一二级分类项
                mItemsChildAdapter.notifyDataSetChanged();
                int position = mItemsChildVM.selectItemChild.get();
                if (mItemsChildList.size() > 0 && null != mItemsChildList.get(position)) {
                    mSelectedItemsId = mItemsChildList.get(position).categoryId;
                    // 更新标题
                    mDataBinding.filterTvItems.setText(mItemsChildList.get(position).categoryName);
                    if (mOnTabItemSelectedListener != null) {
                        mOnTabItemSelectedListener.onItemSelected(mSelectedItemsId, mSelectedTagId, mSelectedOrderId);
                        hideFilterContent();
                    }
                }
            }
        });

        mCommonFilterVM.itemsData.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                mItemsGroupList = mCommonFilterVM.itemsData.get();
                // group
                mItemsGroupVM.beans.clear();
                mItemsGroupVM.addAll(mItemsGroupList);
                mItemsGroupAdapter.notifyDataSetChanged();
                mItemsGroupVM.selectItemGroup.set(mSelectedGroupIndex);
                // child
                if (0 < mItemsGroupList.size() && mItemsGroupList.get(mSelectedGroupIndex).itemCategory != null) {
                    mItemsChildList = mItemsGroupList.get(mSelectedGroupIndex).itemCategory;
                    mItemsChildVM.beans.clear();
                    mItemsChildVM.addAll(mItemsChildList);
                    mItemsChildAdapter.notifyDataSetChanged();

                    if (mSelectedChildIndex != -1 && mSelectedChildIndex < mItemsChildList.size()) {
                        mItemsChildVM.selectItemChild.set(mSelectedChildIndex);
                        setItemTitle(mItemsChildList.get(mSelectedChildIndex).categoryName + "");
                        setSelectedItemsId(mItemsChildList.get(mSelectedChildIndex).categoryId + "");
                        mIsSelectedItemsInited = true;
                    }
                }
                if (!mIsSelectedItemsInited) {
                    initSelectItemInited(mSelectedItemsId);
                }
            }
        });
    }

    /**
     * 通过遍历的方式初始化已选中的筛选条件
     *
     * @param mSelectedItemsId
     */
    private void initSelectItemInited(String mSelectedItemsId) {
        if (TextUtils.isEmpty(mSelectedItemsId) || mItemsGroupList == null || mItemsGroupList.size() == 0) {
            return;
        }

        int groupSize = mItemsGroupList.size();
        for (int i = 0; i < groupSize; i++) {
            List<ItemCategoryChildBean> tempChildList = mItemsGroupList.get(i).itemCategory;
            if (tempChildList == null) {
                break;
            }
            int childSize = tempChildList.size();
            for (int j = 0; j < childSize; j++) {
                ItemCategoryChildBean itemChild = tempChildList.get(j);
                if (itemChild == null) {
                    break;
                }
                // 先比对ID
                if (TextUtils.equals(itemChild.categoryId, mSelectedItemsId)) {
                    mItemsChildList = tempChildList;
                    setItemTitle(itemChild.categoryName);
                    setSelectedItemsIdItem(i, j);
                    return;
                    // 如果ID无匹配项，则比对name
                } else if (TextUtils.equals(itemChild.categoryName, mItemsTitle)) {
                    // 若真找到了，则同时更新ID
                    mItemsChildList = tempChildList;
                    mSelectedItemsId = itemChild.categoryId;
                    setSelectedItemsIdItem(i, j);
                    return;
                }
            }
        }
    }

    private void setSelectedItemsIdItem(int groupIndex, int childIndex) {
        if (DEFAULT_FIRST_ITEM_GROUP == groupIndex) {
            return;
        }
        mSelectedGroupIndex = groupIndex;
        mSelectedChildIndex = childIndex;
        // group
        mItemsGroupVM.beans.clear();
        mItemsGroupVM.addAll(mItemsGroupList);
        mItemsGroupAdapter.notifyDataSetChanged();
        mItemsGroupVM.selectItemGroup.set(mSelectedGroupIndex);
        //child
        mItemsChildVM.beans.clear();
        mItemsChildVM.addAll(mItemsChildList);
        mItemsChildAdapter.notifyDataSetChanged();
        mItemsChildVM.selectItemChild.set(mSelectedChildIndex);

        mIsSelectedItemsInited = true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 点击下面的黑色半透明部分
            case R.id.filter_rl_content:
                hideFilterContent();
                break;
            case R.id.filter_rl_list:
                break;
            // 商品Tab
            case R.id.filter_rl_items:
                if (mDataBinding.filterRlItems.isSelected()) {
                    mDataBinding.filterTvItems.setTextColor(mContext.getResources().getColor(R.color.c_333333));
                    hideFilterContent();
                } else {
                    mDataBinding.filterTvItems.setTextColor(mContext.getResources().getColor(R.color.c_main));
                    mDataBinding.filterRlItems.setSelected(true);
                    mDataBinding.filterRlSales.setSelected(false);
                    mDataBinding.filterRlOrder.setSelected(false);
                    mDataBinding.filterRcvItemsGroup.setVisibility(View.VISIBLE);
                    mDataBinding.filterRcvItemsChild.setVisibility(View.VISIBLE);
                    showFilterContent();
                }
                break;
            case R.id.filter_rl_sales:
                if (mDataBinding.filterRlSales.isSelected()) {
                    mDataBinding.filterTvSales.setTextColor(mContext.getResources().getColor(R.color.c_333333));
                    mDataBinding.filterRlSales.setSelected(false);
                } else {
                    mDataBinding.filterTvSales.setTextColor(mContext.getResources().getColor(R.color.c_main));
                    mDataBinding.filterRlSales.setSelected(true);
                }

                mDataBinding.filterTvItems.setTextColor(mContext.getResources().getColor(R.color.c_333333));
                mDataBinding.filterRlItems.setSelected(false);
                mDataBinding.filterRlOrder.setSelected(false);
                mDataBinding.filterRlContent.setVisibility(View.GONE);

                mSelectedTagId = TextUtils.equals(SALES_PREFERRED_DESC, mSelectedTagId) ? SALES_PREFERRED : SALES_PREFERRED_DESC;
                if (mOnTabItemSelectedListener != null) {
                    mOnTabItemSelectedListener.onItemSelected(mSelectedItemsId, mSelectedTagId, mSelectedOrderId);
                }
                break;
            case R.id.filter_rl_order:
                mDataBinding.filterTvItems.setTextColor(mContext.getResources().getColor(R.color.c_333333));
                mDataBinding.filterRlItems.setSelected(false);
                mDataBinding.filterRlSales.setSelected(false);
                mDataBinding.filterRlOrder.setSelected(true);
                mDataBinding.filterRlContent.setVisibility(View.GONE);

                if (TextUtils.equals(ORDER_LIST, mSelectedOrderId)) {
                    mSelectedOrderId = ORDER_GRID;
                    mDataBinding.filterIvOrder.setImageResource(R.drawable.ic_filter_grid);
                } else {
                    mDataBinding.filterIvOrder.setImageResource(R.drawable.ic_filter_list);
                    mSelectedOrderId = ORDER_LIST;
                }
                if (mOnTabItemSelectedListener != null) {
                    mOnTabItemSelectedListener.onItemSelected(mSelectedItemsId, mSelectedTagId, mSelectedOrderId);
                }
                break;
        }
    }

    private void hideFilterContent() {
        mDataBinding.filterRlItems.setSelected(false);
        mDataBinding.filterRlSales.setSelected(false);
        mDataBinding.filterRlOrder.setSelected(false);
        mDataBinding.filterRlContent.setVisibility(View.GONE);
    }

    private void showFilterContent() {
        if (!mDataBinding.filterRlContent.isShown()) {
            if (mItemsChildList == null || mItemsGroupList == null || mItemsChildList.size() == 0 || mItemsGroupList.size() == 0) {
                mCommonFilterVM.toGetData();
            } else if (TextUtils.equals(DEFAULT_FIRST_ITEM_GROUP_ID, mSelectedItemsId)) {
                mCommonFilterVM.itemsData.notifyChange();
            }
            mDataBinding.filterRlContent.setVisibility(View.VISIBLE);
        }
    }

    public CommonFilter setItemTitle(String itemTitle) {
        mItemsTitle = itemTitle;
        mDataBinding.filterTvItems.setTextColor(mContext.getResources().getColor(R.color.c_main));
        mDataBinding.filterTvItems.setText(itemTitle);
        return this;
    }

    /**
     * 获取数据
     */
    public void fetchData() {
        mCommonFilterVM.toGetData();
    }

    /**
     * 获取商品选中的id
     *
     * @return
     */
    public String getSelectedItemsId() {
        return mSelectedItemsId;
    }

    /**
     * 设置商品初始选中的id
     *
     * @param selectedItemsId
     */
    public CommonFilter setSelectedItemsId(String selectedItemsId) {
        mSelectedItemsId = selectedItemsId;
        return this;
    }

    /**
     * 设置tab点击回调
     *
     * @param onTabItemSelectedListener
     */
    public CommonFilter setOnTabItemSelectedListener(OnTabItemSelectedListener onTabItemSelectedListener) {
        mOnTabItemSelectedListener = onTabItemSelectedListener;
        return this;
    }

    /**
     * tab点击接口
     */
    public interface OnTabItemSelectedListener {
        void onItemSelected(String itemId, String tagId, String orderId);
    }
}
