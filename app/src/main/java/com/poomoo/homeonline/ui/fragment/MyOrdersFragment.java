package com.poomoo.homeonline.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.poomoo.commlib.LogUtils;
import com.poomoo.homeonline.adapter.OrdersAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.ui.base.BaseListFragment;
import com.poomoo.homeonline.ui.custom.ErrorLayout;
import com.poomoo.model.response.ROrderBO;


/**
 * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG            #
 * #                                                   #
 * 作者: 李苜菲
 * 日期: 2016/7/14 9:59
 * <p/>
 * 我的订单
 */
public class MyOrdersFragment extends BaseListFragment<ROrderBO> implements BaseListAdapter.OnItemClickListener {
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
        adapter = new OrdersAdapter(getActivity(), BaseListAdapter.ONLY_FOOTER, true);
        return adapter;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView.setPadding(0, setDividerSize(), 0, 0);
        mAdapter.setOnItemClickListener(this);

        EMPTY_DATA = ErrorLayout.NO_ORDER;
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

}
