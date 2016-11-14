package com.poomoo.homeonline.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.poomoo.api.NetConfig;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.ui.activity.AbroadActivity;
import com.poomoo.homeonline.ui.fragment.ClassifyFragment;
import com.poomoo.model.response.RAbroadClassifyBO;
import com.poomoo.model.response.RClassifyBO;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 AbroadClassifyListAdapter
 * 描述 跨境分类适配器
 * 作者 李苜菲
 * 日期 2016/7/19 11:30
 */
public class AbroadClassifyListAdapter extends BaseListAdapter<RAbroadClassifyBO> {
    private RAbroadClassifyBO item;

    public AbroadClassifyListAdapter(Context context, int mode) {
        super(context, mode);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new BaseViewHolder(mInflater.inflate(R.layout.item_list_abroad_classify, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        item = items.get(position);
        BaseViewHolder holder = (BaseViewHolder) h;
        holder.classifyTxt.setText(item.categoryName);
        if (position == AbroadActivity.SELECT_POSITION) {
            Glide.with(mContext).load(NetConfig.ImageUrl + item.pcReplacePic).priority(Priority.IMMEDIATE).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.classifyImg);
            holder.classifyTxt.setTextColor(ContextCompat.getColor(mContext, R.color.ThemeRed));
        } else {
            Glide.with(mContext).load(NetConfig.ImageUrl + item.pcPic).priority(Priority.IMMEDIATE).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.classifyImg);
            holder.classifyTxt.setTextColor(ContextCompat.getColor(mContext, R.color.gold));
        }
    }

    public static final class BaseViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_abroad_classify)
        ImageView classifyImg;
        @Bind(R.id.txt_abroad_classify)
        TextView classifyTxt;

        public BaseViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
