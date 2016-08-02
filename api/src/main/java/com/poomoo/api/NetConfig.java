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
    public static String url = RemoteUrl;

    public final static boolean FALSE = false;//超时
    public final static String TIME_OUT_EVENT_MSG = "连接服务器失败";

    public final static String LOGIN = "1001";//登录
    public final static String CODE = "1002";//获取验证码
    public final static String CHECK = "1003";//校验验证码
    public final static String REGISTER = "1004";//注册
    public final static String CHANGEPASSWORD = "1005";//修改密码
}
