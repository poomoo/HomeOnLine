package com.poomoo.homeonline.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.poomoo.commlib.LogUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;
import com.poomoo.homeonline.ui.custom.AddAndMinusView;
import com.poomoo.model.response.RCommodityBO;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 购物车
 */
public class CommodityListAdapter extends BaseListAdapter<RCommodityBO> {

    private static final String TAG = "CommodityListAdapter" + "";
    public HashMap<Integer, Boolean> booleanHashMap = new HashMap<>();
    private int len;
    private RCommodityBO item;

    public CommodityListAdapter(Context context, int mode, int len) {
        super(context, mode);
        this.len = len;
        init(true);
    }

    public void init(boolean flag) {
        for (int i = 0; i < len; i++) {
            if (flag)
                if (i == 0)
                    booleanHashMap.put(i, true);
                else
                    booleanHashMap.put(i, false);
            else
                booleanHashMap.put(i, false);
        }
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new BaseViewHolder(mInflater.inflate(R.layout.item_list_commodity, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        LogUtils.d(TAG, "CommodityListAdapter onBindDefaultViewHolder:" + position + booleanHashMap.size());
        item = items.get(position);
        BaseViewHolder holder = (BaseViewHolder) h;
        holder.commodityChk.setChecked(false);
//        Glide.with(mContext).load(item.img).into(holder.commodityImg);
        holder.commodityTxt.setText(item.name);
        holder.addAndMinusView.setCount(item.count);
    }

    public static final class BaseViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.chk_commodity)
        CheckBox commodityChk;
        @Bind(R.id.img_commodity)
        ImageView commodityImg;
        @Bind(R.id.txt_commodity)
        TextView commodityTxt;
        @Bind(R.id.addAndMinusView)
        AddAndMinusView addAndMinusView;

        public BaseViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public HashMap<Integer, Boolean> getBooleanHashMap() {
        init(false);
        return booleanHashMap;
    }

    public void setBooleanHashMap(HashMap<Integer, Boolean> booleanHashMap) {
        this.booleanHashMap = booleanHashMap;
    }
}
