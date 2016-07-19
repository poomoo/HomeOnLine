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
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyConfig;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.ZoneAdapter;
import com.poomoo.homeonline.database.DataBaseHelper;
import com.poomoo.homeonline.ui.base.BaseActivity;
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
public class AddressInfoActivity extends BaseActivity {
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
    private String[] zone;
    private String flag = "";//new-新建 else-修改
    private static final int NEW = 1;
    private static final int DELETE = 2;
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
        return R.string.title_addressInfo;
    }

    private void init() {
        setBack();

        flag = getIntent().getStringExtra(getString(R.string.intent_value));
        if (!flag.equals("new")) {
            receiptName = getIntent().getStringExtra(getString(R.string.intent_receiptName));
            tel = getIntent().getStringExtra(getString(R.string.intent_receiptTel));
            address = getIntent().getStringExtra(getString(R.string.intent_receiptAddress));
            provinceId = getIntent().getIntExtra(getString(R.string.intent_provinceId), -1);
            cityId = getIntent().getIntExtra(getString(R.string.intent_cityId), -1);
            areaId = getIntent().getIntExtra(getString(R.string.intent_areaId), -1);

            nameEdt.setText(receiptName);
            telEdt.setText(tel);
            addressEdt.setText(address);
            nameEdt.setText(receiptName);
            zone = DataBaseHelper.getProvinceCityArea(provinceId, cityId, areaId);
            provinceName = zone[0];
            cityName = zone[1];
            areaName = zone[2];

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

            list_address.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
                            cityTxt.setText(cityList.get(position).split("#")[0]);
                            cityId = Integer.parseInt(temp[1]);
                            areaTxt.setText("选择城区");
                            break;
                        case ZoneAdapter.AREA:
                            temp = areaList.get(position).split("#");
                            areaTxt.setText(areaList.get(position).split("#")[0]);
                            areaId = Integer.parseInt(temp[1]);
                            break;
                    }
                }
            });

            mMenuView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int height_top = mMenuView.findViewById(R.id.llayout_address).getTop();
                    int height_bottom = mMenuView.findViewById(R.id.llayout_address).getBottom();
                    int y = (int) event.getY();
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (y < height_top || y > height_bottom) {
                            dismiss();
                        }
                    }
                    return true;
                }
            });
        }
    }


    /**
     * 新建保存
     *
     * @param view
     */
    public void toDo(View view) {
        receiptName = nameEdt.getText().toString();
        if (TextUtils.isEmpty(receiptName)) {
            MyUtils.showToast(getApplicationContext(), "请输入收货人姓名");
            return;
        }
        rReceiptBO.name = receiptName;

        tel = telEdt.getText().toString().trim();
        if (TextUtils.isEmpty(tel)) {
            MyUtils.showToast(getApplicationContext(), "请输入收货人电话");
            return;
        }
        if (!MyUtils.checkPhoneNum(tel)) {
            MyUtils.showToast(getApplicationContext(), "收货人电话不合法");
            return;
        }
        rReceiptBO.tel = tel;
        address = addressEdt.getText().toString();
        if (provinceId == -1 || cityId == -1 || areaId == -1 || TextUtils.isEmpty(address)) {
            MyUtils.showToast(getApplicationContext(), "请输入收货地址");
            return;
        }

        rReceiptBO.address = provinceTxt.getText().toString() + " " + cityTxt.getText().toString() + " " + areaTxt.getText().toString() + " " + address;
        getIntent().putExtra(getString(R.string.intent_value), rReceiptBO);
        setResult(NEW, getIntent());
        finish();
    }


    /**
     * 删除
     *
     * @param view
     */
    public void deleteAddress(View view) {
        Dialog dialog = new AlertDialog.Builder(this).setMessage("确定删除?").setNegativeButton("取消", null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setResult(DELETE);
                finish();
            }
        }).create();

        dialog.show();
    }

    /**
     * 保存
     *
     * @param view
     */
    public void saveAddress(View view) {

    }
}
