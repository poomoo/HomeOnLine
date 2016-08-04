/**
 * Copyright (c) 2015. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.api;

/**
 * 作者: 李苜菲
 * 日期: 2015/11/27 11:22.
 */
public class NetConfig {
    public static String RemoteUrl = "http://115.28.95.198/";
    public static String LocalUrl = "http://192.168.1.107:8080/";
    public static String ImageUrl = "http://img.jiayou9.com/jyzx";
    public static String url = RemoteUrl;

    public final static boolean FALSE = false;//超时
    public final static String TIME_OUT_EVENT_MSG = "连接服务器失败";

    public final static String LOGIN = "1001";//登录
    public final static String CODE = "1002";//获取验证码
    public final static String CHECK = "1003";//校验验证码
    public final static String REGISTER = "1004";//注册
    public final static String CHANGEPASSWORD = "1005";//修改密码

    public final static String SLIDE = "1006_01";//首页滚动广告
    public final static String SPECIAL = "1006_02";//首页专题广告
    public final static String TYPE = "1006_03";//首页分类
    public final static String GRAB = "1006_04";//首页抢购
    public final static String GUESS = "1006_05";//首页猜你喜欢
    public final static String HOT = "1006_06";//首页热门推荐

    public final static String CLASSIFY = "1007_01";//商品分类
}
