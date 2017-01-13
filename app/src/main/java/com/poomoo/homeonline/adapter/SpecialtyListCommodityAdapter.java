/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.adapter;

import android.content.Context;
import android.graphics.Paint;
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
import com.poomoo.homeonline.listeners.AddToCartListener;
import com.poomoo.model.response.RListCommodityBO;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 SpecialtyListCommodityAdapter
 * 描述 专题商品列表适配器
 * 作者 李苜菲
 * 日期 2016/7/19 11:33
 */
public class SpecialtyListCommodityAdapter extends BaseListAdapter<RListCommodityBO> {
    private RListCommodityBO item;
    private AddToCartListener addToCartListener;
    private LinearLayout.LayoutParams layoutParams;
    private int margin = 0;
    private int FLAG;
    public static final int ECOLOGICAL = 0;//生态有机
    public static final int SPECIALTY = 1;//贵州特产
    public static final int TEA_WIN = 2;//贵酒贵茶
    public static final int ARTWORK = 3;//黔匠艺品

    public SpecialtyListCommodityAdapter(Context context, int mode, int flag, AddToCartListener addToCartListener) {
        super(context, mode);
        this.addToCartListener = addToCartListener;
        margin = (int) context.getResources().getDimension(R.dimen.dp_8);
        this.FLAG = flag;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new BaseViewHolder(mInflater.inflate(R.layout.item_list_classify_commodity, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        BaseViewHolder holder = (BaseViewHolder) h;
        layoutParams = new LinearLayout.LayoutParams(MyUtils.getScreenWidth(mContext) / 3, MyUtils.getScreenWidth(mContext) / 3);//设置图片的宽高比为1:1
        layoutParams.setMargins(0, margin, margin, margin);
        holder.image.setLayoutParams(layoutParams);

        item = items.get(position);
        Glide.with(mContext)
                .load(NetConfig.ImageUrl + item.listPic)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .placeholder(R.drawable.replace)
                .priority(Priority.HIGH)
                .into(holder.image);

        holder.nameTxt.setText(item.commodityName);
        holder.priceTxt.setText("￥" + item.platformPrice);
        holder.oldPriceTxt.setText("￥" + item.commonPrice);
        holder.oldPriceTxt.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.addToCartImg.setOnClickListener(new OnClickListener(position));
        switch (FLAG) {
            case ECOLOGICAL:
                holder.logoImg.setImageResource(R.drawable.ic_logo_ecological);
                holder.addToCartImg.setImageResource(R.drawable.ic_cart_ecological);
                break;
            case SPECIALTY:
                holder.logoImg.setImageResource(R.drawable.ic_logo_specialty);
                holder.addToCartImg.setImageResource(R.drawable.ic_cart_specialty);
                break;
            case TEA_WIN:
                holder.logoImg.setImageResource(R.drawable.ic_logo_teawin);
                holder.addToCartImg.setImageResource(R.drawable.ic_cart_teawin);
                break;
            case ARTWORK:
                holder.logoImg.setImageResource(R.drawable.ic_logo_artwork);
                holder.addToCartImg.setImageResource(R.drawable.ic_cart_artwork);
                break;
        }
    }

    public static final class BaseViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_commodity)
        ImageView image;
        @Bind(R.id.txt_commodity_name)
        TextView nameTxt;
        @Bind(R.id.txt_commodity_price)
        TextView priceTxt;
        @Bind(R.id.txt_commodity_oldPrice)
        TextView oldPriceTxt;
        @Bind(R.id.img_addToCart)
        ImageView addToCartImg;
        @Bind(R.id.img_logo)
        ImageView logoImg;

        public BaseViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private class OnClickListener implements View.OnClickListener {
        int position;

        public OnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            addToCartListener.addToCart(position);
        }
    }
}
