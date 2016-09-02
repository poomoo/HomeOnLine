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
import android.text.TextUtils;
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

import com.poomoo.api.NetConfig;
import com.poomoo.commlib.LogUtils;
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
import java.util.ArrayList;
import java.util.List;

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

    private String id;//退货ID
    private int returnReason = 0;//退货原因
    private int returnType = 2;//退货类型 1-退货且退款 2-仅退款
    private int goodsState = 2;//货物状态 1-收到 2-未收到
    private String returnExplain;//退货说明
    private String returnProof = "";//退货凭证
    private int len = 0;//图片张数
    private int index;
    private final int num = 200;
    private boolean isNew = true;//true-提交 false-修改
    private String[] urls;
    private List<String> drr;
    private List<Integer> integers;

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
        isNew = getIntent().getBooleanExtra(getString(R.string.intent_value), true);

        countTxt.setText(count + "");
        countInfoTxt.setText(count + "");
        amountTxt.setText(df.format(count * amount));
        amountInfoTxt.setText(df.format(count * amount));

        drawable = getResources().getDrawable(R.drawable.ic_check);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), (drawable.getMinimumHeight()));
        typeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rbtn_reFund:
                    fundRbtn.setCompoundDrawables(null, null, drawable, null);
                    goodRbtn.setCompoundDrawables(null, null, null, null);
                    statusLayout.setVisibility(View.GONE);
                    spinner.setAdapter(adapter_fund);
                    returnType = 2;
                    break;
                case R.id.rbtn_reGood:
                    fundRbtn.setCompoundDrawables(null, null, null, null);
                    goodRbtn.setCompoundDrawables(null, null, drawable, null);
                    statusLayout.setVisibility(View.VISIBLE);
                    spinner.setAdapter(adapter_good);
                    returnType = 1;
                    break;
            }
        });

        statusGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rbtn_no:
                    yesRbtn.setCompoundDrawables(null, null, null, null);
                    noRbtn.setCompoundDrawables(null, null, drawable, null);
                    goodsState = 2;
                    break;
                case R.id.rbtn_yes:
                    yesRbtn.setCompoundDrawables(null, null, drawable, null);
                    noRbtn.setCompoundDrawables(null, null, null, null);
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
                if (spinner.getAdapter().equals(adapter_fund))//仅退款
                    returnReason = position + 4;
                else//退货退款
                    returnReason = position > 4 ? 10 : position;
                subBtn.setEnabled(true);
                LogUtils.d(TAG, "returnReason:" + returnReason + " parent:" + parent + " spinner:" + spinner.getAdapter());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if (!isNew) {
            id = getIntent().getStringExtra(getString(R.string.intent_returnNoteId));
            returnType = getIntent().getIntExtra(getString(R.string.intent_returnType), 2);
            goodsState = getIntent().getIntExtra(getString(R.string.intent_goodsState), 2);
            returnReason = getIntent().getIntExtra(getString(R.string.intent_returnReason), 2);
            returnExplain = getIntent().getStringExtra(getString(R.string.intent_returnExplain));
            returnProof = getIntent().getStringExtra(getString(R.string.intent_returnProof));
            LogUtils.d(TAG, "returnType:" + returnType);
            LogUtils.d(TAG, "goodsState:" + goodsState);
            LogUtils.d(TAG, "returnReason:" + returnReason);
            LogUtils.d(TAG, "returnProof:" + returnProof);
            explainEdt.setText(returnExplain);
            Bimp.drr = new ArrayList<>();
            if (!TextUtils.isEmpty(returnProof)) {
                urls = returnProof.split("#");
                for (String url : urls)
                    Bimp.drr.add(NetConfig.ImageUrl + url);

//                    try {
//                        Bimp.bmp.add(Glide.with(this).load(NetConfig.ImageUrl + url).asBitmap().into(100, 100).get());
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    } catch (ExecutionException e) {
//                        e.printStackTrace();
//                    }
//                    Glide.with(this).load(NetConfig.ImageUrl + url).asBitmap().into(new SimpleTarget<Bitmap>() {
//                        @Override
//                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                            Bimp.bmp.add(resource);
//                            addPicsAdapter.update();
//                        }
//
//                    });
            }

            if (returnType == 2) {//仅退款
                spinner.setAdapter(adapter_fund);
                if (returnReason > 0)
                    spinner.setSelection(returnReason > 9 ? 6 : returnReason - 4);
            } else {//退货退款
                goodRbtn.setChecked(true);
                fundRbtn.setCompoundDrawables(null, null, null, null);
                goodRbtn.setCompoundDrawables(null, null, drawable, null);
                statusLayout.setVisibility(View.VISIBLE);
                spinner.setAdapter(adapter_good);
                if (returnReason > 0)
                    spinner.setSelection(returnReason > 4 ? 5 : returnReason);
                if (goodsState == 1) {
                    yesRbtn.setCompoundDrawables(null, null, drawable, null);
                    noRbtn.setCompoundDrawables(null, null, null, null);
                }
            }
        }

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
        integers = new ArrayList<>();
        for (String url : Bimp.drr) {
            if (!url.startsWith("http"))
                integers.add(index);
            index++;
        }
        drr = new ArrayList<>();
        drr.addAll(Bimp.drr);
        Bimp.drr = new ArrayList<>();
        index = 0;
        LogUtils.d(TAG, "len:" + len);
        if (len > 0)
            mPresenter.uploadPic(Bimp.files.get(index++));
        else
            submit();
        errorLayout.setState(ErrorLayout.LOADING, "");
    }

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (index == len)
                    submit();
                else
                    mPresenter.uploadPic(Bimp.files.get(index++));
            }
            super.handleMessage(msg);
        }
    };

    public void upLoadSuccessful(RUploadUrlBO rUploadUrlBO) {
        Bimp.drr.add(rUploadUrlBO.picUrl);

        Message message = new Message();
        message.what = 1;
        myHandler.sendMessage(message);
    }

    @Override
    public void onLoadActiveClick() {
        scrollView.setVisibility(View.VISIBLE);
        index = 0;
        if (len > 0)
            mPresenter.uploadPic(Bimp.files.get(index++));
        else
            submit();

        errorLayout.setState(ErrorLayout.LOADING, "");
    }

    public void submit() {
        returnProof = "";
        LogUtils.d(TAG, "integers:" + integers.toString() + " Bimp.drr" + Bimp.drr);
        for (int i = 0; i < integers.size(); i++)
            drr.set(integers.get(i), Bimp.drr.get(i));
        LogUtils.d(TAG, "drr:" + drr);

        for (String url : drr)
            returnProof += url.startsWith("http") ? url.substring(url.indexOf("/", 27), url.length()) + "#" : url + "#";

        if (isNew)
            mPresenter.subReFund(commodityId, commodityDetailId, orderId, orderDetailId, application.getUserId(), returnReason, returnExplain, returnProof, returnType, count, goodsState, count * amount);
        else
            mPresenter.changeReFund(id, returnReason, returnExplain, returnProof, returnType, count, goodsState, count * amount);
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

    public void changeSuccessful() {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == Bimp.drr.size()) {
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
