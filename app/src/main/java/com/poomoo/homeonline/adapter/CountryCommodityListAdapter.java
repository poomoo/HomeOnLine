package com.poomoo.homeonline.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.poomoo.api.NetConfig;
import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.listeners.AdvertisementListener;
import com.poomoo.homeonline.ui.activity.CollectActivity;
import com.poomoo.homeonline.ui.custom.SlideShowView;
import com.poomoo.model.response.RCollectBO;
import com.poomoo.model.response.RListCommodityBO;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 CountryCommodityListAdapter
 * 描述 国家馆商品适配器
 * 作者 李苜菲
 * 日期 2016/7/19 11:29
 */
public class CountryCommodityListAdapter extends BaseListAdapter<RListCommodityBO> {
    private RListCommodityBO item;

    public CountryCommodityListAdapter(Context context, int mode) {
        super(context, mode);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new BaseViewHolder(mInflater.inflate(R.layout.item_list_country_commodity, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        BaseViewHolder holder = (BaseViewHolder) h;
        item = items.get(position);

        Glide.with(mContext).load(NetConfig.ImageUrl + item.listPic).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.drawable.replace).into(holder.commodityImg);
        holder.nameTxt.setText(item.commodityName);
        holder.priceTxt.setText("￥" + item.platformPrice);
        holder.oldPriceTxt.setText("￥" + item.commonPrice);
        holder.oldPriceTxt.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.subTitleTxt.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    public static final class BaseViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_info_country_commodity)
        ImageView commodityImg;
        @Bind(R.id.txt_country_info_commodityName)
        TextView nameTxt;
        @Bind(R.id.txt_country_info_commodity_subTitle)
        TextView subTitleTxt;
        @Bind(R.id.txt_country_info_commodity_onSale)
        TextView saleTxt;
        @Bind(R.id.txt_country_info_commodity_type)
        TextView typeTxt;
        @Bind(R.id.txt_country_info_commodity_price)
        TextView priceTxt;
        @Bind(R.id.txt_country_info_commodity_oldPrice)
        TextView oldPriceTxt;

        public BaseViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
