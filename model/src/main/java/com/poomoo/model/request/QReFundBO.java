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
 * 类名 QReFundBO
 * 描述 退款
 * 作者 李苜菲
 * 日期 2016/8/26 10:19
 */
public class QReFundBO extends BaseRequest {
    public String commodityId;
    public String commodityDetailId;
    public String orderId;
    public String orderDetailId;
    public int userId;
    public int returnReason;
    public String returnExplain;
    public String returnProof;
    public int returnType;
    public int returnNum;
    public int goodsState;
    public double returnMoney;

    public QReFundBO(String method, String commodityId, String commodityDetailId, String orderId, String orderDetailId, int userId, int returnReason, String returnExplain, String returnProof, int returnType, int returnNum, int goodsState, double returnMoney) {
        super(method);
        this.commodityId = commodityId;
        this.commodityDetailId = commodityDetailId;
        this.orderId = orderId;
        this.orderDetailId = orderDetailId;
        this.userId = userId;
        this.returnReason = returnReason;
        this.returnExplain = returnExplain;
        this.returnProof = returnProof;
        this.returnType = returnType;
        this.returnNum = returnNum;
        this.goodsState = goodsState;
        this.returnMoney = returnMoney;
    }
}
