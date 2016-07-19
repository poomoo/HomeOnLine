/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.ZoneAdapter;

import java.util.List;

/**
 * 类名 AddressPopUpWindow
 * 描述 地址选择器
 * 作者 李苜菲
 * 日期 2016/7/19 11:34
 */
public class AddressPopUpWindow extends PopupWindow {
    private View mMenuView;
    private ListView list_type;
    public ZoneAdapter adapter;
    private String selected;

    public AddressPopUpWindow(final Activity context, final List<String> stringList, final SelectAddress selectAddress) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popup_address, null);
        list_type = (ListView) mMenuView.findViewById(R.id.list_address);

//        DisplayMetrics dm = new DisplayMetrics();
//        context.getWindowManager().getDefaultDisplay().getMetrics(dm); // 获取手机屏幕的大小
//        list_type.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dm.heightPixels * 6 / 10));

        adapter = new ZoneAdapter(context);
        list_type.setAdapter(adapter);
        adapter.addItems(stringList);


        this.setContentView(mMenuView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);

        list_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = stringList.get(position);
                adapter.notifyDataSetChanged();
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

    /**
     *
     */
    public interface SelectAddress {
        /**
         * 把选中的下标通过方法回调回来
         *
         * @param province
         */
        public void SelectAddress(String province);
    }
}
