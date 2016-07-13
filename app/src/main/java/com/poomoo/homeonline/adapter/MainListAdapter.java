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
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.model.response.RRecommendBO;

import butterknife.Bind;
import butterknife.ButterKnife;

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
 * 作者: 李苜菲
 * 日期: 2016/7/13 16:52
 */
public class MainListAdapter extends BaseListAdapter<RRecommendBO> {
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
        RRecommendBO item = items.get(position);
        Glide.with(mContext).load(item.img).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.commodityImg);
        holder.newPriceTxt.setText("￥" + item.newPrice);
        holder.oldPriceTxt.setText("￥" + item.oldPrice);
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
