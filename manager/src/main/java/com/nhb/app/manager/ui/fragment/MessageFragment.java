package com.nhb.app.manager.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.fast.library.Adapter.recyclerview.BaseRecyclerAdapter;
import com.fast.library.Adapter.recyclerview.RecyclerViewHolder;
import com.fast.library.tools.ViewTools;
import com.fast.library.ui.AnnotateViewUtils;
import com.fast.library.ui.ContentView;
import com.fast.library.utils.LogUtils;
import com.fast.library.utils.NetUtils;
import com.fast.library.utils.NumberUtils;
import com.fast.library.utils.UIUtils;
import com.fast.library.view.CircleProgressView;
import com.nhb.app.library.base.NHBFragment;
import com.nhb.app.library.event.EventCenter;
import com.nhb.app.library.http.NHBSubscriber;
import com.nhb.app.manager.R;
import com.nhb.app.manager.base.NmConstant;
import com.nhb.app.manager.bean.OrderMessageBean;
import com.nhb.app.manager.event.EventType;
import com.nhb.app.manager.model.ManagerAPI;
import com.nhb.app.manager.ui.activity.OrderDetailActivity;
import com.nhb.app.manager.ui.helper.BGARefreshHelper;
import com.nhb.app.manager.utils.CommonUtils;
import com.nhb.app.manager.view.EmptyView;
import com.nhb.app.zbar.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

import static android.R.attr.fragment;
import static com.nhb.app.manager.R.id.emptyView;

/**
 * 说明：消息
 * <p>
 * 作者：fanly
 * <p>
 * 类型：Class
 * <p>
 * 时间：2016/6/23 9:40
 * <p>
 * 版本：verson 1.0
 */
@ContentView(R.layout.fragment_message)
public class MessageFragment extends NHBFragment implements BaseRecyclerAdapter.OnItemClickListener, BGARefreshHelper.BGARefreshCallBack {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    BGARefreshLayout mBGARefresh;
    CircleProgressView cpv;
    ImageView ivIcon;
    TextView tvText;
    FrameLayout flContent;

