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
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.model.response;

/**
 * 类名 ROrderBO
 * 描述 订单
 * 作者 李苜菲
 * 日期 2016/8/19 10:30
 */
public class ROrderBO {
    public static final int ORDER_ALL = 0;
    public static final int ORDER_PAY = 1;//代付款
    public static final int ORDER_DELIVER = 2;//待发货
    public static final int ORDER_PICKING = 3;//配货中
    public static final int ORDER_RECEIPT = 4;//待收货
    public static final int ORDER_EVALUATE = 5;//待评价
    public static final int ORDER_FINISH = 6;//已完成
    public static final int ORDER_USERCANCEL = 7;
    public static final int ORDER_SYSYCANCEL = 8;

    public String orderId;
    public String sign;
}
