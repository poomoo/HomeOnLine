package com.poomoo.homeonline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.poomoo.commlib.LogUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.ui.fragment.ClassifyFragment;
import com.poomoo.model.response.RClassifyBO;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 ClassifyListAdapter
 * 描述 一级分类适配器
 * 作者 李苜菲
 * 日期 2016/7/19 11:30
 */
public class ClassifyListAdapter extends BaseListAdapter<RClassifyBO> {

    public ClassifyListAdapter(Context context, int mode) {
        super(context, mode);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new BaseViewHolder(mInflater.inflate(R.layout.item_list_classify, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        BaseViewHolder holder = (BaseViewHolder) h;
        holder.classifyTxt.setText(items.get(position).categoryName);
        holder.classifyTxt.setTextColor(mContext.getResources().getColor(R.color.black));
        holder.classifyTxt.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        if (position == ClassifyFragment.SELECTPOSITION) {
            holder.classifyTxt.setTextColor(mContext.getResources().getColor(R.color.ThemeRed));
            holder.classifyTxt.setBackgroundColor(mContext.getResources().getColor(R.color.ThemeBg));
        }
    }

    public static final class BaseViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txt_classify)
        TextView classifyTxt;

        public BaseViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
