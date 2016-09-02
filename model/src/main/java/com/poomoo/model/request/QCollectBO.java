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
 * 类名 QCollectBO
 * 描述 添加收藏
 * 作者 李苜菲
 * 日期 2016/8/19 14:00
 */
public class QCollectBO extends BaseRequest {
    public int userId;
    public int commodityId;
    public int commodityDetailId;
    public int commodityType;
    public int rushPurchaseId;

    public QCollectBO(String method, int userId, int commodityId, int commodityDetailId, int commodityType, int rushPurchaseId) {
        super(method);
        this.userId = userId;
        this.commodityId = commodityId;
        this.commodityDetailId = commodityDetailId;
        this.commodityType = commodityType;
        this.rushPurchaseId = rushPurchaseId;
    }
}
