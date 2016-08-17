/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.poomoo.api.NetConfig;
import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.model.response.RListCommodityBO;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 ListCommodityAdapter
 * 描述 商品列表适配器
 * 作者 李苜菲
 * 日期 2016/7/19 11:33
 */
public class ListCommodityAdapter extends BaseListAdapter<RListCommodityBO> {
    private RListCommodityBO item;
    private boolean isCommon;//true-通用 false-特列

    public ListCommodityAdapter(Context context, int mode, boolean isCommon) {
        super(context, mode);
        this.isCommon = isCommon;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        if (isCommon)
            return new BaseViewHolder(mInflater.inflate(R.layout.item_list_commodity, parent, false));
        else
            return new BaseViewHolder(mInflater.inflate(R.layout.item_list_classify_commodity, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        BaseViewHolder holder = (BaseViewHolder) h;
//        if (isCommon)
//            holder.image.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MyUtils.getScreenWidth(mContext) / 2));//设置广告栏的宽高比为1:1
//        else
//            holder.image.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MyUtils.getScreenWidth(mContext) * 2 / 5));//设置广告栏的宽高比为1:1

        item = items.get(position);
        Glide.with(mContext).load(NetConfig.ImageUrl + item.listPic).placeholder(R.drawable.replace).priority(Priority.HIGH).into(holder.image);
        holder.nameTxt.setText(item.commodityName);
        holder.priceTxt.setText("￥" + item.platformPrice);
    }

    public static final class BaseViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_commodity)
        ImageView image;
        @Bind(R.id.txt_commodity_name)
        TextView nameTxt;
        @Bind(R.id.txt_commodity_price)
        TextView priceTxt;

        public BaseViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
