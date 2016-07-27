package com.poomoo.homeonline.ui.activity.pics;

import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.ui.base.BaseActivity;
import com.poomoo.homeonline.ui.custom.PinchImageView;


public class PicViewActivity extends BaseActivity {

    private static final long ANIM_TIME = 200;

    private Matrix mThumbImageMatrix;


    private PinchImageView mImageView;

    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Point thumbSize = new Point(100, 100);
        url = getIntent().getStringExtra(getString(R.string.intent_value));

        //view初始化
        mImageView = (PinchImageView) findViewById(R.id.img_big);
        Glide.with(this).load(R.drawable.test).into(mImageView);
        mImageView.post(new Runnable() {
            @Override
            public void run() {
                mImageView.setAlpha(1f);
                //图片放大动画
                RectF thumbImageMatrixRect = new RectF();
                PinchImageView.MathUtils.calculateScaledRectInContainer(new RectF(), thumbSize.x, thumbSize.y, ImageView.ScaleType.FIT_CENTER, thumbImageMatrixRect);
                RectF bigImageMatrixRect = new RectF();
                PinchImageView.MathUtils.calculateScaledRectInContainer(new RectF(0, 0, mImageView.getWidth(), mImageView.getHeight()), thumbSize.x, thumbSize.y, ImageView.ScaleType.FIT_CENTER, bigImageMatrixRect);
                mThumbImageMatrix = new Matrix();
                PinchImageView.MathUtils.calculateRectTranslateMatrix(bigImageMatrixRect, thumbImageMatrixRect, mThumbImageMatrix);
                mImageView.outerMatrixTo(mThumbImageMatrix, 0);
                mImageView.outerMatrixTo(new Matrix(), ANIM_TIME);
            }
        });
    }

    @Override
    protected int onBindLayout() {
        return R.layout.dialog_pic_view;
    }

    @Override
    protected int onSetTitle() {
        return 0;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return true;
    }
}