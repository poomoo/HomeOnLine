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


import java.io.Serializable;
import java.util.ArrayList;

/**
 * 类名 ROrderListBO
 * 描述 订单列表
 * 作者 李苜菲
 * 日期 2016/8/19 9:45
 */
public class ROrderListBO implements Serializable {
    public ArrayList<OrderDetails> orderDetails;
    public Order order;

    public class OrderDetails implements Serializable {
        public int id;//订单明细主键
        public String orderId;//订单编号
        public int commodityDetailsId;//商品明细主键
        public int commodityId;//商品主键
        public String commodityName;//商品名称
        public int commodityNum;//商品数量
        public double unitPrice;//单价
        public int orderType;//订单类型
        public int rushPurchaseId;//抢购Id
        public int activityId;//新年活动Id
        public int newActivityId;//活动Id
        public String listPic;
        public int state;
        public boolean isReturnGood;//是否有退货
        public String returnGoodId;//退货主键
        /*买赠*/
        public int activityRule;//买赠规则
        public String present;//买赠信息
        public int presentNum;//赠品数量

        @Override
        public String toString() {
            return "OrderDetails{" +
                    "id=" + id +
                    ", orderId='" + orderId + '\'' +
                    ", commodityDetailsId=" + commodityDetailsId +
                    ", commodityId=" + commodityId +
                    ", commodityName='" + commodityName + '\'' +
                    ", commodityNum=" + commodityNum +
                    ", unitPrice=" + unitPrice +
                    ", orderType=" + orderType +
                    ", rushPurchaseId=" + rushPurchaseId +
                    ", activityId=" + activityId +
                    ", newActivityId=" + newActivityId +
                    ", listPic='" + listPic + '\'' +
                    ", state=" + state +
                    '}';
        }
    }

    public class Order implements Serializable {
        public String orderId;
        public double totalPrise;
        public int deliveryId;
        public double deliveryFee;
        public double sumMoney;
        public int state;
        public int payWay;//1.在线支付 2.货到付款
        public String createTime;
        public String deliveryAddress;

        @Override
        public String toString() {
            return "Order{" +
                    "orderId='" + orderId + '\'' +
                    ", totalPrise=" + totalPrise +
                    ", deliveryId=" + deliveryId +
                    ", deliveryFee=" + deliveryFee +
                    ", sumMoney=" + sumMoney +
                    ", state=" + state +
                    ", payWay=" + payWay +
                    ", createTime='" + createTime + '\'' +
                    ", deliveryAddress='" + deliveryAddress + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ROrderListBO{" +
                "orderDetails=" + orderDetails +
                ", order=" + order +
                '}';
    }
}
