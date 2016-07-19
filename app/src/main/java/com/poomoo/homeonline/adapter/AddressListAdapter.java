package com.poomoo.homeonline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.model.response.RReceiptBO;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 AddressListAdapter
 * 描述 地址列表适配器
 * 作者 李苜菲
 * 日期 2016/7/19 11:29
 */
public class AddressListAdapter extends BaseListAdapter<RReceiptBO> {
    private RReceiptBO item;

    private static final String TAG = "AddressListAdapter";

    public AddressListAdapter(Context context, int mode) {
        super(context, mode);
    }


    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new BaseViewHolder(mInflater.inflate(R.layout.item_list_address, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        BaseViewHolder holder = (BaseViewHolder) h;
        item = items.get(position);

        holder.nameTxt.setText(item.name);
        holder.telTxt.setText(item.tel);
        holder.addressTxt.setText(item.address);
    }

    public static final class BaseViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txt_receipt_name)
        TextView nameTxt;
        @Bind(R.id.txt_receipt_tel)
        TextView telTxt;
        @Bind(R.id.txt_receipt_address)
        TextView addressTxt;

        public BaseViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
