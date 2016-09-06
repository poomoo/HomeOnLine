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
    public final static String suffixRemote = "jyzx/app/call.json";
    public final static String suffixLocal = "app/call.json";

    public final static String suffix = suffixRemote;
    public static String url = RemoteUrl;

    public static final String grabUrl = url + "jyzx/app/rush.html";

    public final static String LOGIN = "1001";//登录
    public final static String CODE = "1002";//获取验证码
    public final static String CHECK = "1003";//校验验证码
    public final static String REGISTER = "1004";//注册

    public final static String CHANGEPASSWORD = "1005";//修改密码
    public final static String USERINFO = "1005_01";//用户基本信息
    public final static String FEEDBACK = "1005_02";//意见反馈
    public final static String UPDATEINFO = "1005_03";//修改个人信息

    public final static String SLIDE = "1006_01";//首页滚动广告
    public final static String SPECIAL = "1006_02";//首页专题广告
    public final static String TYPE = "1006_03";//首页分类
    public final static String GRAB = "1006_04";//首页抢购
    public final static String GUESS = "1006_05";//首页猜你喜欢
    public final static String HOT = "1006_06";//首页热门推荐

    public final static String CLASSIFY = "1007_01";//商品分类

    public final static String CARTINFO = "1008_01";//购物车信息
    public final static String CARTNUM = "1008_02";//购物车数量
    public final static String ADDCART = "1008_03";//加入购物车
    public final static String DELETECART = "1008_04";//删除购物车商品
    public final static String CHANGCOUNT = "1008_05";//购物车某商品数量修改

    public final static String COMMODITY = "1009_01";//商品详情
    public final static String SPECIFICATION = "1009_02";//商品规格详情
    public final static String SEARCH = "1009_03";//商品搜索
    public final static String CLASSINFO = "1009_04";//导航商品列表
    public final static String CLASSCOMMODITYLIST = "1009_05";//三级分类下的商品
    public final static String CLASSINFOLIST = "1009_06";//导航商品分类

    public final static String PUTORDER = "1010_01";//提交订单
    public final static String ORDERLIST = "1010_02";//订单列表
    public static final String CANCELORDER = "1010_03";//取消订单
    public static final String DELETEORDER = "1010_04";//删除订单
    public static final String CONFIRM = "1010_05";//确认收货
    public static final String EVALUATE = "1010_07";//评价
    public final static String TRANSFERPRICE = "1010_08";//计算运费
    public static final String SIGN = "1010_09";//支付宝签名

    public final static String NEWADDRESS = "1011_01";//收货地址列表
    public final static String GETADDRESSLIST = "1011_02";//收货地址列表
    public final static String UPDATEADDRESS = "1011_03";//更新收货地址
    public final static String DELETEADDRESS = "1011_04";//删除收货地址
    public final static String GETADDRESS = "1011_05";//删除收货地址
    public final static String GETZONEINFO = "1011_06";//区域信息
    public final static String DEFAULTADDRESS = "1011_07";//默认收货地址

    public static final String ADDHISTORY = "1012_01";//增加浏览记录
    public static final String GETHISTORY = "1012_02";//获取浏览记录
    public static final String DELETEHISTORY = "1012_03";//删除浏览记录

    public static final String COLLECT = "1013_01";//收藏
    public static final String COLLECTIONLIST = "1013_02";//收藏记录
    public static final String CANCELCOLLECTION = "1013_03";//取消收藏
    public static final String ISCOLLECT = "1013_04";//该商品是否收藏过

    public static final String REFUND = "1014_01";//申请退款
    public static final String CHANGEREFUND = "1014_02";//修改退款
    public static final String REFUNDINFO = "1014_03";//退款详情

    public static final String UPDATE="1015_01";//检查版本

    public static final String TICKETS = "1016_01";//优惠券
    public static final String TICKETSTATEMENT = "1016_02";//用券须知

}
