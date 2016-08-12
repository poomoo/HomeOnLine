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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.poomoo.api.NetConfig;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.model.response.RAdBO;
import com.poomoo.model.response.RGuessBO;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 GuessAdapter
 * 描述 首页猜你喜欢
 * 作者 李苜菲
 * 日期 2016/7/19 11:33
 */
public class GuessAdapter extends BaseListAdapter<RGuessBO> {
    private RGuessBO item;

    public GuessAdapter(Context context, int mode) {
        super(context, mode);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new BaseViewHolder(mInflater.inflate(R.layout.item_list_guess, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        BaseViewHolder holder = (BaseViewHolder) h;
        holder.image.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MyUtils.getScreenWidth(mContext) / 2));//设置广告栏的宽高比为1:1

        item = items.get(position);
        Glide.with(mContext).load(NetConfig.ImageUrl + item.listPic).placeholder(R.drawable.replace).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.image);
        holder.nameTxt.setText(item.commodityName);
        holder.priceTxt.setText("￥" + item.platformPrice);
    }

    public static final class BaseViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_guess)
        ImageView image;
        @Bind(R.id.txt_guess_name)
        TextView nameTxt;
        @Bind(R.id.txt_guess_price)
        TextView priceTxt;

        public BaseViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
