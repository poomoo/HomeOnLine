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
package com.poomoo.model;

/**
 * 类名 PayBO
 * 描述 支付宝返回
 * 作者 李苜菲
 * 日期 2016/8/23 11:50
 */
public class PayBO {
    public Content alipay_trade_app_pay_response;
    public String sign;

    public class Content {
        public String code;
        public String Success;
        public String total_amount;
        public String trade_no;
        public String seller_id;
        public String out_trade_no;
        public String app_id;

        @Override
        public String toString() {
            return "Content{" +
                    "code='" + code + '\'' +
                    ", Success='" + Success + '\'' +
                    ", total_amount='" + total_amount + '\'' +
                    ", trade_no='" + trade_no + '\'' +
                    ", seller_id='" + seller_id + '\'' +
                    ", out_trade_no='" + out_trade_no + '\'' +
                    ", app_id='" + app_id + '\'' +
                    '}';
        }
    }
}
