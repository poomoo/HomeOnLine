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
 * 类名 RReFundInfoBO
 * 描述 退款详情
 * 作者 李苜菲
 * 日期 2016/8/25 11:09
 */
public class RReFundInfoBO {
    public String shopName; //商家名称
    public String finishTime;//成交时间
    public double unitPrice;//单价
    public ReturnNote returnNote;
    public String commodityName;//商品名称
    public String orderId;
    public double deliveryFee;//运费
    public double totalPrice;//总计
    public String createTime;//订单生成时间

    public class ReturnNote {
        public String id;
        public String orderId;
        public int returnStatus;
        public int returnReason; //退款原因
        public double returnMoney;//退款金额
        public String returnExplain;//退款说明
        public String submitTime; //退款提交时间
        public String checkTime;//退款处理时间
        public String payCashTime; //退款时间
        public String finishTime;//完成时间
        public String checkResult;//审核结果
        public String cancleTime;//取消时间
        public int returnType;//退货类型
        public int returnNum;//退货数量
        public int goodsState;//货物状态
        public int commodityId;
        public int commodityDetailId;
        public String returnProof;
        public int orderDetailId;
    }
}
