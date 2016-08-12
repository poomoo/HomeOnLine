package com.poomoo.homeonline.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.poomoo.api.NetConfig;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.model.response.RGrabBO;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 MainListAdapter
 * 描述 首页推荐适配器
 * 作者 李苜菲
 * 日期 2016/7/19 11:32
 */
public class MainListAdapter extends BaseListAdapter<RGrabBO> {
    private RGrabBO item;

    public MainListAdapter(Context context, int mode) {
        super(context, mode);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new BaseViewHolder(mInflater.inflate(R.layout.item_list_recommend, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        BaseViewHolder holder = (BaseViewHolder) h;
        item = items.get(position);
        Glide.with(mContext).load(NetConfig.ImageUrl + item.listPic).placeholder(R.drawable.replace).into(holder.commodityImg);
        holder.newPriceTxt.setText("￥" + item.rushPurchasePrice);
        holder.oldPriceTxt.setText("￥" + item.platformPrice);
        holder.oldPriceTxt.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }


    public static final class BaseViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_commodity)
        ImageView commodityImg;
        @Bind(R.id.txt_newPrice)
        TextView newPriceTxt;
        @Bind(R.id.txt_oldPrice)
        TextView oldPriceTxt;

        public BaseViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
