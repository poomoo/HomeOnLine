package com.poomoo.homeonline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.poomoo.commlib.LogUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.listeners.ClassifyOnItemClickListener;
import com.poomoo.homeonline.ui.custom.NoScrollGridView;
import com.poomoo.model.response.RClassifyBO;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 */
public class SubClassifyListAdapter extends BaseListAdapter<RClassifyBO> {

    private static final String TAG = "SubClassifyListAdapter";
    private SubClassifyGridAdapter subClassifyGridAdapter;
    private ClassifyOnItemClickListener onItemClickListener;

    public SubClassifyListAdapter(Context context, int mode, ClassifyOnItemClickListener onItemClickListener) {
        super(context, mode);
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new BaseViewHolder(mInflater.inflate(R.layout.item_list_subclassify, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {

        BaseViewHolder holder = (BaseViewHolder) h;
        RClassifyBO item = items.get(position);
        LogUtils.d(TAG, "item:" + item + "position:" + position);
        holder.classifyTxt.setText(item.subTitle);

        subClassifyGridAdapter = new SubClassifyGridAdapter(mContext,onItemClickListener);
        holder.gridView.setAdapter(subClassifyGridAdapter);
        subClassifyGridAdapter.addItems(item.rSubClassifyBOs);
    }

    public static final class BaseViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txt_subTitle)
        TextView classifyTxt;
        @Bind(R.id.grid_subClassify)
        NoScrollGridView gridView;

        public BaseViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
