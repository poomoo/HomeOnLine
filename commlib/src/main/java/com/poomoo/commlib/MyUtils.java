/**
 * Copyright (c) 2015. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.commlib;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 作者: 李苜菲
 * 日期: 2015/11/17 14:41.
 */
public class MyUtils {
    private static String TAG = "MyUtil";

    public static String[] CURSOR_COLS = new String[]{MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ARTIST_ID, MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.TRACK};

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    //把字符串转为日期
    public static Date ConvertToDate(String strDate) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM");
        return df.parse(strDate);
    }

    /**
     * 格式化输入银行卡号
     *
     * @param et
     */
    public static void fortmatCardNum(final EditText et) {
        et.addTextChangedListener(new TextWatcher() {
            int beforeTextLength = 0;
            int onTextLength = 0;
            boolean isChanged = false;

            int location = 0;// 记录光标的位置
            private char[] tempChar;
            private StringBuffer buffer = new StringBuffer();
            int konggeNumberB = 0;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                onTextLength = s.length();
                buffer.append(s.toString());
                if (onTextLength == beforeTextLength || onTextLength <= 3 || isChanged) {
                    isChanged = false;
                    return;
                }
                isChanged = true;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
                beforeTextLength = s.length();
                if (buffer.length() > 0) {
                    buffer.delete(0, buffer.length());
                }
                konggeNumberB = 0;
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == ' ') {
                        konggeNumberB++;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (isChanged) {
                    location = et.getSelectionEnd();
                    int index = 0;
                    while (index < buffer.length()) {
                        if (buffer.charAt(index) == ' ') {
                            buffer.deleteCharAt(index);
                        } else {
                            index++;
                        }
                    }

                    index = 0;
                    int konggeNumberC = 0;
                    while (index < buffer.length()) {
                        if (index % 5 == 4) {
                            buffer.insert(index, ' ');
                            konggeNumberC++;
                        }
                        index++;
                    }

                    if (konggeNumberC > konggeNumberB) {
                        location += (konggeNumberC - konggeNumberB);
                    }

                    tempChar = new char[buffer.length()];
                    buffer.getChars(0, buffer.length(), tempChar, 0);
                    String str = buffer.toString();
                    if (location > str.length()) {
                        location = str.length();
                    } else if (location < 0) {
                        location = 0;
                    }

                    et.setText(str);
                    Editable etable = et.getText();
                    Selection.setSelection(etable, location);
                    isChanged = false;
                }
            }
        });
    }

    /**
     * 隐藏手机号
     *
     * @param tel
     * @return
     */
    public static String hiddenTel(String tel) {
        if (TextUtils.isEmpty(tel))
            return "";
        String temp;
        temp = tel.substring(0, 3) + tel.substring(3, 7).replaceAll("[0123456789]", "*")
                + tel.substring(7, tel.length());
        return temp;
    }

    /**
     * 隐藏身份证号
     *
     * @param num
     * @return
     */
    public static String hiddenIdCardNum(String num) {
        if (TextUtils.isEmpty(num))
            return "";
        if (num.length() != 18)
            return num;
        String temp;
        temp = num.substring(0, 3) + num.substring(3, 14).replaceAll("[0123456789]", "*")
                + num.substring(14, num.length());
        return temp;
    }

    /**
     * 隐藏银行卡号
     *
     * @param num
     * @return
     */
    public static String hiddenBankCardNum(String num) {
        if (TextUtils.isEmpty(num))
            return "";
        String temp;
        temp = num.substring(0, 4) + num.substring(4, 16).replaceAll("[0123456789]", "*")
                + num.substring(16, num.length());
        return temp;
    }

    /**
     * 去掉所有空格
     *
     * @param string
     * @return
     */
    public static String trimAll(String string) {
        return string.replaceAll(" ", "");
    }

    /**
     * double相加
     *
     * @param v1
     * @param v2
     * @return
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * double相减
     *
     * @param v1
     * @param v2
     * @return
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }


    /**
     * 是否登录
     *
     * @param activity
     * @return
     */
//    public static boolean isLogin(Activity activity) {
//        if (!(boolean) SPUtils.get(activity, activity.getString(R.string.sp_isLogin), false)) {
//            activity.finish();
//            Intent intent = new Intent(activity, LogInActivity.class);
//            activity.startActivity(intent);
//            activity.overridePendingTransition(R.anim.activity_in_from_right,
//                    R.anim.activity_center);
//            showToast(activity, "请登录");
//            return false;
//        } else
//            return true;
//    }


    /**
     * 判断app是否正在运行
     *
     * @param ctx
     * @param packageName
     * @return
     */
    public static boolean appIsRunning(Context ctx, String packageName) {
        ActivityManager am = (ActivityManager) ctx.getSystemService(ctx.ACTIVITY_SERVICE);

        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
        if (runningAppProcesses != null) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (runningAppProcessInfo.processName.startsWith(packageName)) {
                    LogUtils.i("CallAlarm", "processName:" + runningAppProcessInfo.processName + " packageName:" + packageName);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * app 是否在后台运行
     *
     * @param ctx
     * @param packageName
     * @return
     */
    public static boolean appIsBackgroundRunning(Context ctx, String packageName) {
        ActivityManager am = (ActivityManager) ctx.getSystemService(ctx.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
        if (runningAppProcesses != null) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (runningAppProcessInfo.processName.startsWith(packageName)) {
                    return runningAppProcessInfo.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && runningAppProcessInfo.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE; //排除无界面的app
                }
            }
        }
        return false;
    }

    public static void saveBitmap(Context context, Bitmap bitmap) {
        String path = Environment.getExternalStorageDirectory() + "/" + System.currentTimeMillis() + ".jpg";
        File my2code = new File(path);
        try {
            FileOutputStream out = new FileOutputStream(my2code);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            showToast(context, "图片已保存到:" + path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 日期转时间
     *
     * @return
     */
    public static long DateToTime(String dateStr) {
        long time = 0;
        Date date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        try {
            date = simpleDateFormat.parse(dateStr);
            time = date.getTime() - System.currentTimeMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    private static ConnectivityManager mCnnManager;

    public static ConnectivityManager getCnnManager(Context context) {
        if (mCnnManager == null)
            mCnnManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return mCnnManager;
    }

    /**
     * 检测是否有网络
     *
     * @return
     */
    public static boolean hasInternet(Context context) {
        return getCnnManager(context).getActiveNetworkInfo() != null && getCnnManager(context).getActiveNetworkInfo().isAvailable();
    }


    public static boolean checkPhoneNum(String tel) {
        int len = tel.length();
        if (len != 11)
            return false;
        return true;
    }

    /**
     * 关闭软键盘
     *
     * @param context
     * @param view
     */
    public static void hiddenKeyBoard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        imm.hideSoftInputFromInputMethod(view.getWindowToken(), 0);
    }

    /**
     * 是否登录
     *
     * @param context
     * @return
     */
    public static boolean isLogin(Context context) {
        return (boolean) SPUtils.get(context, "isLogin", false);
    }
//
//    /**
//     * 打开软键盘
//     *
//     * @param context
//     * @param view
//     */
//    public static void showKeyBoard(Context context, View view) {
//        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInputFromInputMethod(view.getWindowToken(), 0);
//    }

}
