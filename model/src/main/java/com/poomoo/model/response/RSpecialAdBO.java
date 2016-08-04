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
 * 类名 RSpecialAdBO
 * 描述 专题广告
 * 作者 李苜菲
 * 日期 2016/8/3 14:21
 */
public class RSpecialAdBO {
    public String picUrl;
    public List<List<RAdBO>> advs;
//    public List<RAdBO> specialtyAdv;//黔货出山广告实体集合
//    public List<RAdBO> wineAdv;//中外名酒广告实体集合
//    public List<RAdBO> dayAdv;//居家生活广告实体集合
//    public List<RAdBO> mbAdv;//母婴广告实体集合
//    public List<RAdBO> foodAdv; //零食驿站广告实体集合


    @Override
    public String toString() {
        return "RSpecialAdBO{" +
                "picUrl='" + picUrl + '\'' +
                ", advs=" + advs +
                '}';
    }
}
