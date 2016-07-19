/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.base.MyBaseAdapter;
import com.poomoo.model.response.RTypeBO;

/**
 * 类名 MainGridAdapter
 * 描述 首页类别适配器
 * 作者 李苜菲
 * 日期 2016/7/19 11:31
 */
public class MainGridAdapter extends MyBaseAdapter<RTypeBO> {
    private String TAG = "MainGridAdapter";
    private RTypeBO item;

    public MainGridAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        viewHolder = new ViewHolder();
        item = itemList.get(position);
        convertView = inflater.inflate(R.layout.item_grid_main, null);
        viewHolder.image = (ImageView) convertView.findViewById(R.id.img_main);
        viewHolder.txt = (TextView) convertView.findViewById(R.id.txt_main);
        Glide.with(context).load(item.icon).placeholder(R.drawable.nav_main_normal).into(viewHolder.image);
        viewHolder.txt.setText(item.name);
        return convertView;
    }

    class ViewHolder {
        private ImageView image;
        private TextView txt;
    }

}
