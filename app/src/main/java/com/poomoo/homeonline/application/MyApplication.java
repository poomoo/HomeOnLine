package com.poomoo.homeonline.application;


import android.graphics.Bitmap;

import com.poomoo.homeonline.crashhandler.CrashHandler;

import org.litepal.LitePalApplication;

/**
 * 类名 MyApplication
 * 描述 自定义Application
 * 作者 李苜菲
 * 日期 2016/7/19 11:28
 */
public class MyApplication extends LitePalApplication {
    // 用户信息
    private Integer userId;
    private String tel = "";//用户手机号码
    private String nickName = "";//用户昵称
    private int cartNum;
    private int version;
    private Bitmap[] index;

    @Override
    public void onCreate() {
        super.onCreate();

        CrashHandler crashHandler = CrashHandler.getInstance();
        // 注册crashHandler
        crashHandler.init(getApplicationContext());
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getCartNum() {
        return cartNum;
    }

    public void setCartNum(int cartNum) {
        this.cartNum = cartNum;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Bitmap[] getIndex() {
        return index;
    }

    public void setIndex(Bitmap[] index) {
        this.index = index;
    }
}
