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
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.AddressListAdapter;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.ui.base.BaseActivity;
import com.poomoo.model.response.RReceiptBO;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

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
 * 日期: 2016/7/14 17:26.
 * <p/>
 * 地址列表
 */
public class AddressListActivity extends BaseActivity implements BaseListAdapter.OnItemClickListener {
    @Bind(R.id.recycler_address)
    RecyclerView recyclerView;

    private AddressListAdapter adapter;
    private ArrayList<RReceiptBO> rReceiptBOs = new ArrayList<>();
    private RReceiptBO rReceiptBO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_address_list;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_address_list;
    }

    private void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(getResources().getColor(R.color.divide))
                .size((int) getResources().getDimension(R.dimen.divider_height2))
                .build());

        adapter = new AddressListAdapter(this, BaseListAdapter.NEITHER);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        adapter.setItems(getList());
    }

    private List<RReceiptBO> getList() {
        for (int i = 0; i < 4; i++) {
            rReceiptBO = new RReceiptBO();
            rReceiptBO.name = "测试收货人" + i + 1;
            rReceiptBO.tel = "1860087814" + i;
            rReceiptBO.address = "贵州省 贵阳市 观山湖区 世纪城龙禧苑11栋";
            rReceiptBOs.add(rReceiptBO);
        }
        return rReceiptBOs;
    }

    @Override
    public void onItemClick(int position, long id, View view) {
//        Bundle bundle = new Bundle();
//        bundle.putString(getString(R.string.intent_value));
        openActivity(AddressInfoActivity.class);
    }

    public void addAddress(View view) {
        openActivity(AddressInfoActivity.class);
    }
}
