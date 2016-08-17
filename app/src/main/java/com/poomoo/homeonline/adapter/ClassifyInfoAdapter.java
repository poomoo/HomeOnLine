package com.poomoo.homeonline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.ui.activity.ClassifyInfoActivity;
import com.poomoo.homeonline.ui.fragment.ClassifyFragment;
import com.poomoo.model.response.RClassifyBO;
import com.poomoo.model.response.RClassifyInfoBO;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 ClassifyInfoAdapter
 * 描述 分类详情适配器
 * 作者 李苜菲
 * 日期 2016/7/19 11:30
 */
public class ClassifyInfoAdapter extends BaseListAdapter<RClassifyInfoBO.ThreeCategoryList> {

    public ClassifyInfoAdapter(Context context, int mode) {
        super(context, mode);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new BaseViewHolder(mInflater.inflate(R.layout.item_list_classify_info, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        BaseViewHolder holder = (BaseViewHolder) h;
        holder.classifyChk.setText(items.get(position).categoryName);
        holder.classifyChk.setChecked(position == ClassifyInfoActivity.SELECTPOSITION);
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
