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
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.poomoo.api.NetConfig;
import com.poomoo.commlib.LogUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.base.MyBaseAdapter;
import com.poomoo.model.response.RCateBO;
import com.poomoo.model.response.RListCommodityBO;

/**
 * 类名 OnSaleGridAdapter
 * 描述 优惠专区适配器
 * 作者 李苜菲
 * 日期 2016/7/19 11:31
 */
public class OnSaleGridAdapter extends MyBaseAdapter<RListCommodityBO> {
    private RListCommodityBO item;
    private String url;

    public OnSaleGridAdapter(Context context) {
        super(context);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        viewHolder = new ViewHolder();
        item = itemList.get(position);
//        LogUtils.d(TAG,"item:"+item);

        convertView = inflater.inflate(R.layout.item_list_commodity, null);
        viewHolder.image = (ImageView) convertView.findViewById(R.id.img_commodity);
        viewHolder.nameTxt = (TextView) convertView.findViewById(R.id.txt_commodity_name);
        viewHolder.priceTxt = (TextView) convertView.findViewById(R.id.txt_commodity_price);

        Glide.with(context).load(NetConfig.ImageUrl + item.listPic).placeholder(R.drawable.replace).priority(Priority.IMMEDIATE).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(viewHolder.image);
        viewHolder.nameTxt.setText(item.commodityName);
        viewHolder.priceTxt.setText("￥" + item.price);
        return convertView;
    }

    class ViewHolder {
        private ImageView image;
        private TextView nameTxt;
        private TextView priceTxt;
    }

}
