package com.poomoo.commlib;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * 倒计时器
 * 作者: 李苜菲
 * 日期: 2015/11/16 15:49.
 */
public class TimeCountDownUtil extends CountDownTimer {
    private String TAG = this.getClass().getSimpleName();
    private TextView textView;
    private CountDownListener countDownListener;

    public TimeCountDownUtil(long millisInFuture, long countDownInterval, TextView textView) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
    }


    // 在这个构造方法里需要传入三个参数，一个是Activity，一个是总的时间millisInFuture，一个是countDownInterval，然后就是你在哪个按钮上做这个事，就把这个按钮传过来就可以了
    public TimeCountDownUtil(long millisInFuture, long countDownInterval, TextView textView, final CountDownListener countDownListener) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
        this.countDownListener = countDownListener;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        textView.setClickable(false);// 设置不能点击
        textView.setText(millisUntilFinished / 1000 + "s");// 设置倒计时时间
    }

    @Override
    public void onFinish() {
        textView.setClickable(true);// 设置点击
        textView.setEnabled(true);
        if (countDownListener == null)
            textView.setText("重新获取");
        else
            countDownListener.onFinish(1);
    }

}