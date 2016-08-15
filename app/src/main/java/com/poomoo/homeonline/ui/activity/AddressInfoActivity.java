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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.ZoneAdapter;
import com.poomoo.homeonline.database.DataBaseHelper;
import com.poomoo.homeonline.presenters.AddressInfoPresenter;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseActivity;
import com.poomoo.homeonline.ui.base.BaseDaggerActivity;
import com.poomoo.model.response.RReceiptBO;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 AddressInfoActivity
 * 描述 地址详情
 * 作者 李苜菲
 * 日期 2016/7/19 11:21
 */
public class AddressInfoActivity extends BaseDaggerActivity<AddressInfoPresenter> {
    @Bind(R.id.edt_receiptName)
    EditText nameEdt;
    @Bind(R.id.edt_receiptTel)
    EditText telEdt;
    @Bind(R.id.edt_receiptAddress)
    EditText addressEdt;
    @Bind(R.id.txt_province)
    TextView provinceTxt;
    @Bind(R.id.txt_city)
    TextView cityTxt;
    @Bind(R.id.txt_area)
    TextView areaTxt;
    @Bind(R.id.edt_code)
    EditText codeEdt;
    @Bind(R.id.chk_default_address)
    CheckBox defaultChk;
    @Bind(R.id.llayout_bottom)
    LinearLayout bottomLayout;

