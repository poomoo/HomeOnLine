package com.poomoo.homeonline.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.OrdersAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.listeners.CancelClickListener;
import com.poomoo.homeonline.listeners.CommodityClickListener;
import com.poomoo.homeonline.listeners.DeleteClickListener;
import com.poomoo.homeonline.listeners.EvaluateClickListener;
import com.poomoo.homeonline.listeners.PayClickListener;
import com.poomoo.homeonline.listeners.ReFundClickListener;
import com.poomoo.homeonline.listeners.ReceiptClickListener;
import com.poomoo.homeonline.listeners.TraceClickListener;
import com.poomoo.homeonline.listeners.UrgeClickListener;
import com.poomoo.homeonline.presenters.OrderPresenter;
import com.poomoo.homeonline.reject.components.DaggerFragmentComponent;
import com.poomoo.homeonline.reject.modules.FragmentModule;
import com.poomoo.homeonline.ui.activity.CommodityInfoActivity;
import com.poomoo.homeonline.ui.activity.ConfirmOrderActivity;
import com.poomoo.homeonline.ui.activity.EvaluateListActivity;
import com.poomoo.homeonline.ui.activity.ReFundActivity;
import com.poomoo.homeonline.ui.activity.ReFundInfoActivity;
import com.poomoo.homeonline.ui.base.BaseDaggerListFragment;
import com.poomoo.homeonline.ui.custom.ErrorLayout;
import com.poomoo.model.response.RCartCommodityBO;
import com.poomoo.model.response.ROrderBO;
import com.poomoo.model.response.ROrderListBO;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名 MyOrdersFragment
 * 描述 我的订单
 * 作者 李苜菲
 * 日期 2016/7/19 11:19
 */
public class MyOrdersFragment extends BaseDaggerListFragment<ROrderListBO, OrderPresenter> implements PayClickListener, UrgeClickListener, ReceiptClickListener, EvaluateClickListener, TraceClickListener, CancelClickListener, DeleteClickListener, ReFundClickListener, CommodityClickListener {
    public int mCatalog;

