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
import com.poomoo.homeonline.listeners.EvaluateClickListener;
import com.poomoo.model.response.ROrderListBO;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 EvaluateListAdapter
 * 描述 待评价的商品列表适配器
 * 作者 李苜菲
 * 日期 2016/8/23 17:50
 */
public class EvaluateListAdapter extends BaseListAdapter<ROrderListBO.OrderDetails> {
    private ROrderListBO.OrderDetails item;
    private EvaluateClickListener evaluateClickListener;

    public EvaluateListAdapter(Context context, int mode, EvaluateClickListener evaluateClickListener) {
        super(context, mode);
        this.evaluateClickListener = evaluateClickListener;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new BaseViewHolder(mInflater.inflate(R.layout.item_list_evaluate, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        BaseViewHolder holder = (BaseViewHolder) h;
        item = items.get(position);

        holder.nameTxt.setText(item.commodityName);
        Glide.with(mContext).load(NetConfig.ImageUrl + item.listPic).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.drawable.replace).into(holder.image);
        holder.evaluateBtn.setOnClickListener(new evaluateOnclick(position));
    }

    public static final class BaseViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_evaluate)
        ImageView image;
        @Bind(R.id.txt_evaluate_name)
        TextView nameTxt;
        @Bind(R.id.btn_evaluate)
        Button evaluateBtn;

        public BaseViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 评价
     */
    class evaluateOnclick implements View.OnClickListener {
        int position;

        public evaluateOnclick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            evaluateClickListener.evaluate(position);
        }
    }
}
