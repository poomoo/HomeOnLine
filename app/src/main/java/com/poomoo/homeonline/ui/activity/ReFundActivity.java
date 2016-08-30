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
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
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
import com.poomoo.homeonline.ui.custom.ErrorLayout;
import com.poomoo.homeonline.ui.custom.NoScrollGridView;
import com.poomoo.model.response.RReFundBO;
import com.poomoo.model.response.RUploadUrlBO;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 ReFundActivity
 * 描述 申请f退款
 * 作者 李苜菲
 * 日期 2016/8/22 15:38
 */
public class ReFundActivity extends BaseDaggerActivity<ReFundPresenter> implements AdapterView.OnItemClickListener, ErrorLayout.OnActiveClickListener {
    @Bind(R.id.scroll_refund)
    ScrollView scrollView;
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
    @Bind(R.id.edt_refund_explain)
    EditText explainEdt;
    @Bind(R.id.btn_submit)
    Button subBtn;
    @Bind(R.id.error_frame)
    ErrorLayout errorLayout;

    private Drawable drawable;
    private String[] good_data;
    private String[] fund_data;
    private AddPicsAdapter addPicsAdapter;

    private String commodityId;
    private String commodityDetailId;
    private String orderId;
    private String orderDetailId;
    private int count;
    private double amount;
    private DecimalFormat df = new DecimalFormat("0.00");
    private ArrayAdapter<String> adapter_good;
    private ArrayAdapter<String> adapter_fund;

    private int returnReason = 0;//退货原因
    private int returnType = 2;//退货类型 1-退货且退款 2-仅退款
    private int goodsState = 2;//货物状态 1-收到 2-未收到
    private String returnExplain;//退货说明
    private String returnProof;//退货凭证
    private int len = 0;//图片张数
    private int index;
    private final int num = 200;

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
        errorLayout.setOnActiveClickListener(this);

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
                    spinner.setAdapter(adapter_good);
                    returnType = 2;
                    break;
                case R.id.rbtn_reFund:
                    fundRbtn.setCompoundDrawables(null, null, drawable, null);
                    goodRbtn.setCompoundDrawables(null, null, null, null);
                    statusLayout.setVisibility(View.VISIBLE);
                    spinner.setAdapter(adapter_fund);
                    returnType = 1;
                    break;
            }
        });

        statusGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rbtn_yes:
                    yesRbtn.setCompoundDrawables(null, null, drawable, null);
                    noRbtn.setCompoundDrawables(null, null, null, null);
                    goodsState = 2;
                    break;
                case R.id.rbtn_no:
                    yesRbtn.setCompoundDrawables(null, null, null, null);
                    noRbtn.setCompoundDrawables(null, null, drawable, null);
                    goodsState = 1;
                    break;
            }
        });


        good_data = getResources().getStringArray(R.array.with_good_reason);
        adapter_good = new ArrayAdapter<>(this, R.layout.my_simple_spinner_self_item, good_data);

        fund_data = getResources().getStringArray(R.array.no_good_reason);
        adapter_fund = new ArrayAdapter<>(this, R.layout.my_simple_spinner_self_item, fund_data);

        spinner.setAdapter(adapter_fund);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    subBtn.setEnabled(false);
                    return;
                }
                if (parent.equals(adapter_good))
                    returnReason = position > 4 ? position + 5 : position;
                else
                    returnReason = position > 5 ? position + 5 : position + 4;
                subBtn.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        explainEdt.addTextChangedListener(new TextWatcher() {

            private CharSequence temp;
            private int selectionStart;
            private int selectionEnd;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                temp = s;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                selectionStart = explainEdt.getSelectionStart();
                selectionEnd = explainEdt.getSelectionEnd();
                if (temp.length() > num) {
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    explainEdt.setText(s);
                    explainEdt.setSelection(tempSelection);// 设置光标在最后
                }
            }
        });

        picGrid.setSelector(new ColorDrawable(Color.TRANSPARENT));
        addPicsAdapter = new AddPicsAdapter(this);
        addPicsAdapter.update();
        picGrid.setAdapter(addPicsAdapter);
        picGrid.setOnItemClickListener(this);
    }

    public void submitReFund(View view) {
        returnExplain = explainEdt.getText().toString().trim();
        len = Bimp.files.size();
        if (len > 0)
            mPresenter.uploadPic(Bimp.files.get(index++));
        else
            mPresenter.subReFund(commodityId, commodityDetailId, orderId, orderDetailId, application.getUserId(), returnReason, returnExplain, returnProof, returnType, count, goodsState, count * amount);
        errorLayout.setState(ErrorLayout.LOADING, "");
    }

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {

            if (msg.what == 1) {
                if (index == len)
                    mPresenter.subReFund(commodityId, commodityDetailId, orderId, orderDetailId, application.getUserId(), returnReason, returnExplain, returnProof, returnType, count, goodsState, count * amount);
                else
                    mPresenter.uploadPic(Bimp.files.get(index++));
            }
            super.handleMessage(msg);
        }
    };

    public void upLoadSuccessful(RUploadUrlBO rUploadUrlBO) {
        returnProof += rUploadUrlBO.picUrl + "#";

        Message message = new Message();
        message.what = 1;
        myHandler.sendMessage(message);
    }

    @Override
    public void onLoadActiveClick() {
        scrollView.setVisibility(View.VISIBLE);
        if (len > 0)
            mPresenter.uploadPic(Bimp.files.get(index++));
        else
            mPresenter.subReFund(commodityId, commodityDetailId, orderId, orderDetailId, application.getUserId(), returnReason, returnExplain, returnProof, returnType, count, goodsState, count * amount);
        errorLayout.setState(ErrorLayout.LOADING, "");
    }

    public void failed(String msg) {
        scrollView.setVisibility(View.GONE);
        errorLayout.setState(ErrorLayout.NOT_NETWORK, "");
    }

    public void successful(RReFundBO rReFundBO) {
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.intent_value), rReFundBO.returnId);
        openActivity(ReFundInfoActivity.class, bundle);
        finish();
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