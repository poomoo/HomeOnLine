package com.poomoo.homeonline.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.model.response.RClassifyInfoBO;
import com.poomoo.model.response.RThirdClassifyBO;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.poomoo.commlib.MyConfig.ARTWORK;
import static com.poomoo.commlib.MyConfig.ECOLOGICAL;
import static com.poomoo.commlib.MyConfig.SPECIALTY;
import static com.poomoo.commlib.MyConfig.TEA_WINE;

/**
 * 类名 SpecialtyTitleAdapter
 * 描述 专题分类标题适配器
 * 作者 李苜菲
 * 日期 2016/7/19 11:30
 */
public class SpecialtyTitleAdapter extends BaseListAdapter<RThirdClassifyBO> {
    public static int SELECT_POSITION;
    public static boolean isShowAll = false;
    private ColorStateList color;
    private int FLAG;

    public SpecialtyTitleAdapter(Context context, int mode, int flag) {
        super(context, mode);
        this.FLAG = flag;
        SELECT_POSITION = 0;
        isShowAll = false;
        switch (FLAG) {
            case ECOLOGICAL:
                break;
            case SPECIALTY:
                color = ContextCompat.getColorStateList(context, R.color.selector_specialty_text);
                break;
            case TEA_WINE:
                color = ContextCompat.getColorStateList(context, R.color.selector_teawine_text);
                break;
            case ARTWORK:
                color = ContextCompat.getColorStateList(context, R.color.selector_artwork_text);
                break;
        }

    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new BaseViewHolder(mInflater.inflate(R.layout.item_list_classify_info, parent, false));
    }

    @Override
    public int getItemCount() {
        if (isShowAll)
            return super.getItemCount();
        else {
            if (super.getItemCount() >= 4) return 4;
            else
                return super.getItemCount();
        }
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        BaseViewHolder holder = (BaseViewHolder) h;

        holder.classifyChk.setTextColor(color);
        holder.classifyChk.setText(items.get(position).categoryName);
        holder.classifyChk.setChecked(position == SELECT_POSITION);

        switch (FLAG) {
            case ECOLOGICAL:
                break;
            case SPECIALTY:
                holder.classifyChk.setBackgroundResource(R.drawable.specialty_classify_title_bg);
                break;
            case TEA_WINE:
                holder.classifyChk.setBackgroundResource(R.drawable.teawine_classify_title_bg);
                break;
            case ARTWORK:
                holder.classifyChk.setBackgroundResource(R.drawable.artwork_classify_title_bg);
                break;
        }

    }

    public static final class BaseViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.chk_classify_info)
        CheckBox classifyChk;

        public BaseViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
