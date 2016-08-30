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
 * 类名 QCommodityInfoBO
 * 描述 获取商品详情请求
 * 作者 李苜菲
 * 日期 2016/8/5 17:22
 */
public class QCommodityInfoBO extends BaseRequest {
    public int commodityId;
    public Integer commodityDetailId;
    public int commodityType;
    public Integer matchId;

//    public QCommodityInfoBO(String method, int commodityId, Integer commodityDetailId) {
//        super(method);
//        this.commodityId = commodityId;
//        if (commodityDetailId != -1)
//            this.commodityDetailId = commodityDetailId;
//    }

    public QCommodityInfoBO(String method, int commodityId, Integer commodityDetailId, int commodityType, int matchId) {
        super(method);
        this.commodityId = commodityId;
        if (commodityDetailId != -1)
            this.commodityDetailId = commodityDetailId;
        this.commodityType = commodityType;
        if (matchId != -1)
            this.matchId = matchId;
    }
}
