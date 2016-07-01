package com.poomoo.homeonline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.poomoo.commlib.LogUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.base.BaseListAdapter;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 */
public class ClassifyListAdapter extends BaseListAdapter<String> {

    private static final String TAG = "ClassifyListAdapter";
    public HashMap<Integer, Boolean> booleanHashMap = new HashMap<>();
    private int len;

    public ClassifyListAdapter(Context context, int mode, int len) {
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
        return new BaseViewHolder(mInflater.inflate(R.layout.item_list_classify, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
//        LogUtils.d(TAG, "onBindDefaultViewHolder:" + position + booleanHashMap.size());
        BaseViewHolder holder = (BaseViewHolder) h;
        holder.classifyTxt.setText(items.get(position));
        if (booleanHashMap.get(position)) {
            holder.classifyTxt.setTextColor(mContext.getResources().getColor(R.color.ThemeRed));
            holder.classifyTxt.setBackgroundColor(mContext.getResources().getColor(R.color.ThemeBg));
        } else {
            holder.classifyTxt.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.classifyTxt.setBackgroundColor(mContext.getResources().getColor(R.color.white));
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

    public HashMap<Integer, Boolean> getBooleanHashMap() {
        init(false);
        return booleanHashMap;
    }

    public void setBooleanHashMap(HashMap<Integer, Boolean> booleanHashMap) {
        this.booleanHashMap = booleanHashMap;
    }
}
