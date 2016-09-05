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
 * 类名 QEvaluateBO
 * 描述 评价
 * 作者 李苜菲
 * 日期 2016/9/5 14:48
 */
public class QEvaluateBO extends BaseRequest {
    public String orderId;
    public int commodityId;
    public String content;
    public int descriptFit;
    public int qualitySatisfy;
    public int priceRational;
    public int orderDetailId;
    public boolean isPublic;

    public QEvaluateBO(String method, String orderId, int commodityId, String content, int descriptFit, int qualitySatisfy, int priceRational, int orderDetailId, boolean isPublic) {
        super(method);
        this.orderId = orderId;
        this.commodityId = commodityId;
        this.content = content;
        this.descriptFit = descriptFit;
        this.qualitySatisfy = qualitySatisfy;
        this.priceRational = priceRational;
        this.orderDetailId = orderDetailId;
        this.isPublic = isPublic;
    }
}
