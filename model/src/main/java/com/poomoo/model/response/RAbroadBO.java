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
 * 类名 RAbroadBO
 * 描述 跨境
 * 作者 李苜菲
 * 日期 2016/11/8 14:18
 */
public class RAbroadBO {
//    public List<RAbroadClassifyBO> categorys;
//    public List<List<RAbroadCommodityBO>> commodityList;
//    public List<List<RAdBO>> advList;
//    public List<RAdBO> topAdvList;

    public List<RAdBO> topAdvList;
    public List<RClassifyBO> categorys;
    public List<RAdBO> earthAdv;
    public List<RCountryBO> countrys;
    public List<adv> advList;

    public class adv {
        public List<RAdBO> advs;
        public String categoryName;
        public String categoryId;
        public String pcPic;
    }
}
