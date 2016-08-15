package com.poomoo.homeonline.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.SPUtils;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.ui.base.BaseActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 类名 SplashActivity
 * 描述 启动页
 * 作者 李苜菲
 * 日期 2016/7/19 11:22
 */
public class SplashActivity extends BaseActivity {

    private final int SPLASH_DISPLAY_LENGHT = 3000;
    private boolean isIndex = false;//是否需要引导
    private static String DB_PATH = "/data/data/com.poomoo.homeonline/databases/";
    private static String DB_NAME = "homeOnLine.db";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        importDB();
        isIndex = (boolean) SPUtils.get(getApplicationContext(), getString(R.string.sp_isIndex), true);
        new Handler().postDelayed(() -> {
            if (isIndex) {
                SPUtils.put(getApplicationContext(), getString(R.string.sp_isIndex), false);
                openActivity(IndexViewPagerActivity.class);
                finish();
            } else {
                application.setUserId((Integer) SPUtils.get(getApplicationContext(), getString(R.string.sp_userId), null));
                application.setNickName((String) SPUtils.get(getApplicationContext(), getString(R.string.sp_nickName), ""));
                application.setTel((String) SPUtils.get(getApplicationContext(), getString(R.string.sp_phoneNum), ""));
                openActivity(MainNewActivity.class);
                finish();
            }

        }, SPLASH_DISPLAY_LENGHT);
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected int onSetTitle() {
        return 0;
    }

    private void importDB() {
        // TODO 自动生成的方法存根
        try {
            // 获得.db文件的绝对路径
            String databaseFilename = DB_PATH + DB_NAME;
            File dir = new File(DB_PATH);
            // 如果目录不存在，创建这个目录
            if (!dir.exists())
                dir.mkdir();
            boolean isExists = (new File(databaseFilename)).exists();
            LogUtils.d(TAG, "importDB:" + isExists);
            // 如果在目录中不存在 .db文件，则从res\assets目录中复制这个文件到该目录
            if (!isExists) {
                LogUtils.d(TAG, "文件不存在");
                // 获得封装.db文件的InputStream对象
                InputStream is = getAssets().open(DB_NAME);
                FileOutputStream fos = new FileOutputStream(databaseFilename);
                byte[] buffer = new byte[7168];
                int count = 0;
                // 开始复制.db文件
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
                LogUtils.d(TAG, "导入数据库文件结束");
            }
        } catch (Exception e) {
            LogUtils.d(TAG, "EXCEPTION:" + e.getMessage());
        }
    }
}
