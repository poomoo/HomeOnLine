/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.poomoo.api.NetConfig;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.base.MyBaseAdapter;
import com.poomoo.model.response.RAdBO;

/**
 * 类名 PicturesGridAdapter
 * 描述 首页图片适配器
 * 作者 李苜菲
 * 日期 2016/7/19 11:33
 */
public class PicturesGridAdapter extends MyBaseAdapter<RAdBO> {
    private RAdBO item;

    public PicturesGridAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        LogUtils.d(TAG,"getView"+ position+"len:"+itemList.size());
        final ViewHolder viewHolder;
        viewHolder = new ViewHolder();
        item = itemList.get(position);
        convertView = inflater.inflate(R.layout.item_pictures, null);
        viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
        viewHolder.image.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MyUtils.getScreenWidth(context) / 2 * 10 / 13));//设置广告栏的宽高比为13:10
        Glide.with(context).load(NetConfig.ImageUrl + item.advertisementPic).placeholder(R.drawable.replace13b10).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(viewHolder.image);
        return convertView;
    }

    class ViewHolder {
        private ImageView image;
    }

}