    private ZoneAdapter adapter;
    private AddressPopUpWindow addressPopUpWindow;
    private List<String> provinceList = new ArrayList<>();
    private List<String> cityList = new ArrayList<>();
    private List<String> areaList = new ArrayList<>();
    private int provinceId = -1;
    private int cityId = -1;
    private int areaId = -1;
    private String provinceName;
    private String cityName;
    private String areaName;
    private String receiptName;
    private String tel;
    private String address;
    private String code;
    private String flag = "";//new-新建 else-修改
    private static final int NEW = 1;
    private static final int UPDATE = 2;
    private static final int DELETE = 3;
    private RReceiptBO rReceiptBO = new RReceiptBO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.acitvity_address;
    }

    @Override
    protected int onSetTitle() {
        if (!flag.equals("new"))
            return R.string.title_addressInfo;
        else
            return R.string.title_newAddress;
    }

    @Override
    protected void setupActivityComponent(ActivityModule activityModule) {
        DaggerActivityComponent.builder()
                .activityModule(activityModule)
                .build()
                .inject(this);
    }

    private void init() {
        flag = getIntent().getStringExtra(getString(R.string.intent_value));
        setBack();
        if (!flag.equals("new")) {
            rReceiptBO = (RReceiptBO) getIntent().getSerializableExtra(getString(R.string.intent_receiptBO));
            receiptName = rReceiptBO.consigneeName;
            tel = rReceiptBO.consigneeTel;
            address = rReceiptBO.streetName;
            code = rReceiptBO.postCode;
            provinceId = rReceiptBO.provinceId;
            cityId = rReceiptBO.cityId;
            areaId = rReceiptBO.areaId;
            provinceName = rReceiptBO.provinceName;
            cityName = rReceiptBO.cityName;
            areaName = rReceiptBO.areaName;
            LogUtils.d(TAG, "rReceiptBO:" + rReceiptBO);
            nameEdt.setText(receiptName);
            telEdt.setText(tel);
            addressEdt.setText(address);
            nameEdt.setText(receiptName);
            codeEdt.setText(code);
            defaultChk.setChecked(rReceiptBO.isDefault);

            provinceTxt.setText(TextUtils.isEmpty(provinceName) ? "选择省份" : provinceName);
            cityTxt.setText(TextUtils.isEmpty(cityName) ? "选择城市" : cityName);
            areaTxt.setText(TextUtils.isEmpty(areaName) ? "选择城区" : areaName);
        } else {
            bottomLayout.setVisibility(View.GONE);

            HeaderViewHolder headerViewHolder = getHeaderView();
            headerViewHolder.rightTxt.setText("保存");
            headerViewHolder.rightTxt.setTextColor(ContextCompat.getColor(this, R.color.ThemeRed));
            headerViewHolder.rightTxt.setVisibility(View.VISIBLE);
        }

        adapter = new ZoneAdapter(context);
        addressPopUpWindow = new AddressPopUpWindow(this);

        initZone();
        getProgressBar();
    }

    private void initZone() {
        adapter.setCurrAddress(ZoneAdapter.PROVINCE);
        provinceList = DataBaseHelper.getProvince();
        if (!TextUtils.isEmpty(provinceName))
            adapter.setSelectedPosition(provinceList.indexOf(provinceName + "#" + provinceId));

        adapter.setCurrAddress(ZoneAdapter.CITY);
        cityList = DataBaseHelper.getCity(provinceId);
        if (!TextUtils.isEmpty(cityName))
            adapter.setSelectedPosition(cityList.indexOf(cityName + "#" + cityId));

        adapter.setCurrAddress(ZoneAdapter.AREA);
        areaList = DataBaseHelper.getArea(cityId);
        if (!TextUtils.isEmpty(areaName))
            adapter.setSelectedPosition(areaList.indexOf(areaName + "#" + areaId));
    }

    public void selectProvince(View view) {
        adapter.setCurrAddress(ZoneAdapter.PROVINCE);
        provinceList = DataBaseHelper.getProvince();
        adapter.setItems(provinceList);

        addressPopUpWindow.showAtLocation(this.findViewById(R.id.llayout_address), Gravity.CENTER, 0, 0);
    }

    public void selectCity(View view) {
        if (provinceId == -1)
            return;

        adapter.setCurrAddress(ZoneAdapter.CITY);
        cityList = DataBaseHelper.getCity(provinceId);
        if (cityList.size() == 0)
            return;
        adapter.setItems(cityList);

        addressPopUpWindow.showAtLocation(this.findViewById(R.id.llayout_address), Gravity.CENTER, 0, 0);
    }

    public void selectArea(View view) {
        if (cityId == -1)
            return;

        adapter.setCurrAddress(ZoneAdapter.AREA);
        areaList = DataBaseHelper.getArea(cityId);
        if (areaList.size() == 0)
            return;
        adapter.setItems(areaList);

        addressPopUpWindow.showAtLocation(this.findViewById(R.id.llayout_address), Gravity.CENTER, 0, 0);
    }

    public class AddressPopUpWindow extends PopupWindow {
        private View mMenuView;
        private ListView list_address;

        public AddressPopUpWindow(Activity context) {
            super(context);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mMenuView = inflater.inflate(R.layout.popup_address, null);
            list_address = (ListView) mMenuView.findViewById(R.id.list_address);

            list_address.setAdapter(adapter);

            this.setContentView(mMenuView);
            this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            this.setFocusable(true);
            ColorDrawable dw = new ColorDrawable(0xb0000000);
            this.setBackgroundDrawable(dw);

            list_address.setOnItemClickListener((parent, view, position, id) -> {
                dismiss();
                adapter.setSelectedPosition(position);
                String temp[];
                switch (adapter.getCurrAddress()) {
                    case ZoneAdapter.PROVINCE:
                        temp = provinceList.get(position).split("#");
                        provinceTxt.setText(temp[0]);
                        provinceId = Integer.parseInt(temp[1]);
                        cityId = -1;
                        cityTxt.setText("选择城市");
                        areaTxt.setText("选择城区");
                        break;
                    case ZoneAdapter.CITY:
                        temp = cityList.get(position).split("#");
                        cityTxt.setText(temp[0]);
                        cityId = Integer.parseInt(temp[1]);
                        areaTxt.setText("选择城区");
                        break;
                    case ZoneAdapter.AREA:
                        temp = areaList.get(position).split("#");
                        areaTxt.setText(temp[0]);
                        areaId = Integer.parseInt(temp[1]);
                        break;
                }
            });
            mMenuView.setOnTouchListener((v, event) -> {
                int height_top = mMenuView.findViewById(R.id.llayout_address).getTop();
                int height_bottom = mMenuView.findViewById(R.id.llayout_address).getBottom();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height_top || y > height_bottom) {
                        dismiss();
                    }
                }
                return true;
            });
        }
    }

    /**
     * 检查输入项合法性
     *
     * @return
     */
    private boolean checkInput() {
        receiptName = nameEdt.getText().toString();
        if (TextUtils.isEmpty(receiptName)) {
            MyUtils.showToast(getApplicationContext(), "请输入收货人姓名");
            return false;
        }
        rReceiptBO.consigneeName = receiptName;

        tel = telEdt.getText().toString().trim();
        if (TextUtils.isEmpty(tel)) {
            MyUtils.showToast(getApplicationContext(), "请输入收货人电话");
            return false;
        }
        if (!MyUtils.checkPhoneNum(tel)) {
            MyUtils.showToast(getApplicationContext(), "收货人电话不合法");
            return false;
        }
        rReceiptBO.consigneeTel = tel;

        address = addressEdt.getText().toString();
        if (provinceId == -1 || cityId == -1 || areaId == -1) {
            MyUtils.showToast(getApplicationContext(), "请选择收货地址");
            return false;
        }
        rReceiptBO.provinceId = provinceId;
        rReceiptBO.cityId = cityId;
        rReceiptBO.areaId = areaId;

        if (TextUtils.isEmpty(address)) {
            MyUtils.showToast(getApplicationContext(), "请输入详细地址");
            return false;
        }
        rReceiptBO.streetName = address;

        code = codeEdt.getText().toString().trim();
        rReceiptBO.postCode = code;

        rReceiptBO.isDefault = defaultChk.isChecked();
        return true;
    }

    /**
     * 新建
     *
     * @param view
     */
    public void toDo(View view) {
        if (checkInput()) {
            mProgressBar.setVisibility(View.VISIBLE);
            mPresenter.newAddress(application.getUserId(), rReceiptBO.consigneeName, rReceiptBO.consigneeTel, rReceiptBO.postCode, rReceiptBO.provinceId, rReceiptBO.cityId, rReceiptBO.areaId, rReceiptBO.streetName, rReceiptBO.isDefault);
        }
    }

    public void newSucceed() {
        mProgressBar.setVisibility(View.GONE);
        setResult(NEW);
        finish();
    }


    /**
     * 修改
     *
     * @param view
     */
    public void updateAddress(View view) {
        if (checkInput()) {
            mProgressBar.setVisibility(View.VISIBLE);
            mPresenter.updateAddress(rReceiptBO.id, application.getUserId(), rReceiptBO.consigneeName, rReceiptBO.consigneeTel, rReceiptBO.postCode, rReceiptBO.provinceId, rReceiptBO.cityId, rReceiptBO.areaId, rReceiptBO.streetName, rReceiptBO.isDefault);
        }
    }

    public void updateSucceed() {
        mProgressBar.setVisibility(View.GONE);
        setResult(UPDATE);
        finish();
    }

    /**
     * 删除
     *
     * @param view
     */
    public void deleteAddress(View view) {
        Dialog dialog = new AlertDialog.Builder(this).setMessage("确定删除?").setNegativeButton("取消", null).setPositiveButton("确定", (dialog1, which) -> {
            mProgressBar.setVisibility(View.VISIBLE);
            mPresenter.deleteAddress(rReceiptBO.id);
        }).create();

        dialog.show();
    }

    public void deleteSucceed() {
        mProgressBar.setVisibility(View.GONE);
        setResult(DELETE);
        finish();
    }


    public void failed(String msg) {
        mProgressBar.setVisibility(View.GONE);
        MyUtils.showToast(getApplicationContext(), msg);
    }
}
