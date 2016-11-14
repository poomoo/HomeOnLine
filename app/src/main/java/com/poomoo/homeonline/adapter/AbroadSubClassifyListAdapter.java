package com.poomoo.homeonline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.poomoo.api.NetConfig;
import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.listeners.ClassifyOnItemClickListener;
import com.poomoo.model.response.RAbroadCommodityBO;
import com.poomoo.model.response.RListCommodityBO;
import com.poomoo.model.response.RSubClassifyBO;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 AbroadSubClassifyListAdapter
 * 描述 跨境二级分类适配器
 * 作者 李苜菲
 * 日期 2016/7/19 11:33
 */
public class AbroadSubClassifyListAdapter extends BaseListAdapter<RAbroadCommodityBO> {
    private RAbroadCommodityBO item;

    public AbroadSubClassifyListAdapter(Context context, int mode) {
        super(context, mode);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new ViewHolder(mInflater.inflate(R.layout.item_list_abroad_subclassify_content, parent, false));
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        item = items.get(position);
        ViewHolder viewHolder = (ViewHolder) h;
        viewHolder.nameTxt.setText(item.commodityName);
        viewHolder.priceTxt.setText("￥ " + item.lowestPriceDetail.platformPrice);
        Glide.with(mContext).load(NetConfig.ImageUrl + item.listPic).placeholder(R.drawable.replace).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(viewHolder.classifyImg);
    }

    public static final class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_abroad_commodity)
        ImageView classifyImg;
        @Bind(R.id.txt_abroad_commodity_name)
        TextView nameTxt;
        @Bind(R.id.txt_abroad_commodity_price)
        TextView priceTxt;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
