/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.poomoo.api.NetConfig;
import com.poomoo.commlib.LogUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.base.MyBaseAdapter;
import com.poomoo.homeonline.listeners.ClassifyOnItemClickListener;
import com.poomoo.model.response.RClassifyBO;
import com.poomoo.model.response.RThirdClassifyBO;

/**
 * 类名 SubClassifyGridAdapter
 * 描述 三级分类适配器
 * 作者 李苜菲
 * 日期 2016/7/19 11:33
 */
public class SubClassifyGridAdapter extends MyBaseAdapter<RThirdClassifyBO> {
    private Context context;
    private RThirdClassifyBO item;
    private ClassifyOnItemClickListener onItemClickListener;

    public SubClassifyGridAdapter(Context context, ClassifyOnItemClickListener onItemClickListener) {
        super(context);
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        viewHolder = new ViewHolder();
        item = itemList.get(position);
        convertView = inflater.inflate(R.layout.item_grid_sub_classify, null);
        viewHolder.linearLayout = (LinearLayout) convertView.findViewById(R.id.llayout_thirdClassify);
        viewHolder.nameTxt = (TextView) convertView.findViewById(R.id.txt_thirdClassify);
        viewHolder.img = (ImageView) convertView.findViewById(R.id.img_thirdClassify);
        viewHolder.linearLayout.setOnClickListener(new OnItemClick(item.id + ""));
        viewHolder.nameTxt.setText(item.categoryName);
        Glide.with(context).load(NetConfig.ImageUrl + item.categoryPic).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(viewHolder.img);
        return convertView;
    }

    class ViewHolder {
        private LinearLayout linearLayout;
        private TextView nameTxt;
        private ImageView img;
    }

    class OnItemClick implements View.OnClickListener {
        private String categoryId;

        public OnItemClick(String categoryId) {
            this.categoryId = categoryId;
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onClick(this.categoryId);
        }
    }


}