    private int currentPage = 1;
    private boolean isLoadingMore = true;
    private MessageAdapter mAdapter;
    private BGARefreshHelper mBgaHelper;
    private LinearLayoutManager linearLayoutManager;
    private List<OrderMessageBean> messageList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (!AnnotateViewUtils.init(this)){
            LogUtils.e("AnnotateViewUtils.init error!");
        }
        fragmentRootView = inflaterView(inflater, container, savedInstanceState);
        cpv = bind(R.id.cpv);
        ivIcon = bind(R.id.iv_icon);
        tvText = bind(R.id.tv_desc);
        flContent = bind(R.id.fl_content);
        mBGARefresh = bind(R.id.bga_refresh);
        ViewTools.GONE(cpv);
        mBgaHelper = new BGARefreshHelper(mBGARefresh,this,true);
        return fragmentRootView;
    }

    private void showEmpty(){
        ViewTools.GONE(flContent);
        ViewTools.VISIBLE(ivIcon);
        ViewTools.VISIBLE(tvText);
        ViewTools.setText(tvText,R.string.no_message);
        ivIcon.setImageResource(R.drawable.empty_message);
        ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoRefresh();
            }
        });
    }

    private void showSuccess(){
        ViewTools.VISIBLE(flContent);
        ViewTools.GONE(ivIcon);
        ViewTools.GONE(tvText);
    }

    private void showFail(){
        ViewTools.GONE(flContent);
        ViewTools.VISIBLE(ivIcon);
        ViewTools.VISIBLE(tvText);
        ViewTools.setText(tvText,R.string.network_error);
        ivIcon.setImageResource(R.drawable.network_error);
        ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoRefresh();
            }
        });
    }

    @Override
    protected void onFirstUserVisible() {
        tvTitle.setText(R.string.nhb);
        bind(R.id.iv_right,true);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.drawable.qrcode);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new MessageAdapter(recyclerView,messageList);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

    }

    @Override
    protected void clickView(View v, int id) {
        switch (id){
            case R.id.iv_right://扫一扫
                showActivity(CaptureActivity.class);
                break;
        }
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void refreshMessageList(EventCenter<String> eventCenter){
        if (eventCenter.type == EventType.MESSAGE_REFRESH){
            onUserVisible();
        }
    }

    @Override
    protected void onUserVisible() {
        autoRefresh();
    }

    @Override
    protected void onUserInvisible() {
    }

    @Override
    public void onItemClick(View view, int position) {
        if (mAdapter.getData().get(position).isClick()){
            Bundle bundle = new Bundle();
            bundle.putParcelable(OrderDetailActivity.ORDER_INFO,mAdapter.getData().get(position));
            showActivity(OrderDetailActivity.class,bundle);
        }
    }

    @Override
    public void onBGARefresh(final BGARefreshLayout refreshLayout) {
        if (NetUtils.isNetConnected()){
            currentPage = 1;
            ManagerAPI.getAPI().getShopOrderMessage(currentPage)
                    .subscribe(new NHBSubscriber<ArrayList<OrderMessageBean>>() {
                        @Override
                        public void onCompleted() {
                            super.onCompleted();
                            refreshLayout.endRefreshing();
                        }

                        @Override
                        public void onError(int code, String msg) {
                            showEmpty();
                        }

                        @Override
                        public void onNext(ArrayList<OrderMessageBean> orderMessageBeen) {
                            if (orderMessageBeen == null || orderMessageBeen.isEmpty()){
                                showEmpty();
                            }else {
                                isLoadingMore = (orderMessageBeen.size() == NumberUtils.toInt(NmConstant.Page.SIZE)) ;
                                mAdapter.refresh(orderMessageBeen);
                                showSuccess();
                            }
                        }
                    });
        }else {
            showFail();
        }
    }

    @Override
    public boolean onBGALoadMore(final BGARefreshLayout refreshLayout) {
        if (NetUtils.isNetConnected()){
            if (isLoadingMore){
                currentPage++;
                ManagerAPI.getAPI().getShopOrderMessage(currentPage)
                        .subscribe(new NHBSubscriber<ArrayList<OrderMessageBean>>() {
                            @Override
                            public void onCompleted() {
                                super.onCompleted();
                                refreshLayout.endLoadingMore();
                            }

                            @Override
                            public void onError(int code, String msg) {
                                shortToast("网络连接错误");
                            }

                            @Override
                            public void onNext(ArrayList<OrderMessageBean> orderMessageBeen) {
                                if (orderMessageBeen != null && !orderMessageBeen.isEmpty()){
                                    isLoadingMore = (orderMessageBeen.size() == NumberUtils.toInt(NmConstant.Page.SIZE)) ;
                                    mAdapter.addAll(orderMessageBeen);
                                }else {
                                    isLoadingMore = false;
                                }
                            }
                        });
                return true;
            }else {
                refreshLayout.endLoadingMore();
                return false;
            }
        }else {
            refreshLayout.endLoadingMore();
            shortToast(R.string.network_error);
            return true;
        }

    }

    class MessageAdapter extends BaseRecyclerAdapter<OrderMessageBean> {

        public MessageAdapter(RecyclerView recyclerView, List<OrderMessageBean> data) {
            super(recyclerView, data);
            addItemType(R.layout.item_message);
        }

        @Override
        public void convert(RecyclerViewHolder holder, OrderMessageBean item, int position) {
            holder.setImage(R.id.iv_custom_pic,item.getCustomPic());
            holder.setText(R.id.tv_custom_name,String.format(UIUtils.getString(R.string.message_card_phone),item.getCustomPhone()));
            if (!item.isClick()){
                holder.setText(R.id.tv_message_state,item.getTradeMsg());
            }else {
                holder.setText(R.id.tv_message_state, CommonUtils.getMessageState(item.getTradeState()));
            }
            holder.setText(R.id.tv_card_number,String.format(UIUtils.getString(R.string.message_card_number),item.getOrderId()));
            holder.setText(R.id.tv_trade_no,String.format(UIUtils.getString(R.string.message_trade_no),item.getTradeNo()));
        }
    }

    public void autoRefresh(){
        if (NetUtils.isNetConnected()){
            mBgaHelper.autoRefresh();
        }else {
            if (mAdapter != null && mAdapter.getData() != null && !mAdapter.getData().isEmpty()){
                shortToast(R.string.network_error);
            }else {
                showFail();
            }
        }
    }
}
