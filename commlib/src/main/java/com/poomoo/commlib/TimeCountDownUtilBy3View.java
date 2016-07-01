package com.poomoo.commlib;

import android.os.CountDownTimer;
import android.text.Spanned;
import android.widget.TextView;

/**
 * 首页倒计时器
 * 作者: 李苜菲
 * 日期: 2015/11/16 15:49.
 */
public class TimeCountDownUtilBy3View extends CountDownTimer {
    private String TAG = this.getClass().getSimpleName();
    private TextView hourTxt;
    private TextView minuteTxt;
    private TextView secondTxt;
    private String hoursStr;
    private String minutesStr;
    private String secondStr;

    public TimeCountDownUtilBy3View(long millisInFuture, long countDownInterval, TextView hour, TextView minute, TextView second) {
        super(millisInFuture, countDownInterval);
        this.hourTxt = hour;
        this.minuteTxt = minute;
        this.secondTxt = second;
    }


    @Override
    public void onTick(long millisUntilFinished) {
//        LogUtils.d(TAG, "onTick:" + "millisUntilFinished->" + millisUntilFinished + "hoursStr->" + hoursStr + "minutesStr->" + minutesStr + "secondStr->" + secondStr);
        dealTime(millisUntilFinished / 1000);
        hourTxt.setText(hoursStr);
        minuteTxt.setText(minutesStr);
        secondTxt.setText(secondStr);
    }

    @Override
    public void onFinish() {
        hourTxt.setText("00");
        minuteTxt.setText("00");
        secondTxt.setText("00");
    }

    /**
     * 倒计时时间转换
     *
     * @param time
     * @return
     */

    public void dealTime(long time) {
        long hours = (time % (24 * 60 * 60)) / (60 * 60);
        long minutes = ((time % (24 * 60 * 60)) % (60 * 60)) / 60;
        long second = ((time % (24 * 60 * 60)) % (60 * 60)) % 60;
        hoursStr = timeStrFormat(String.valueOf(hours));
        minutesStr = timeStrFormat(String.valueOf(minutes));
        secondStr = timeStrFormat(String.valueOf(second));
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