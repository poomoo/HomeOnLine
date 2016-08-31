package com.poomoo.homeonline.application;


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
    private int cartNum = -1;

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
}
