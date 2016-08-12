package com.poomoo.homeonline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.poomoo.api.NetConfig;
import com.poomoo.commlib.LogUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.ui.activity.CollectActivity;
import com.poomoo.model.response.RCollectBO;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类名 CollectionListAdapter
 * 描述 收藏列表适配器
 * 作者 李苜菲
 * 日期 2016/7/19 11:29
 */
public class CollectionListAdapter extends BaseListAdapter<RCollectBO> {
    private RCollectBO item;
    private List<RCollectBO> indexList = new ArrayList<>();

    public CollectionListAdapter(Context context, int mode) {
        super(context, mode);
    }


    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new BaseViewHolder(mInflater.inflate(R.layout.item_list_collection, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        BaseViewHolder holder = (BaseViewHolder) h;
        item = items.get(position);

        Glide.with(mContext).load(NetConfig.ImageUrl + item.listPic).placeholder(R.drawable.replace).into(holder.commodityImg);
        holder.commodityTxt.setText(item.commodityName);
        holder.priceTxt.setText(item.commodityPrice + "");
        holder.commodityChk.setChecked(item.isChecked);

        holder.commodityLayout.setOnClickListener(new CheckBox_Click(position));
    }

    class CheckBox_Click implements View.OnClickListener {
        private int position;

        CheckBox_Click(int position) {
            this.position = position;
        }

        public void onClick(View v) {
            item = items.get(position);
            item.toggleChecked();
            if (item.isChecked)
                indexList.add(item);
            else
                indexList.remove(item);
            CollectActivity.inStance.isClick = false;
            CollectActivity.inStance.checkBox.setChecked(isAllSelected());
            LogUtils.d(TAG, "isAllSelected:" + isAllSelected() + " indexList.size()" + indexList.size());
            notifyDataSetChanged();
        }
    }

    public List<RCollectBO> getIndexList() {
        return indexList;
    }

    public int[] getIndexs() {
        int[] ids = new int[indexList.size()];
        int i = 0;
        for (RCollectBO rCollectBO : indexList)
            ids[i++] = rCollectBO.id;
        return ids;
    }

    public boolean isAllSelected() {
        if (indexList.size() == getItemCount() - 1)
            return true;
        return false;
    }

    public void selectAll() {
        for (RCollectBO rCollectBO : items)
            rCollectBO.isChecked = true;
        notifyDataSetChanged();
    }

    public void cancelAll() {
        for (RCollectBO rCollectBO : items)
            rCollectBO.isChecked = false;
        notifyDataSetChanged();
    }

    public void removeItems() {
        for (RCollectBO rCollectBO : indexList)
            items.remove(rCollectBO);
        indexList = new ArrayList<>();
        notifyDataSetChanged();
    }

    public static final class BaseViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.llayout_commodity)
        LinearLayout commodityLayout;
        @Bind(R.id.chk_commodity)
        CheckBox commodityChk;
        @Bind(R.id.img_commodity)
        ImageView commodityImg;
        @Bind(R.id.txt_commodity)
        TextView commodityTxt;
        @Bind(R.id.txt_price)
        TextView priceTxt;

        public BaseViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
