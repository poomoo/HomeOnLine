package com.poomoo.homeonline.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.poomoo.api.HttpLoggingInterceptor;
import com.poomoo.api.NetConfig;
import com.poomoo.api.NetWork;
import com.poomoo.commlib.LogUtils;
import com.poomoo.commlib.MyUtils;
import com.poomoo.commlib.SPUtils;
import com.poomoo.homeonline.DownLoadImageService;
import com.poomoo.homeonline.R;
import com.poomoo.homeonline.adapter.CountryCommodityListAdapter;
import com.poomoo.homeonline.listeners.ImageDownLoadCallBack;
import com.poomoo.homeonline.presenters.SplashPresenter;
import com.poomoo.homeonline.reject.components.DaggerActivityComponent;
import com.poomoo.homeonline.reject.modules.ActivityModule;
import com.poomoo.homeonline.ui.base.BaseDaggerActivity;
import com.poomoo.model.response.RIndexBO;
import com.poomoo.model.response.RVersionBO;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.poomoo.commlib.MyConfig.COUNTDOWNTIBTERVAL;

/**
 * 类名 SplashActivity
 * 描述 启动页
 * 作者 李苜菲
 * 日期 2016/7/19 11:22
 */
public class SplashActivity extends BaseDaggerActivity<SplashPresenter> {
    @Bind(R.id.txt_version)
    TextView versionTxt;
    @Bind(R.id.layout_version)
    LinearLayout versionLayout;
    @Bind(R.id.layout_advertisement)
    LinearLayout adLayout;
    @Bind(R.id.txt_countDownTime)
    TextView countDownTxt;

    private final int SPLASH_DISPLAY_LENGTH = 1 * 1000;
    private boolean isIndex = false;//是否需要引导
    private static String DB_PATH = "/data/data/com.poomoo.homeonline/databases/";
    private static String DB_NAME = "homeOnLine.db";
    private int index = 0;
    private boolean isFirst = true;
    private Bitmap[] bitmaps;
    private int length = 0;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Date curDate;
    private Date date1;
    private Date date2;
    private CountDownTimer countDownTimer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        versionTxt.setText(MyUtils.getVersionName(this));

        //不显示日志
//        LogUtils.isDebug = false;
//        NetWork.level = HttpLoggingInterceptor.Level.NONE;

        //统计错误日志到友盟平台
        MobclickAgent.setDebugMode(true);
        MobclickAgent.setCatchUncaughtExceptions(true);

        curDate = new Date();
        try {
            date1 = sdf.parse("2017-01-26 12:00:00");
            date2 = sdf.parse("2017-02-04 12:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        importDB();
        isIndex = (boolean) SPUtils.get(getApplicationContext(), getString(R.string.sp_isIndex), true);
        if (isIndex)
            mPresenter.getIndex();
        else
            mPresenter.checkUpdate();
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected int onSetTitle() {
        return 0;
    }

    public void toMain() {
        new Handler().postDelayed(() -> {
            if (MyUtils.isLogin(this)) {
                application.setUserId((Integer) SPUtils.get(getApplicationContext(), getString(R.string.sp_userId), -1));
                application.setNickName((String) SPUtils.get(getApplicationContext(), getString(R.string.sp_nickName), ""));
                application.setTel((String) SPUtils.get(getApplicationContext(), getString(R.string.sp_phoneNum), ""));
            }
            if (curDate.before(date1)) {
                LogUtils.d(TAG, "展示年货");
                showAd(1);
            } else if (curDate.after(date1) && curDate.before(date2)) {
                LogUtils.d(TAG, "展示新年");
                showAd(2);
            } else{
                openActivity(MainNewActivity.class);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void showAd(int flag) {
        if (2 == flag)
            adLayout.setBackgroundResource(R.drawable.bg_newyear);
        versionLayout.setVisibility(View.GONE);
        adLayout.setVisibility(View.VISIBLE);
        countDownTimer = new CountDownTimer(4 * 1000, COUNTDOWNTIBTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                LogUtils.d(TAG, "millisUntilFinished:" + millisUntilFinished);
                countDownTxt.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                LogUtils.d(TAG, "onFinish:");
                openActivity(MainNewActivity.class);
                finish();
            }
        };
        countDownTimer.start();
    }

    public void toFinish(View view) {
        LogUtils.d(TAG, "点击跳过:");
        countDownTimer.onFinish();
        countDownTimer.cancel();
    }

    @Override
    protected void setupActivityComponent(ActivityModule activityModule) {
        DaggerActivityComponent.builder()
                .activityModule(activityModule)
                .build()
                .inject(this);
    }

    public void failed(String msg) {
        toMain();
    }

    public void successful(List<RIndexBO> rIndexBOs) {
        length = rIndexBOs.size();
        if (length == 0) {
            toMain();
        } else {
            SPUtils.put(getApplicationContext(), getString(R.string.sp_isIndex), false);
            bitmaps = new Bitmap[length];
            for (RIndexBO rIndexBO : rIndexBOs)
                onDownLoad(NetConfig.ImageUrl + rIndexBO.url);
        }
    }

    public void checkUpdateFailed() {
        toMain();
    }

    public void checkUpdateSuccessful(RVersionBO rVersionBO) {
        application.setVersion(rVersionBO.version);
        toMain();
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

    /**
     * 单线程列队执行
     */
    private static ExecutorService singleExecutor = null;


    /**
     * 执行单线程列队执行
     */
    public void runOnQueue(Runnable runnable) {
        if (singleExecutor == null) {
            singleExecutor = Executors.newSingleThreadExecutor();
        }
        singleExecutor.submit(runnable);
    }

    /**
     * 启动图片下载线程
     */
    private void onDownLoad(String url) {
        DownLoadImageService service = new DownLoadImageService(this, url, new ImageDownLoadCallBack() {
            @Override
            public void onDownLoadSuccess(Bitmap bitmap) {
                // 在这里执行图片保存方法
                bitmaps[index++] = bitmap;
                LogUtils.d(TAG, "下载引导页图片成功:" + index + " :" + bitmap);
                if (index == length) {
                    runOnUiThread(() -> {
                        application.setIndex(bitmaps);
                        openActivity(IndexViewPagerActivity.class);
                        finish();
                    });

                }
            }

            @Override
            public void onDownLoadFailed() {
                LogUtils.d(TAG, "下载引导页图片失败");
                if (isFirst) {
                    isFirst = false;
                    runOnUiThread(() ->
                            toMain()
                    );

                }
            }
        });
        //启动图片下载线程
        runOnQueue(service);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            return true;
        return super.onKeyDown(keyCode, event);
    }
}
