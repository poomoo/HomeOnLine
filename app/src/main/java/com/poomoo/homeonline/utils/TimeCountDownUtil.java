package com.poomoo.homeonline.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Html;
import android.text.Spanned;
import android.widget.Button;

import com.poomoo.homeonline.R;


/**
 * 倒计时器
 * 作者: 李苜菲
 * 日期: 2015/11/16 15:49.
 */
public class TimeCountDownUtil extends CountDownTimer {
    private String TAG = this.getClass().getSimpleName();
    private Button button;
    private long millisUntilFinished;

    // 在这个构造方法里需要传入三个参数，一个是Activity，一个是总的时间millisInFuture，一个是countDownInterval，然后就是你在哪个按钮上做这个事，就把这个按钮传过来就可以了


    public TimeCountDownUtil(long millisInFuture, long countDownInterval, Button button) {
        super(millisInFuture, countDownInterval);
        this.button = button;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        this.millisUntilFinished = millisUntilFinished;
        button.setClickable(false);// 设置不能点击
        button.setText(millisUntilFinished / 1000 + "s");// 设置倒计时时间
        button.setTextColor(Color.parseColor("#E81540"));
        // 设置按钮为灰色，这时是不能点击的
        button.setBackgroundResource(R.color.gray);

    }

    @Override
    public void onFinish() {
        button.setClickable(true);// 设置点击
        button.setText("重新获取");
        button.setTextColor(Color.parseColor("#FFFFFF"));
        button.setBackgroundResource(R.drawable.selector_button);
    }

    /**
     * 倒计时时间转换
     *
     * @param time
     * @return
     */

    public Spanned dealTime(long time) {
        Spanned str;
        StringBuffer returnString = new StringBuffer();
        //2 * 5 * 57 * 21 * 1000
        long day = time / (24 * 60 * 60);
        long hours = (time % (24 * 60 * 60)) / (60 * 60);
        long minutes = ((time % (24 * 60 * 60)) % (60 * 60)) / 60;
        long second = ((time % (24 * 60 * 60)) % (60 * 60)) % 60;
//        Log.i(TAG, "time:" + time + "day:" + day + "hours:" + hours + "minutes:" + minutes + "second:" + second);
        String dayStr = String.valueOf(day);
        String hoursStr = timeStrFormat(String.valueOf(hours));
        String minutesStr = timeStrFormat(String.valueOf(minutes));
        String secondStr = timeStrFormat(String.valueOf(second));
        returnString.append(dayStr).append("天").append(hoursStr).append("小时")
                .append(minutesStr).append("分钟").append(secondStr).append("秒");
        str = Html.fromHtml(returnString.toString());

        return str;
    }

    /**
     * 补0操作
     *
     * @param timeStr
     * @return
     */
    private static String timeStrFormat(String timeStr) {
        switch (timeStr.length()) {
            case 1:
                timeStr = "0" + timeStr;
                break;
        }
        return timeStr;
    }

}