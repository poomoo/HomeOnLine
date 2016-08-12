/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.poomoo.commlib.LogUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.base.MyBaseAdapter;

/**
 * 类名 ZoneAdapter
 * 描述 地址选择适配器
 * 作者 李苜菲
 * 日期 2016/7/19 11:34
 */
public class ZoneAdapter extends MyBaseAdapter<String> {
    public static final int PROVINCE = 1;
    public static final int CITY = 2;
    public static final int AREA = 3;
    public int currAddress = 0;

    private int provincePosition = -1;
    private int cityPosition = -1;
    private int areaPosition = -1;
    private int selectedPosition = -1;

    public ZoneAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LogUtils.d(TAG, "AddressAdapter" + position);
        final ViewHolder viewHolder;
        viewHolder = new ViewHolder();
        convertView = inflater.inflate(R.layout.item_list_zone, null);
        viewHolder.textView = (TextView) convertView.findViewById(R.id.txt_address);
        viewHolder.imageView = (ImageView) convertView.findViewById(R.id.img_address);
        viewHolder.idTxt = (TextView) convertView.findViewById(R.id.txt_addressId);

        convertView.setTag(viewHolder);
        String item = itemList.get(position);

        viewHolder.textView.setText(item.split("#")[0]);
        if (position == selectedPosition)
            viewHolder.imageView.setImageResource(R.drawable.ic_checkbox_checked);
        else
            viewHolder.imageView.setImageResource(R.drawable.ic_checkbox_normal);

        viewHolder.idTxt.setText(item.split("#")[1]);

        return convertView;
    }

    public static class ViewHolder {
        TextView textView;
        ImageView imageView;
        TextView idTxt;
    }

    public void setSelectedPosition(int selectedPosition) {
        LogUtils.d(TAG, "setSelectedPosition:" + selectedPosition + "currAddress:" + currAddress);
        this.selectedPosition = selectedPosition;
        switch (currAddress) {
            case PROVINCE:
                this.provincePosition = selectedPosition;
                this.cityPosition = -1;
                this.areaPosition = -1;
                break;
            case CITY:
                this.cityPosition = selectedPosition;
                this.areaPosition = -1;
                break;
            case AREA:
                this.areaPosition = selectedPosition;
                break;
        }
    }

    public void setCurrAddress(int currAddress) {
        this.currAddress = currAddress;
        switch (currAddress) {
            case PROVINCE:
                this.selectedPosition = this.provincePosition;
                break;
            case CITY:
                this.selectedPosition = this.cityPosition;
                break;
            case AREA:
                this.selectedPosition = this.areaPosition;
                break;
        }
    }

    public int getCurrAddress() {
        return currAddress;
    }
}