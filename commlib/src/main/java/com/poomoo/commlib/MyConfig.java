/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.commlib;

/**
 * 作者: 李苜菲
 * 日期: 2016/6/28 14:22.
 */
public class MyConfig {
    public static final String[] classify = {"贵州味道", "贵酒贵茶", "黔匠艺品", "食品酒水", "全球优品", "母婴用品", "美妆个护", "家居生活"};
    public static final String[] gzwd = {"食尚黔味", "黔珍山宝", "黔品副食", "黔品辣椒", "黔品粮油"};
    public static final String[] gzwd_1 = {"健康冲饮", "高山水饮", "特产零食", "黔品风味"};
    public static final String[] gzwd_2 = {"贵州三宝", "山珍野品"};
    public static final String[] gzwd_3 = {"黔品腌腊", "黔味佐餐小菜", "黔品调味料"};
    public static final String[] gzwd_4 = {"黔品特色辣椒", "黔品辣椒面", "黔品油辣椒"};
    public static final String[] gzwd_5 = {"黔品米面杂粮", "黔品食用油"};
    public static final String gzwdjson = "[{\"rSubClassifyBOs\":[{\"name\":\"健康冲饮\"},{\"name\":\"高山水饮\"},{\"name\":\"特产零食\"},{\"name\":\"黔品风味\"}],\"subTitle\":\"食尚黔品\"},{\"rSubClassifyBOs\":[{\"name\":\"贵州三宝\"},{\"name\":\"山珍野品\"}],\"subTitle\":\"黔珍山宝\"},{\"rSubClassifyBOs\":[{\"name\":\"黔品腌腊\"},{\"name\":\"黔品佐餐小菜\"},{\"name\":\"黔品调味料\"}],\"subTitle\":\"黔品副食\"},{\"rSubClassifyBOs\":[{\"name\":\"黔品特色辣椒\"},{\"name\":\"黔品辣椒面\"},{\"name\":\"黔品油辣椒\"}],\"subTitle\":\"黔品辣椒\"},{\"rSubClassifyBOs\":[{\"name\":\"黔品米面杂粮\"},{\"name\":\"黔品食用油\"}],\"subTitle\":\"黔品粮油\"}]";
    public static final String gjgcJson = "[{\"rSubClassifyBOs\":[{\"name\":\"黔品白酒\"},{\"name\":\"洋酒\"},{\"name\":\"其他黔品名酒\"},{\"name\":\"黔品果酒\"},{\"name\":\"黔品收藏酒\"},{\"name\":\"啤酒\"}],\"subTitle\":\"醉美琼浆\"},{\"rSubClassifyBOs\":[{\"name\":\"黔品红茶\"},{\"name\":\"黔品绿茶\"},{\"name\":\"黔品苦荞茶\"},{\"name\":\"其他黔品茗茶\"}],\"subTitle\":\"黔茶飘香\"}]";
    public static final int PAGE_SIZE = 10;
    public static final String EXTRA_IMAGE_LIST = "imageList";
    public static final int MINCOUNT = 1;//购买数量下限
    public static final int MAXCOUNT = 999;//购买数量上限

    //订单退货状态
    /**
     * 退货状态：提交申请
     */
    public static final int GOODS_RETURN_NOTE_STATUS_SUBMIT = 1;
    /**
     * 退货状态：审核中
     */
    public static final int GOODS_RETURN_NOTE_STATUS_CHECK = 2;
    /**
     * 退货状态：审核未通过
     */
    public static final int GOODS_RETURN_NOTE_STATUS_CHECK_NO = 3;
    /**
     * 退货状态：审核通过
     */
    public static final int GOODS_RETURN_NOTE_STATUS_CHECK_YES = 4;
    /**
     * 退货状态：完成
     */
    public static final int GOODS_RETURN_NOTE_STATUS_FINISH = 5;
    /**
     * 退货状态：退款到账
     */
    public static final int GOODS_RETURN_NOTE_STATUS_PAY_CASH = 6;
    /**
     * 退货状态：用户取消
     */
    public static final int GOODS_RETUEN_NOTE_STATUS_USER_CANCLE = 7;

    //退款原因
    public static final String[] reason = {"", "商品描述不符", "卖家发错货", "少件/漏发", "7天无理由退换货", "买错，不需要", "快递无跟踪记录", "快递一直未送到", "空包裹，少货", "未按规定时间发货", "其他"};
}
