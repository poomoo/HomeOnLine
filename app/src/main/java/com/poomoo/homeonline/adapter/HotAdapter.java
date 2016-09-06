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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.poomoo.api.NetConfig;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.model.response.RAdBO;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 HotAdapter
 * 描述 首页热门推荐
 * 作者 李苜菲
 * 日期 2016/7/19 11:33
 */
public class HotAdapter extends BaseListAdapter<RAdBO> {
    private RAdBO item;

    public HotAdapter(Context context, int mode) {
        super(context, mode);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new BaseViewHolder(mInflater.inflate(R.layout.item_pictures, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        BaseViewHolder holder = (BaseViewHolder) h;
        holder.image.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MyUtils.getScreenWidth(mContext) / 2));//设置广告栏的宽高比为2:1

        item = items.get(position);
        Glide.with(mContext).load(NetConfig.ImageUrl + item.advertisementPic).placeholder(R.drawable.replace2).diskCacheStrategy(DiskCacheStrategy.SOURCE).centerCrop().into(holder.image);
    }

    public static final class BaseViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.image)
        ImageView image;

        public BaseViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
