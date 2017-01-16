/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.poomoo.commlib.LogUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.base.MyBaseAdapter;
import com.poomoo.model.response.RCateBO;

/**
 * 类名 MainGridAdapter
 * 描述 首页类别适配器
 * 作者 李苜菲
 * 日期 2016/7/19 11:31
 */
public class MainGridAdapter extends MyBaseAdapter<RCateBO> {
    private boolean isWholeSale;
    private RCateBO item;
    private final static int[] wholeSaleLogo = {R.drawable.ic_wholesale_taste, R.drawable.ic_wholesale_wine, R.drawable.ic_wholesale_artware, R.drawable.ic_wholesale_food, R.drawable.ic_wholesale_global, R.drawable.ic_wholesale_infant, R.drawable.ic_wholesale_beauty, R.drawable.ic_wholesale_house};
    public final static String[] wholeSaleName = {"贵州味道", "贵酒贵茶", "黔匠艺品", "食品酒水", "全球优品", "母婴用品", "美妆个护", "家居生活"};
    private final static int[] type = {R.drawable.ic_grab_new, R.drawable.ic_tickets_new, R.drawable.ic_present_new, R.drawable.ic_abroad_new, R.drawable.ic_ecological_new, R.drawable.ic_specialty_new, R.drawable.ic_teawin_new, R.drawable.ic_artwork_new};

    public MainGridAdapter(Context context, boolean isWholeSale) {
        super(context);
        this.isWholeSale = isWholeSale;
    }

//    public void setUrl(String url) {
//        this.url = url;
//    }


    @Override
    public int getCount() {
        if (!isWholeSale)
            return itemList.size();
        else
            return 8;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        viewHolder = new ViewHolder();

        convertView = inflater.inflate(R.layout.item_grid_main, null);
        viewHolder.image = (ImageView) convertView.findViewById(R.id.img_main);
        viewHolder.txt = (TextView) convertView.findViewById(R.id.txt_main);

        if (!isWholeSale) {
            item = itemList.get(position);
            viewHolder.txt.setText(item.categoryName);
            viewHolder.image.setImageResource(type[position]);
        } else {
            viewHolder.image.setImageResource(wholeSaleLogo[position]);
            viewHolder.txt.setText(wholeSaleName[position]);
        }

        return convertView;
    }

    class ViewHolder {
        private ImageView image;
        private TextView txt;
    }

}
