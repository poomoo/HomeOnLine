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
 * 类名 QAddressBO
 * 描述 收货地址
 * 作者 李苜菲
 * 日期 2016/8/15 10:40
 */
public class QAddressBO extends BaseRequest {
    public Integer id;
    public int userId;
    public String consigneeName;
    public String consigneeTel;
    public String postCode;
    public int provinceId;
    public int cityId;
    public int areaId;
    public String streetName;
    public boolean isDefault;

    public QAddressBO(String method, int id, int userId, String consigneeName, String consigneeTel, String postCode, int provinceId, int cityId, int areaId, String streetName, boolean isDefault) {
        super(method);
        if (id != -1)
            this.id = id;
        this.userId = userId;
        this.consigneeName = consigneeName;
        this.consigneeTel = consigneeTel;
        this.postCode = postCode;
        this.provinceId = provinceId;
        this.cityId = cityId;
        this.areaId = areaId;
        this.streetName = streetName;
        this.isDefault = isDefault;
    }
}
