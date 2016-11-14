/**
 * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG            #
 * #                                                   #
 * Copyright (c) 2016. 跑马科技 Inc. All rights reserved.
 */
package com.poomoo.homeonline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
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
import com.poomoo.model.response.RCartCommodityBO;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 ConfirmOrderAdapter
 * 描述 确认订单
 * 作者 李苜菲
 * 日期 2016/8/17 16:09
 */
public class ConfirmOrderAdapter extends BaseListAdapter<RCartCommodityBO> {

    private RCartCommodityBO item;
    private int presentCount = 0;

    public ConfirmOrderAdapter(Context context, int mode) {
        super(context, mode);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new BaseViewHolder(mInflater.inflate(R.layout.item_list_confirm_order, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        BaseViewHolder holder = (BaseViewHolder) h;
        item = items.get(position);

        holder.nameTxt.setText(item.commodityName);
        holder.priceTxt.setText(item.commodityPrice + "");
        holder.countTxt.setText(item.commodityNum + "");
        LogUtils.d(TAG, "commodityType:" + item.commodityType);
        if (item.commodityType == 4) {//买赠
            if (!TextUtils.isEmpty(item.present)) {
                holder.linearLayout.setVisibility(View.VISIBLE);
                holder.presentCountTxt.setText(item.present);
            } else {
                presentCount = MyUtils.CalculatePresentCount(item.activityRule, item.commodityNum);
                if (presentCount > 0) {
                    holder.linearLayout.setVisibility(View.VISIBLE);
                    holder.presentCountTxt.setText(item.commodityName + " X" + presentCount);
                }
            }
        }
        Glide.with(mContext).load(NetConfig.ImageUrl + item.listPic).placeholder(R.drawable.replace).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.commodityImg);
    }

    public static final class BaseViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_order_commodity)
        ImageView commodityImg;
        @Bind(R.id.txt_order_commodity)
        TextView nameTxt;
        @Bind(R.id.txt_order_price)
        TextView priceTxt;
        @Bind(R.id.txt_order_commodity_count)
        TextView countTxt;
        @Bind(R.id.llayout_order_present)
        LinearLayout linearLayout;
        @Bind(R.id.txt_order_present_count)
        TextView presentCountTxt;

        public BaseViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
