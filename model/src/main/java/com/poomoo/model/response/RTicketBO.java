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

import java.util.List;

/**
 * 类名 RTicketBO
 * 描述 优惠券
 * 作者 李苜菲
 * 日期 2016/9/6 13:55
 */
public class RTicketBO {
    public List<Ticket> voucheredlist;//已使用的抵用卷
    public List<Ticket> voucherlist;//未使用的抵用卷
    public List<Ticket> expiredCouponslist;//已使用的抵用卷

    public class Ticket {
        public int id;
        public String voucherPic;//未使用显示
        public String voucheredPic;//已使用显示
        public String expiredCouponsPic;//已失效显示
        public String updateTime;
        public String periodValidity;
        public String status;//
    }
}
