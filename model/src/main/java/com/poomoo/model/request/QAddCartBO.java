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
package com.poomoo.model.request;

/**
 * 类名 QAddCartBO
 * 描述 添加购物车
 * 作者 李苜菲
 * 日期 2016/8/10 9:32
 */
public class QAddCartBO extends BaseRequest {
    public int userId;//用户ID
    public Integer activityId;//新年活动主键
    public Integer newActivityId;//活动主键
    public int commodityDetailId;//商品明细主键
    public String listPic;//商品图片地址
    public int commodityNum;//商品数量
    public Integer rushPurchaseId;//抢购主键
    public int commodityType;//商品类型
    public int commodityId;//商品主键
    public String commodityName;////商品名称

    public QAddCartBO(String method, int userId, int activityId, int newActivityId, int commodityDetailId, String listPic, int commodityNum, int rushPurchaseId, int commodityType, int commodityId, String commodityName) {
        super(method);
        this.userId = userId;
        if (activityId != -1)
            this.activityId = activityId;
        if (newActivityId != -1)
            this.newActivityId = newActivityId;
        if (commodityDetailId != -1)
            this.commodityDetailId = commodityDetailId;
        this.listPic = listPic;
        this.commodityNum = commodityNum;
        if (rushPurchaseId != -1)
            this.rushPurchaseId = rushPurchaseId;
        this.commodityType = commodityType;
        this.commodityId = commodityId;
        this.commodityName = commodityName;
    }
}