    private OrdersAdapter adapter;
    private ROrderListBO.OrderDetails orderDetails;
    private Bundle bundle;
    private ArrayList<RCartCommodityBO> rCartCommodityBOs = new ArrayList<>();
    private RCartCommodityBO cartCommodityBO = new RCartCommodityBO();

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mCatalog = getArguments().getInt("BUNDLE_TYPE", ROrderBO.ORDER_ALL);
    }

    @Override
    protected BaseListAdapter<ROrderListBO> onSetupAdapter() {
        adapter = new OrdersAdapter(getActivity(), BaseListAdapter.ONLY_FOOTER, this, this, this, this, this, this, this, this, this);
        return adapter;
    }

    @Override
    protected void setupFragmentComponent(FragmentModule fragmentModule) {
        DaggerFragmentComponent.builder()
                .fragmentModule(fragmentModule)
                .build()
                .inject(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView.setPadding(0, setDividerSize(), 0, 0);
        setEmptyMsg("您还没有订单哦");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        if (mCatalog == ROrderBO.ORDER_EVALUATE) {
//            onLoadFinishState(action);
//            onLoadResultData(getList());
//        } else if (mCatalog == ROrderBO.ORDER_RECEIPT || mCatalog == ROrderBO.ORDER_FINISH) {
//            onLoadFinishState(action);
//            onLoadResultData(getList());
//        } else {
//        }
        mSwipeRefreshLayout.setEnabled(false);
        successful();
    }

    private List<ROrderListBO> getList() {
        List<ROrderListBO> rOrderListBOs = new ArrayList<>();
        ArrayList<ROrderListBO.OrderDetails> orderDetailses;
        ROrderListBO rOrderListBO;
        ROrderListBO.Order order;
        ROrderListBO.OrderDetails orderDetails;
        for (int i = 0; i < 3; i++) {
            rOrderListBO = new ROrderListBO();
            order = rOrderListBO.new Order();
            order.orderId = "201608231618" + i;
            order.state = mCatalog;
            order.sumMoney = 100.00;
            order.createTime = "2016-08-24 10:11";
            rOrderListBO.order = order;

            orderDetailses = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                orderDetails = rOrderListBO.new OrderDetails();
                orderDetails.listPic = "/upload/company/20160723/20160723112219_938.jpg";
                orderDetails.commodityName = "测试商品" + i + "" + j;
                orderDetails.commodityNum = 1;
                orderDetails.state = mCatalog;
                orderDetails.unitPrice = 50.00;
                orderDetailses.add(orderDetails);
            }
            rOrderListBO.orderDetails = orderDetailses;
            rOrderListBOs.add(rOrderListBO);
        }
        return rOrderListBOs;
    }


    public void succeed(List<ROrderListBO> rOrderListBOs) {
        onLoadFinishState(action);
        LogUtils.d(TAG, "rOrderListBOs:" + rOrderListBOs);
        onLoadResultData(rOrderListBOs);
    }

    public void failed(String msg) {
        if (isNetWorkInvalid(msg)) {
            mAdapter.clear();
            onNetworkInvalid(action);
        } else
            onLoadErrorState(action);
    }

//    /**
//     * 触发下拉刷新事件
//     */
//    @Override
//    public void onRefresh() {
//        super.onRefresh();
//        mCurrentPage = 1;
//        mPresenter.getOrderList(application.getUserId(), mCatalog, mCurrentPage);
//    }


    @Override
    public void onLoading() {
        super.onLoading();
        mPresenter.getOrderList(application.getUserId(), mCatalog, mCurrentPage);
    }

    /**
     * 再错误页面点击重新加载
     */
    @Override
    public void onLoadActiveClick() {
        super.onLoadActiveClick();
        mCurrentPage = 1;
        mPresenter.getOrderList(application.getUserId(), mCatalog, mCurrentPage);
    }

    /**
     * 操作成功
     */
    public void successful() {
        mErrorLayout.setState(ErrorLayout.LOADING, "");
        mCurrentPage = 1;
        mAdapter.clear();
        mPresenter.getOrderList(application.getUserId(), mCatalog, mCurrentPage);
    }

    @Override
    public void cancel(int position) {
        createDialog("确定取消订单?", (dialog, which) -> {
            mErrorLayout.setState(ErrorLayout.LOADING, "");
            mPresenter.cancelOrder(application.getUserId(), mAdapter.getItem(position).order.orderId);
        }).show();
    }

    @Override
    public void delete(int position) {
        createDialog("确定删除订单?", (dialog, which) -> {
            mErrorLayout.setState(ErrorLayout.LOADING, "");
            mPresenter.deleteOrder(application.getUserId(), mAdapter.getItem(position).order.orderId);
        }).show();
    }

    @Override
    public void evaluate(int position) {
//        MyUtils.showToast(getActivity().getApplicationContext(), "评价:" + position);
        bundle = new Bundle();
        bundle.putSerializable(getString(R.string.intent_orderDetails), mAdapter.getItem(position).orderDetails);
        bundle.putString(getString(R.string.intent_orderId), mAdapter.getItem(position).order.orderId);
        bundle.putString(getString(R.string.intent_date), mAdapter.getItem(position).order.createTime);
        openActivity(EvaluateListActivity.class, bundle);
    }

    @Override
    public void pay(int position) {
        rCartCommodityBOs = new ArrayList<>();
        for (ROrderListBO.OrderDetails orderDetails : mAdapter.getItem(position).orderDetails) {
            cartCommodityBO = new RCartCommodityBO();
            cartCommodityBO.commodityId = orderDetails.commodityId;
            cartCommodityBO.listPic = orderDetails.listPic;
            cartCommodityBO.commodityName = orderDetails.commodityName;
            cartCommodityBO.commodityPrice = orderDetails.unitPrice;
            cartCommodityBO.commodityNum = orderDetails.commodityNum;
            cartCommodityBO.commodityDetailsId = orderDetails.commodityDetailsId;
            rCartCommodityBOs.add(cartCommodityBO);
        }
        bundle = new Bundle();
        bundle.putSerializable(getString(R.string.intent_commodityList), rCartCommodityBOs);
        bundle.putDouble(getString(R.string.intent_totalPrice), mAdapter.getItem(position).order.sumMoney);
        bundle.putBoolean(getString(R.string.intent_isFreePostage), mAdapter.getItem(position).order.deliveryFee == 0.0 ? true : false);
        bundle.putBoolean(getString(R.string.intent_value), true);
        bundle.putInt(getString(R.string.intent_deliveryId), mAdapter.getItem(position).order.deliveryId);
        bundle.putDouble(getString(R.string.intent_deliveryFee), mAdapter.getItem(position).order.deliveryFee);
        bundle.putString(getString(R.string.intent_orderId), mAdapter.getItem(position).order.orderId);
        bundle.putString(getString(R.string.intent_commodityName), mAdapter.getItem(position).orderDetails.get(0).commodityName);
        openActivity(ConfirmOrderActivity.class, bundle);
    }

    @Override
    public void receipt(int position) {
        createDialog("确定收货?", (dialog, which) -> {
            mErrorLayout.setState(ErrorLayout.LOADING, "");
            mPresenter.confirm(mAdapter.getItem(position).order.orderId);
        }).show();
    }

    @Override
    public void trace(int position) {
        MyUtils.showToast(getActivity().getApplicationContext(), "查看物流:" + position);
    }

    @Override
    public void urge(int position) {
        MyUtils.showToast(getActivity().getApplicationContext(), "催单成功");
    }

    @Override
    public void refund(int groupPos, int childPos) {
        bundle = new Bundle();
        orderDetails = mAdapter.getItem(groupPos).orderDetails.get(childPos);
        if (orderDetails.isReturnGood) {
            bundle = new Bundle();
            bundle.putString(getString(R.string.intent_value), orderDetails.returnGoodId);
            openActivity(ReFundInfoActivity.class, bundle);
        } else {
            bundle.putString(getString(R.string.intent_commodityId), orderDetails.commodityId + "");
            bundle.putString(getString(R.string.intent_commodityDetailId), orderDetails.commodityDetailsId + "");
            bundle.putString(getString(R.string.intent_orderId), mAdapter.getItem(groupPos).order.orderId);
            bundle.putString(getString(R.string.intent_orderDetailId), orderDetails.id + "");
            bundle.putInt(getString(R.string.intent_count), orderDetails.commodityNum);
            bundle.putDouble(getString(R.string.intent_amount), orderDetails.unitPrice);
            bundle.putBoolean(getString(R.string.intent_value), true);
            openActivity(ReFundActivity.class, bundle);
        }
    }

    @Override
    public void click(int groupPos, int childPos) {
        ROrderListBO rOrderListBO = mAdapter.getItem(groupPos);
        bundle = new Bundle();
        bundle.putInt(getString(R.string.intent_commodityId), rOrderListBO.orderDetails.get(childPos).commodityId);
        bundle.putInt(getString(R.string.intent_commodityDetailId), rOrderListBO.orderDetails.get(childPos).commodityDetailsId);
        bundle.putInt(getString(R.string.intent_commodityType), 1);
        openActivity(CommodityInfoActivity.class, bundle);
    }
}
