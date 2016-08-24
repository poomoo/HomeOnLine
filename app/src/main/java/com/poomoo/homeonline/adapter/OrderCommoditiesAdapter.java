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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.poomoo.api.NetConfig;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.listeners.ReFundClickListener;
import com.poomoo.model.response.ROrderListBO;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 OrderCommoditiesAdapter
 * 描述 订单商品列表适配器
 * 作者 李苜菲
 * 日期 2016/8/19 10:14
 */
public class OrderCommoditiesAdapter extends BaseListAdapter<ROrderListBO.OrderDetails> {
    private DecimalFormat df = new DecimalFormat("0.00");
    private ReFundClickListener reFundClickListener;
    private int groupPosition;

    public OrderCommoditiesAdapter(Context context, int mode, int groupPosition, ReFundClickListener reFundClickListener) {
        super(context, mode);
        this.reFundClickListener = reFundClickListener;
        this.groupPosition = groupPosition;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new BaseViewHolder(mInflater.inflate(R.layout.item_list_order_commodities, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        BaseViewHolder holder = (BaseViewHolder) h;
        ROrderListBO.OrderDetails item = items.get(position);
        holder.nameTxt.setText(item.commodityName);
        holder.priceTxt.setText("￥" + df.format(item.unitPrice));
        holder.countTxt.setText(item.commodityNum + "");
        holder.totalPriceTxt.setText("￥" + df.format(item.unitPrice * item.commodityNum));
        if (item.isReturnGood)
            holder.reFundTxt.setText("已申请退款/退货");
        else
            holder.reFundTxt.setText("退款/退货");
        if (item.state == 4 || item.state == 6)
            holder.reFundTxt.setVisibility(View.VISIBLE);
        else
            holder.reFundTxt.setVisibility(View.GONE);
        holder.reFundTxt.setOnClickListener(new reFundOnclick(position));
        Glide.with(mContext).load(NetConfig.ImageUrl + item.listPic).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.drawable.replace).into(holder.orderImg);
    }

    public static final class BaseViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_order)
        ImageView orderImg;
        @Bind(R.id.txt_commodity_name)
        TextView nameTxt;
        @Bind(R.id.txt_commodity_price)
        TextView priceTxt;
        @Bind(R.id.txt_commodity_count)
        TextView countTxt;
        @Bind(R.id.txt_commodity_total_price)
        TextView totalPriceTxt;
        @Bind(R.id.txt_reFund)
        TextView reFundTxt;


        public BaseViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 催单
     */
    class reFundOnclick implements View.OnClickListener {
        int position;

        public reFundOnclick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            reFundClickListener.refund(groupPosition, position);
        }
    }

}
