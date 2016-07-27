package com.poomoo.homeonline.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.adapter.OrdersAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.listeners.CancelClickListener;
import com.poomoo.homeonline.listeners.DeleteClickListener;
import com.poomoo.homeonline.listeners.EvaluateClickListener;
import com.poomoo.homeonline.listeners.PayClickListener;
import com.poomoo.homeonline.listeners.ReceiptClickListener;
import com.poomoo.homeonline.listeners.TraceClickListener;
import com.poomoo.homeonline.listeners.UrgeClickListener;
import com.poomoo.homeonline.ui.base.BaseListFragment;
import com.poomoo.homeonline.ui.custom.ErrorLayout;
import com.poomoo.model.response.ROrderBO;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名 MyOrdersFragment
 * 描述 我的订单
 * 作者 李苜菲
 * 日期 2016/7/19 11:19
 */
public class MyOrdersFragment extends BaseListFragment<ROrderBO> implements BaseListAdapter.OnItemClickListener, PayClickListener, UrgeClickListener, ReceiptClickListener, EvaluateClickListener, TraceClickListener, CancelClickListener, DeleteClickListener {
    public int mCatalog;

    private OrdersAdapter adapter;
    private int currPage = 1;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mCatalog = getArguments().getInt("BUNDLE_TYPE", ROrderBO.ORDER_ALL);
    }

    @Override
    protected BaseListAdapter<ROrderBO> onSetupAdapter() {
        adapter = new OrdersAdapter(getActivity(), BaseListAdapter.ONLY_FOOTER, this, this, this, this, this, this, this);
        return adapter;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView.setPadding(0, setDividerSize(), 0, 0);
        mAdapter.setOnItemClickListener(this);

        EMPTY_DATA = ErrorLayout.NO_ORDER;

        onLoadFinishState(action);
        onLoadResultData(getList());
    }

    private List<ROrderBO> getList() {
        List<ROrderBO> rOrderBOs = new ArrayList<>();
        ROrderBO rOrderBO;
        for (int i = 0; i < 10; i++) {
            rOrderBO = new ROrderBO();
            rOrderBO.status = mCatalog;
            rOrderBO.name = "测试商品" + (i + 1);
            rOrderBOs.add(rOrderBO);
        }
        return rOrderBOs;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onItemClick(int position, long id, View view) {
//        Bundle bundle = new Bundle();
//        bundle.putInt(getString(R.string.intent_value), adapter.getItem(position).jobId);
//        openActivity(JobInfoActivity.class, bundle);
    }

//    @Override
//    public void succeed(List<ROrderBO> list) {
//        LogUtils.d(TAG, "succeed:" + list + "action:" + action);
//        onLoadFinishState(action);
//        onLoadResultData(list);
//    }
//
//    @Override
//    public void failed(String msg) {
//        if (msg.contains("检查网络"))
//            onNetworkInvalid(action);
//        else
//            onLoadErrorState(action);
//    }

    /**
     * 触发下拉刷新事件
     */
    @Override
    public void onRefresh() {
        super.onRefresh();
        currPage = 1;
//        allJobListPresenter.getApplyList(application.getUserId(), mCatalog, currPage);
    }


    @Override
    public void onLoading() {
        super.onLoading();
        LogUtils.d(TAG, "onLoading");
//        allJobListPresenter.getApplyList(application.getUserId(), mCatalog, currPage);
    }

    /**
     * 再错误页面点击重新加载
     */
    @Override
    public void onLoadActiveClick() {
        super.onLoadActiveClick();
        currPage = 1;
//        allJobListPresenter.getApplyList(application.getUserId(), mCatalog, currPage);
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
        MyUtils.showToast(getActivity().getApplicationContext(), "评价:" + position);
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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtils.d(TAG, "onHiddenChanged" + hidden);
        if (!hidden) {
            onLoadResultData(getList());
        }
    }
}
