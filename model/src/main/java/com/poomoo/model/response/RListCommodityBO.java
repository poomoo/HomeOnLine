/**
 * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG            #
 * #                                                   #
 * Copyright (c) 2016. 跑马科技 Inc. All rights reserved.
 */
package com.poomoo.model.response;

/**
 * 类名 RListCommodityBO
 * 描述 列表里的商品
 * 作者 李苜菲
 * 日期 2016/8/12 10:11
 */
public class RListCommodityBO {
    public int id; //主键
    public double commodityPrice;//商品价格
    public double platformPrice; //平台价格
    public int commodityDetailId; //明细主键
    public String commodityName;//商品名称
    public double commonPrice;//市场价格
    public String listPic;//商品显示图片
    public int commodityId;//商品主键
    public int commodityType;//1.普通商品，2抢购商品，3.新年活动商品，4活动商品，5.特价商品
    public Integer rushPurchaseId;
    public int activityId;

    public String price;

    @Override
    public String toString() {
        return "RListCommodityBO{" +
                "commodityName='" + commodityName + '\'' +
                ", commodityType=" + commodityType +
                '}';
    }
}
