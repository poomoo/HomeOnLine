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
 * Copyright (c) 2016. 跑马科技 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.EvaluateListAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.listeners.EvaluateClickListener;
import com.poomoo.homeonline.recyclerLayoutManager.ScrollLinearLayoutManager;
import com.poomoo.homeonline.ui.base.BaseActivity;
import com.poomoo.model.response.ROrderListBO;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 EvaluateListActivity
 * 描述 评价列表
 * 作者 李苜菲
 * 日期 2016/8/23 17:45
 */
public class EvaluateListActivity extends BaseActivity implements EvaluateClickListener {
    @Bind(R.id.txt_evaluate_num)
    TextView numTxt;
    @Bind(R.id.txt_order_id)
    TextView idTxt;
    @Bind(R.id.txt_date)
    TextView dateTxt;
    @Bind(R.id.recycler_evaluate_list)
    RecyclerView recyclerView;

    private EvaluateListAdapter evaluateListAdapter;

    private String orderId;
    private String date;
    private List<ROrderListBO.OrderDetails> orderDetails;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_evaluate_list;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_evaluate;
    }

    private void init() {
        setBack();

        orderId = getIntent().getStringExtra(getString(R.string.intent_orderId));
        date = getIntent().getStringExtra(getString(R.string.intent_date));
        orderDetails = (List<ROrderListBO.OrderDetails>) getIntent().getSerializableExtra(getString(R.string.intent_orderDetails));

        idTxt.setText(orderId + "");
        numTxt.setText(orderDetails.size() + "");
        dateTxt.setText(date);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(getResources().getColor(R.color.transParent))
                .size((int) getResources().getDimension(R.dimen.recycler_divider))
                .build());

        evaluateListAdapter = new EvaluateListAdapter(this, BaseListAdapter.NEITHER, this);
        recyclerView.setAdapter(evaluateListAdapter);
        evaluateListAdapter.setItems(orderDetails);
    }

    @Override
    public void evaluate(int position) {
        bundle = new Bundle();
        bundle.putString(getString(R.string.intent_orderId), orderId);
        bundle.putInt(getString(R.string.intent_commodityId), evaluateListAdapter.getItem(position).commodityId);
        bundle.putInt(getString(R.string.intent_orderDetailId), evaluateListAdapter.getItem(position).id);
        openActivity(EvaluateActivity.class, bundle);
    }
}
