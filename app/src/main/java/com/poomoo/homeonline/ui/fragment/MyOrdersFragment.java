package com.poomoo.homeonline.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.OrdersAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.listeners.CancelClickListener;
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
import com.poomoo.homeonline.ui.activity.EvaluateListActivity;
import com.poomoo.homeonline.ui.activity.ReFundActivity;
import com.poomoo.homeonline.ui.base.BaseDaggerListFragment;
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
public class MyOrdersFragment extends BaseDaggerListFragment<ROrderListBO, OrderPresenter> implements BaseListAdapter.OnItemClickListener, PayClickListener, UrgeClickListener, ReceiptClickListener, EvaluateClickListener, TraceClickListener, CancelClickListener, DeleteClickListener, ReFundClickListener {
    public int mCatalog;

    private OrdersAdapter adapter;
    private ROrderListBO.OrderDetails orderDetails;
    private Bundle bundle;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mCatalog = getArguments().getInt("BUNDLE_TYPE", ROrderBO.ORDER_ALL);
    }

    @Override
    protected BaseListAdapter<ROrderListBO> onSetupAdapter() {
        adapter = new OrdersAdapter(getActivity(), BaseListAdapter.ONLY_FOOTER, this, this, this, this, this, this, this, this);
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
        mAdapter.setOnItemClickListener(this);
        setEmptyMsg("您还没有订单哦");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mCatalog == ROrderBO.ORDER_EVALUATE) {
            onLoadFinishState(action);
            onLoadResultData(getList());
        } else if (mCatalog == ROrderBO.ORDER_RECEIPT || mCatalog == ROrderBO.ORDER_FINISH) {
            onLoadFinishState(action);
            onLoadResultData(getList());
        } else {
            mPresenter.getOrderList(application.getUserId(), mCatalog, mCurrentPage);
        }
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

    @Override
    public void onItemClick(int position, long id, View view) {
//        Bundle bundle = new Bundle();
//        bundle.putInt(getString(R.string.intent_value), adapter.getItem(position).jobId);
//        openActivity(JobInfoActivity.class, bundle);
    }

    public void succeed(List<ROrderListBO> rOrderListBOs) {
        onLoadFinishState(action);
        LogUtils.d(TAG, "rOrderListBOs大小" + rOrderListBOs.size());
        LogUtils.d(TAG, "rOrderListBOs:" + rOrderListBOs);
        onLoadResultData(rOrderListBOs);
    }

    public void failed(String msg) {
        if (isNetWorkInvalid(msg))
            onNetworkInvalid(action);
        else
            onLoadErrorState(action);
    }

    /**
     * 触发下拉刷新事件
     */
    @Override
    public void onRefresh() {
        super.onRefresh();
        mCurrentPage = 1;
        mPresenter.getOrderList(application.getUserId(), mCatalog, mCurrentPage);
    }


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

    @Override
    public void cancel(int position) {
        MyUtils.showToast(getActivity().getApplicationContext(), "取消订单:" + position);
    }

    @Override
    public void delete(int position) {
        MyUtils.showToast(getActivity().getApplicationContext(), "删除订单:" + position);
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
        MyUtils.showToast(getActivity().getApplicationContext(), "付款:" + position);
    }

    @Override
    public void receipt(int position) {
        MyUtils.showToast(getActivity().getApplicationContext(), "确认收货:" + position);
        adapter.removeItem(position);
    }

    @Override
    public void trace(int position) {
        MyUtils.showToast(getActivity().getApplicationContext(), "查看物流:" + position);
    }

    @Override
    public void urge(int position) {
        MyUtils.showToast(getActivity().getApplicationContext(), "催单:" + position);
    }

    @Override
    public void refund(int groupPos, int childPos) {
        bundle = new Bundle();
        orderDetails = mAdapter.getItem(groupPos).orderDetails.get(childPos);
        if (orderDetails.isReturnGood) {

        } else {
            bundle.putString(getString(R.string.intent_commodityId), orderDetails.commodityId + "");
            bundle.putString(getString(R.string.intent_commodityDetailId), orderDetails.commodityDetailsId + "");
            bundle.putString(getString(R.string.intent_orderId), mAdapter.getItem(groupPos).order.orderId);
            bundle.putString(getString(R.string.intent_orderDetailId), orderDetails.id + "");
            bundle.putInt(getString(R.string.intent_count), orderDetails.commodityNum);
            bundle.putDouble(getString(R.string.intent_amount), orderDetails.unitPrice);
            openActivity(ReFundActivity.class, bundle);
        }
    }

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        LogUtils.d(TAG, "onHiddenChanged" + hidden);
//        if (!hidden) {
//            onLoadResultData(getList());
//        }
//    }

}
