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
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.ZoneAdapter;
import com.poomoo.homeonline.database.DataBaseHelper;
import com.poomoo.homeonline.ui.base.BaseActivity;

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
 * 日期: 2016/7/14 14:55.
 */
public class AddressInfoActivity extends BaseActivity {
    @Bind(R.id.txt_province)
    TextView provinceTxt;
    @Bind(R.id.txt_city)
    TextView cityTxt;
    @Bind(R.id.txt_area)
    TextView areaTxt;

    private ZoneAdapter adapter;
    private AddressPopUpWindow addressPopUpWindow;
    private List<String> provinceList = new ArrayList<>();
    private List<String> cityList = new ArrayList<>();
    private List<String> areaList = new ArrayList<>();
    private int provinceId = -1;
    private int cityId = -1;
    private int areaId = -1;

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
        return 0;
    }

    private void init() {
        adapter = new ZoneAdapter(context);
        addressPopUpWindow = new AddressPopUpWindow(this);
    }

    public void selectProvince(View view) {
        provinceList = DataBaseHelper.getProvince();
        adapter.setCurrAddress(ZoneAdapter.PROVINCE);
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
}
