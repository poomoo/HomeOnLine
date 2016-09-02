/**
 * Copyright (c) 2015. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.homeonline.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.poomoo.commlib.LogUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.base.MyBaseAdapter;
import com.poomoo.homeonline.picUtils.Bimp;
import com.poomoo.homeonline.picUtils.FileUtils;

import java.io.IOException;

/**
 * 类名 AddPicsAdapter
 * 描述 选择上传图片适配器
 * 作者 李苜菲
 * 日期 2016/7/19 11:29
 */
public class AddPicsAdapter extends MyBaseAdapter {
    private Context context;

    public AddPicsAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        int size = Bimp.drr.size();
        if (size == 0)
            return 1;
        else if (size == 3)
            return 3;
        else
            return size + 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_grid_pics, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.img_grid);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        LogUtils.d(TAG, "position:" + position + " drr" + Bimp.drr.get(position));
        if (position == Bimp.drr.size()) {
            if (position == 3)
                holder.image.setVisibility(View.GONE);
            else
                Glide.with(context).load(R.drawable.ic_add_image).into(holder.image);
//            holder.image.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_add_image));
        } else {
            Glide.with(context).load(Bimp.drr.get(position)).into(holder.image);
//            holder.image.setImageBitmap(Bimp.bmp.get(position));
        }
        return convertView;
    }

    public class ViewHolder {
        public ImageView image;
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    AddPicsAdapter.this.notifyDataSetChanged();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public void update() {
        loading();
    }

    public void loading() {
        new Thread(() -> {
            while (true) {
                LogUtils.d("lmf", "Bimp.max:" + Bimp.max + " Bimp.drr:" + Bimp.drr.toString());
                if (Bimp.max == Bimp.drr.size()) {
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                    break;
                } else {
                    try {
                        if (Bimp.drr.size() > 0) {
                            String path = Bimp.drr.get(Bimp.max);
                            if (!path.startsWith("http")) {
                                Bitmap bm = Bimp.revitionImageSize(path);
//                                Bimp.bmp.add(bm);
                                String newStr = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
                                Bimp.files.add(FileUtils.saveBitmap(bm, "" + newStr));
                            }
                        }
                        Bimp.max += 1;
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
