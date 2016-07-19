package com.poomoo.homeonline.application;

import com.poomoo.core.AppAction;
import com.poomoo.core.AppActionImpl;

import org.litepal.LitePalApplication;

/**
 * 类名 MyApplication
 * 描述 自定义Application
 * 作者 李苜菲
 * 日期 2016/7/19 11:28
 */
public class MyApplication extends LitePalApplication {
    private AppAction appAction;
    // 用户信息
    private String userId = "";
    private String tel = "";//--用户手机号码

    @Override
    public void onCreate() {
        super.onCreate();
        appAction = new AppActionImpl(this);
//
//        initImageLoader();
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        // 注册crashHandler
//        crashHandler.init(getApplicationContext());
    }

//    private void initImageLoader() {
//        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder() //
//                .showImageForEmptyUri(R.mipmap.ic_launcher) //
//                .showImageOnFail(R.mipmap.ic_launcher) //
//                .cacheInMemory(true) //
//                .cacheOnDisk(true) //
//                .bitmapConfig(Bitmap.Config.RGB_565)// 设置最低配置
//                .build();//
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration//
//                .Builder(getApplicationContext())//
//                .defaultDisplayImageOptions(defaultOptions)//
//                .writeDebugLogs()//
//                .build();//
//        ImageLoader.getInstance().init(config);
//    }

    public AppAction getAppAction() {
        return appAction;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

}
