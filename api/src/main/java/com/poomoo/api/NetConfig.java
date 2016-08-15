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
    public static String url = LocalUrl;

    public final static boolean FALSE = false;//超时
    public final static String TIME_OUT_EVENT_MSG = "连接服务器失败";

    public final static String LOGIN = "1001";//登录
    public final static String CODE = "1002";//获取验证码
    public final static String CHECK = "1003";//校验验证码
    public final static String REGISTER = "1004";//注册

    public final static String CHANGEPASSWORD = "1005";//修改密码
    public final static String USERINFO = "1005_01";//用户基本信息
    public final static String FEEDBACK = "1005_02";//意见反馈

    public final static String SLIDE = "1006_01";//首页滚动广告
    public final static String SPECIAL = "1006_02";//首页专题广告
    public final static String TYPE = "1006_03";//首页分类
    public final static String GRAB = "1006_04";//首页抢购
    public final static String GUESS = "1006_05";//首页猜你喜欢
    public final static String HOT = "1006_06";//首页热门推荐

    public final static String CLASSIFY = "1007_01";//商品分类

    public final static String CARTINFO = "1008_01";//购物车信息
    public final static String ADDCART = "1008_03";//加入购物车
    public final static String DELETECART = "1008_04";//删除购物车商品
    public final static String CHANGCOUNT = "1008_05";//购物车某商品数量修改

    public final static String COMMODITY = "1009_01";//商品详情
    public final static String SPECIFICATION = "1009_02";//商品规格详情

    public final static String NEWADDRESS = "1011_01";//收货地址列表
    public final static String GETADDRESSLIST = "1011_02";//收货地址列表
    public final static String UPDATEADDRESS = "1011_03";//更新收货地址
    public final static String DELETEADDRESS = "1011_04";//删除收货地址
    public final static String GETZONEINFO = "1011_06";//区域信息

    public static final String ADDHISTORY = "1012_01";//增加浏览记录
    public static final String GETHISTORY = "1012_02";//获取浏览记录
    public static final String DELETEHISTORY = "1012_03";//删除浏览记录

    public static final String COLLECTIONLIST = "1013_02";//收藏记录
    public static final String CANCELCOLLECTION = "1013_03";//取消收藏
}
