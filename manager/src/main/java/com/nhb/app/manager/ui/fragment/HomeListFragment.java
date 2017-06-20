package com.nhb.app.manager.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fast.library.Adapter.recyclerview.BaseRecyclerAdapter;
import com.fast.library.Adapter.recyclerview.RecyclerViewHolder;
import com.fast.library.glide.GlideLoader;
import com.fast.library.span.SpanSetting;
import com.fast.library.span.SpanTextUtils;
import com.nhb.app.library.http.NHBResponse;
import com.nhb.app.library.http.NHBSubscriber;
import com.nhb.app.manager.R;
import com.nhb.app.manager.bean.GoodsBean;
import com.nhb.app.manager.model.ManagerAPI;

import java.util.ArrayList;
import java.util.List;


/**
 * 说明：首页列表
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/6/24 0:26
 * <p/>
 * 版本：verson 1.0
 */
public class HomeListFragment extends BaseListFragment<GoodsBean>{

    private GoodsAdapter mAdapter;
//    当前类型
    private int currentType;
//    页数
    private int currentPage = 0;

    @Override
    protected BaseRecyclerAdapter<GoodsBean> getAdapter() {
        mAdapter = new GoodsAdapter(recyclerView,null);
        if (getArguments() != null){
            currentType = getArguments().getInt(HomeFragment.TYPE);
        }
        return mAdapter;
    }

    @Override
    protected void onFirstUserVisible() {
        super.onFirstUserVisible();
        if (currentType == HomeFragment.SHELVES_LOADING){
            refreshItems();
        }
    }

    @Override
    protected void onUserVisible() {
        super.onUserVisible();
        if (currentType != HomeFragment.SHELVES_LOADING){
            if (mAdapter.getData().isEmpty()){
                refreshItems();
            }
        }
    }

    private void refreshItems(){
        emptyView.setLoading();
        currentPage++;
        ManagerAPI.getAPI().getHomeItems(currentPage,currentType)
                .subscribe(new NHBSubscriber<ArrayList<GoodsBean>>() {
                    @Override
                    public void onError(int code, String msg) {
                        if (code == NHBResponse.ERR_RESPONSE_FORMAT){
                            showLoginActivity();
                            return;
                        }
                        shortToast(msg);
                        currentPage--;
                        if (code == NHBResponse.ERR_NETWORK){
//                           网络错误
                            emptyView.setNetError(getString(R.string.network_error), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    refreshItems();
                                }
                            });
                        }else{
//                           数据为空
                            emptyView.setEmpty(getString(R.string.empty_goods), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    refreshItems();
                                }
                            });
                        }
                    }

                    @Override
                    public void onNext(ArrayList<GoodsBean> items) {
                        if (mAdapter != null && items != null && !items.isEmpty()){
                            mAdapter.refresh(items);
                            emptyView.setSuccess();
                        }else {
//                           数据为空
                            emptyView.setEmpty(getString(R.string.empty_goods), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    refreshItems();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        swipeRefresh.setRefreshing(false);
                    }
                });
    }


    @Override
    public void onRefresh() {
        refreshItems();
    }

    class GoodsAdapter extends BaseRecyclerAdapter<GoodsBean>{

        public GoodsAdapter(RecyclerView recyclerView, List<GoodsBean> data) {
            super(recyclerView, data);
            addItemType(R.layout.item_goods);
        }

        @Override
        public void convert(RecyclerViewHolder holder, GoodsBean item, int position) {
            TextView tvPayPrice = holder.getTextView(R.id.tv_pay_price);
            SpanSetting signSetting = new SpanSetting().setCharSequence("￥").setFontSize(14);
            SpanSetting priceSetting = new SpanSetting().setCharSequence(item.price).setFontSize(16);
            SpanTextUtils.setText(tvPayPrice,signSetting,priceSetting);
            GlideLoader.into(item.itemPic,holder.getImageView(R.id.iv_pic));
            holder.setText(R.id.tv_title,item.itemName);
            holder.setText(R.id.tv_buy_count,"购买"+item.buyCount+"人");
        }
    }

}
