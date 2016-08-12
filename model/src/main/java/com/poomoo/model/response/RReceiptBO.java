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

import java.io.Serializable;

/**
 * 类名 RReceiptBO
 * 描述 收货信息
 * 作者 李苜菲
 * 日期 2016/8/12 14:10
 */
public class RReceiptBO implements Serializable {
    public int id;
    public String consigneeName;
    public String consigneeTel;
    public String pca;
    public String provinceName;
    public String cityName;
    public String areaName;
    public String streetName;
    public int provinceId;
    public int cityId;
    public int areaId;

    @Override
    public String toString() {
        return "RReceiptBO{" +
                "id=" + id +
                ", consigneeName='" + consigneeName + '\'' +
                ", consigneeTel='" + consigneeTel + '\'' +
                ", pca='" + pca + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", areaName='" + areaName + '\'' +
                ", streetName='" + streetName + '\'' +
                ", provinceId=" + provinceId +
                ", cityId=" + cityId +
                ", areaId=" + areaId +
                '}';
    }
}
