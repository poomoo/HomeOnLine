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

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.AddPicsAdapter;
import com.poomoo.homeonline.picUtils.Bimp;
import com.poomoo.homeonline.presenters.ReFundPresenter;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.activity.pics.PhotoActivity;
import com.poomoo.homeonline.ui.activity.pics.PhotosActivity;
import com.poomoo.homeonline.ui.base.BaseDaggerActivity;
import com.poomoo.homeonline.ui.custom.NoScrollGridView;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 ReFundActivity
 * 描述 申请f退款
 * 作者 李苜菲
 * 日期 2016/8/22 15:38
 */
public class ReFundActivity extends BaseDaggerActivity<ReFundPresenter> implements AdapterView.OnItemClickListener {
    @Bind(R.id.spinner)
    Spinner spinner;
    @Bind(R.id.radio_group)
    RadioGroup typeGroup;
    @Bind(R.id.rbtn_reFund)
    RadioButton fundRbtn;
    @Bind(R.id.rbtn_reGood)
    RadioButton goodRbtn;
    @Bind(R.id.llayout_good_status)
    LinearLayout statusLayout;
    @Bind(R.id.radio_group_status)
    RadioGroup statusGroup;
    @Bind(R.id.rbtn_yes)
    RadioButton yesRbtn;
    @Bind(R.id.rbtn_no)
    RadioButton noRbtn;
    @Bind(R.id.grid_add_pics)
    NoScrollGridView picGrid;
    @Bind(R.id.txt_refund_count)
    TextView countTxt;
    @Bind(R.id.txt_refund_count_info)
    TextView countInfoTxt;
    @Bind(R.id.txt_refund_amount)
    TextView amountTxt;
    @Bind(R.id.txt_refund_amount_info)
    TextView amountInfoTxt;

    private Drawable drawable;
    private String[] data;
    private AddPicsAdapter addPicsAdapter;

    private String commodityId;
    private String commodityDetailId;
    private String orderId;
    private String orderDetailId;
    private int count;
    private double amount;
    private DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_refund;
    }

    @Override
    protected int onSetTitle() {
        return R.string.title_refund;
    }

    @Override
    protected void setupActivityComponent(ActivityModule activityModule) {
        DaggerActivityComponent.builder()
                .activityModule(activityModule)
                .build()
                .inject(this);
    }

    private void init() {
        setBack();

        commodityId = getIntent().getStringExtra(getString(R.string.intent_commodityId));
        commodityDetailId = getIntent().getStringExtra(getString(R.string.intent_commodityDetailId));
        orderId = getIntent().getStringExtra(getString(R.string.intent_orderId));
        orderDetailId = getIntent().getStringExtra(getString(R.string.intent_orderDetailId));
        count = getIntent().getIntExtra(getString(R.string.intent_count), 0);
        amount = getIntent().getDoubleExtra(getString(R.string.intent_amount), 0.00);

        countTxt.setText(count + "");
        countInfoTxt.setText(count + "");
        amountTxt.setText(df.format(count * amount));
        amountInfoTxt.setText(df.format(count * amount));

        drawable = getResources().getDrawable(R.drawable.ic_check);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), (drawable.getMinimumHeight()));
        typeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rbtn_reGood:
                    fundRbtn.setCompoundDrawables(null, null, null, null);
                    goodRbtn.setCompoundDrawables(null, null, drawable, null);
                    statusLayout.setVisibility(View.GONE);
                    break;
                case R.id.rbtn_reFund:
                    fundRbtn.setCompoundDrawables(null, null, drawable, null);
                    goodRbtn.setCompoundDrawables(null, null, null, null);
                    statusLayout.setVisibility(View.VISIBLE);
                    break;
            }
        });

        statusGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rbtn_yes:
                    yesRbtn.setCompoundDrawables(null, null, drawable, null);
                    noRbtn.setCompoundDrawables(null, null, null, null);
                    break;
                case R.id.rbtn_no:
                    yesRbtn.setCompoundDrawables(null, null, null, null);
                    noRbtn.setCompoundDrawables(null, null, drawable, null);
                    break;
            }
        });


        //Spinner中文框显示样式
        data = getResources().getStringArray(R.array.reason);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.my_simple_spinner_self_item, data);
        //Spinner下拉菜单显示样式
//        adapterTwo.setDropDownViewResource(R.layout.my_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        picGrid.setSelector(new ColorDrawable(Color.TRANSPARENT));
        addPicsAdapter = new AddPicsAdapter(this);
        addPicsAdapter.update();
        picGrid.setAdapter(addPicsAdapter);
        picGrid.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == Bimp.bmp.size()) {
            openActivity(PhotosActivity.class);
        } else {
            Bundle bundle = new Bundle();
            bundle.putInt("ID", position);
            openActivity(PhotoActivity.class, bundle);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        addPicsAdapter.update();
    }
}
