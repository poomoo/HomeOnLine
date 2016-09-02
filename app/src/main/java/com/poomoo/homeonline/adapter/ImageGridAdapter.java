/**
 * Copyright (c) 2015. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.base.MyBaseAdapter;
import com.poomoo.homeonline.picUtils.Bimp;
import com.poomoo.homeonline.picUtils.BitmapCache;
import com.poomoo.model.ImageItem;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者: 李苜菲
 * 日期: 2015/11/23 16:13.
 */
public class ImageGridAdapter extends MyBaseAdapter<ImageItem> {
    private TextCallback textcallback = null;
    public Map<String, String> map = new HashMap<>();
    public Map<String, File> files = new HashMap<>();
    private Bitmap bitmap;
    //    private File file;
    private BitmapCache cache;
    private Handler mHandler;
    private int selectTotal = 0;
    BitmapCache.ImageCallback callback = (imageView, bitmap1, params) -> {
        if (imageView != null && bitmap1 != null) {
            String url = (String) params[0];
            if (url != null && url.equals(imageView.getTag())) {
                (imageView).setImageBitmap(bitmap1);
            } else {
                Log.e(TAG, "callback, bmp not match");
            }
        } else {
            Log.e(TAG, "callback, bmp null");
        }
    };

    public ImageGridAdapter(Context context, Handler mHandler) {
        super(context);
        cache = new BitmapCache();
        this.mHandler = mHandler;
    }

    @Override
    public int getCount() {
        int count = 0;
        if (itemList != null) {
            count = itemList.size();
        }
        return count;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder;

        if (convertView == null) {
            holder = new Holder();
            convertView = inflater.inflate(R.layout.item_grid_image, null);
            holder.iv = (ImageView) convertView.findViewById(R.id.img_grid_image);
            holder.selected = (ImageView) convertView.findViewById(R.id.img_grid_image_isselected);
            holder.text = (TextView) convertView.findViewById(R.id.txt_grid_image);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        final ImageItem item = itemList.get(position);

        holder.iv.setTag(item.imagePath);
        cache.displayBmp(holder.iv, item.thumbnailPath, item.imagePath, callback);
        if (item.isSelected) {
            holder.selected.setImageResource(R.drawable.ic_checkbox_checked);
            holder.text.setBackgroundResource(R.drawable.bg_relatly_line);
        } else {
            holder.selected.setImageResource(-1);
            holder.text.setBackgroundColor(0x00000000);
        }
        holder.iv.setOnClickListener(v -> {
            String path = itemList.get(position).imagePath;

            if ((Bimp.drr.size() + selectTotal) < 3) {
                item.isSelected = !item.isSelected;
                if (item.isSelected) {
                    holder.selected.setImageResource(R.drawable.ic_checkbox_checked);
                    holder.text.setBackgroundResource(R.drawable.bg_relatly_line);
                    selectTotal++;
                    if (textcallback != null)
                        textcallback.onListen(selectTotal);
                    map.put(path, path);
                    try {
                        bitmap = Bimp.revitionImageSize(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (!item.isSelected) {
                    holder.selected.setImageResource(-1);
                    holder.text.setBackgroundColor(0x00000000);
                    selectTotal--;
                    if (textcallback != null)
                        textcallback.onListen(selectTotal);
                    map.remove(path);
                    files.remove(path);
                }
            } else if ((Bimp.drr.size() + selectTotal) >= 3) {
                if (item.isSelected == true) {
                    item.isSelected = !item.isSelected;
                    holder.selected.setImageResource(-1);
                    holder.text.setBackgroundColor(0x00000000);
                    selectTotal--;
                    map.remove(path);
                    files.remove(path);
                } else {
                    Message message = Message.obtain(mHandler, 0);
                    message.sendToTarget();
                }
            }
        });

        return convertView;
    }

    class Holder {
        private ImageView iv;
        private ImageView selected;
        private TextView text;
    }

    public void setTextCallback(TextCallback listener) {
        textcallback = listener;
    }

    public static interface TextCallback {
        public void onListen(int count);
    }
}
