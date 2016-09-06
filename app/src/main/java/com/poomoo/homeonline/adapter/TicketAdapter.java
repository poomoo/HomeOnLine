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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.poomoo.api.NetConfig;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.model.response.RListCommodityBO;
import com.poomoo.model.response.RTicketBO;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 TicketAdapter
 * 描述 优惠券适配器
 * 作者 李苜菲
 * 日期 2016/8/12 10:22
 */
public class TicketAdapter extends BaseListAdapter<RTicketBO.Ticket> {
    private RTicketBO.Ticket item;

    public TicketAdapter(Context context, int mode) {
        super(context, mode);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new BaseViewHolder(mInflater.inflate(R.layout.item_list_ticket, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        BaseViewHolder holder = (BaseViewHolder) h;
        item = items.get(position);
        switch (item.status) {
            case "0":
                Glide.with(mContext).load(NetConfig.ImageUrl + item.voucherPic).into(holder.ticketImg);
                break;
            case "1":
                Glide.with(mContext).load(NetConfig.ImageUrl + item.voucheredPic).into(holder.ticketImg);
                break;
            case "2":
                Glide.with(mContext).load(NetConfig.ImageUrl + item.expiredCouponsPic).into(holder.ticketImg);
                break;
        }
        holder.dateTxt.setText("使用期限:"+item.updateTime.split(" ")[0]+"至"+item.periodValidity.split(" ")[0]);
    }

    public static final class BaseViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_ticket)
        ImageView ticketImg;
        @Bind(R.id.txt_ticket_date)
        TextView dateTxt;

        public BaseViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
