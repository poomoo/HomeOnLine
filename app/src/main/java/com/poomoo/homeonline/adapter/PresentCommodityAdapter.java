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
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.model.response.RListCommodityBO;
import com.poomoo.model.response.RPresentBO;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 PresentCommodityAdapter
 * 描述 买赠适配器
 * 作者 李苜菲
 * 日期 2016/7/19 11:33
 */
public class PresentCommodityAdapter extends BaseListAdapter<RPresentBO.ActivityCommodities> {
    private RPresentBO.ActivityCommodities item;

    public PresentCommodityAdapter(Context context, int mode) {
        super(context, mode);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new BaseViewHolder(mInflater.inflate(R.layout.item_list_present_commodity, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        BaseViewHolder holder = (BaseViewHolder) h;
//        holder.image.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MyUtils.getScreenWidth(mContext) * 2 / 5));//设置广告栏的宽高比为1:1

        item = items.get(position);
        Glide.with(mContext).load(NetConfig.ImageUrl + item.listPic).diskCacheStrategy(DiskCacheStrategy.SOURCE).centerCrop().placeholder(R.drawable.replace).priority(Priority.HIGH).into(holder.image);
        holder.ruleTxt.setText(item.rule);
        holder.nameTxt.setText(item.commodityName);
        holder.priceTxt.setText("￥" + item.commodityPrice);
    }

    public static final class BaseViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_present_commodity)
        ImageView image;
        @Bind(R.id.txt_present_rule)
        TextView ruleTxt;
        @Bind(R.id.txt_present_commodity_name)
        TextView nameTxt;
        @Bind(R.id.txt_present_commodity_price)
        TextView priceTxt;

        public BaseViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
