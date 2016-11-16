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

import com.poomoo.model.response.RCartCommodityBO;

import java.util.List;

/**
 * 类名 QOrdersBO
 * 描述 提交订单
 * 作者 李苜菲
 * 日期 2016/8/19 9:22
 */
public class QOrderBO extends BaseRequest {
    public Order order;
    public List<RCartCommodityBO> orderDetailsList;

    public QOrderBO(String method) {
        super(method);
    }

    public QOrderBO(String method, Order order, List<RCartCommodityBO> orderDetailsList) {
        super(method);
        this.order = order;
        this.orderDetailsList = orderDetailsList;
    }

    public class Order {
        public int userId;
        public int deliveryId;
        public int payWay;//1.在线支付 2.货到付款
        public int orderFrom;//2 APP
        public Integer vouchersId;//优惠券ID

        public Order(int userId) {
            this.userId = userId;
        }
    }

//    public class OrderDetailsList {
//        public int commodityId;//商品主键
//        public int commodityDetailId;//商品明细主键
//        public int commodityNum;//商品数量
//        public int orderType;//订单类型（0.普通商品，1.特价商品，2..抢购商品，3.新年活动商品，4.活动商品）
//        public int activityId;//新年活动主键
//        public int newActivityId;//活动主键
//        public int rushPurchaseId;//抢购主键
//    }
}
