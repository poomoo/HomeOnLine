package com.poomoo.homeonline.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.poomoo.commlib.MyUtils;
import com.poomoo.commlib.SPUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.listeners.EditAddressListener;
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
    private boolean isEdit;
    private EditAddressListener editAddressListener;

    public AddressListAdapter(Context context, int mode, boolean isEdit, EditAddressListener editAddressListener) {
        super(context, mode);
        this.isEdit = isEdit;
        this.editAddressListener = editAddressListener;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new BaseViewHolder(mInflater.inflate(R.layout.item_list_address, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        BaseViewHolder holder = (BaseViewHolder) h;
        item = items.get(position);

        holder.nameTxt.setText(item.consigneeName);
        holder.telTxt.setText(MyUtils.hiddenTel(item.consigneeTel));
        holder.addressTxt.setText(item.pca);
        holder.defaultTxt.setVisibility(item.isDefault ? View.VISIBLE : View.GONE);

        if (isEdit) {
            holder.editImg.setVisibility(View.GONE);
            if (item.isDefault) {
                if (TextUtils.isEmpty((String) SPUtils.get(mContext, mContext.getString(R.string.sp_receiptName), ""))) {
                    SPUtils.put(mContext, mContext.getString(R.string.sp_receiptId), item.id);
                    SPUtils.put(mContext, mContext.getString(R.string.sp_receiptName), item.consigneeName);
                    SPUtils.put(mContext, mContext.getString(R.string.sp_receiptTel), item.consigneeTel);
                    SPUtils.put(mContext, mContext.getString(R.string.sp_receiptAddress), item.pca);
                }
            }
        } else {
            holder.editImg.setVisibility(View.VISIBLE);
            holder.editImg.setOnClickListener(new click(position));
        }
    }

    public static final class BaseViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txt_receipt_name)
        TextView nameTxt;
        @Bind(R.id.txt_receipt_tel)
        TextView telTxt;
        @Bind(R.id.img_edit_address)
        ImageView editImg;
        @Bind(R.id.txt_receipt_address)
        TextView addressTxt;
        @Bind(R.id.txt_receipt_default)
        TextView defaultTxt;

        public BaseViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class click implements View.OnClickListener {
        private int position;

        public click(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            editAddressListener.OnEdit(position);
        }
    }

}
