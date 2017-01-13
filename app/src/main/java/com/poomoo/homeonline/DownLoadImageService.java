package com.poomoo.homeonline;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.poomoo.homeonline.listeners.ImageDownLoadCallBack;

/**
 * 下载图片
 */
public class DownLoadImageService implements Runnable {
    private String url;
    private Context context;
    private ImageDownLoadCallBack callBack;
    private int group = 0;

    public DownLoadImageService(Context context, String url, ImageDownLoadCallBack callBack) {
        this.url = url;
        this.callBack = callBack;
        this.context = context;
    }

    public DownLoadImageService(Context context, String url, int group, ImageDownLoadCallBack callBack) {
        this.url = url;
        this.callBack = callBack;
        this.context = context;
        this.group = group;
    }

    @Override
    public void run() {
        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bitmap != null) {
                callBack.onDownLoadSuccess(bitmap);
            } else {
                callBack.onDownLoadFailed();
            }
        }
    }

}