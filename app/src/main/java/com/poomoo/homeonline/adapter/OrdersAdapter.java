package com.poomoo.homeonline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.model.response.ROrderBO;

import butterknife.ButterKnife;

/**
 * 类名 OrdersAdapter
 * 描述 订单适配器
 * 作者 李苜菲
 * 日期 2016/7/19 11:32
 */
public class OrdersAdapter extends BaseListAdapter<ROrderBO> {
    private boolean hasType = false;

    public OrdersAdapter(Context context, int mode, boolean hasType) {
        super(context, mode);
        this.hasType = hasType;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new BaseViewHolder(mInflater.inflate(R.layout.item_list_order, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        BaseViewHolder holder = (BaseViewHolder) h;
        ROrderBO item = items.get(position);
    }

    public static final class BaseViewHolder extends RecyclerView.ViewHolder {


        public BaseViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
