/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.poomoo.commlib.LogUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.base.MyBaseAdapter;

/**
 * 类名 PicturesGridAdapter
 * 描述 首页图片适配器
 * 作者 李苜菲
 * 日期 2016/7/19 11:33
 */
public class PicturesGridAdapter extends MyBaseAdapter<String> {
    private String TAG = "PicturesGridAdapter";
    private String item;

    public PicturesGridAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        LogUtils.d(TAG,"getView"+ position+"len:"+itemList.size());
        final ViewHolder viewHolder;
        viewHolder = new ViewHolder();
        item = itemList.get(position);
        convertView = inflater.inflate(R.layout.item_grid_pictures, null);
        viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
        Glide.with(context).load(item).placeholder(R.drawable.replace).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(viewHolder.image);
        return convertView;
    }

    class ViewHolder {
        private ImageView image;
    }

}
