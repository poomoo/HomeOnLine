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
 * 类名 ROnSaleBO
 * 描述 优惠券专区
 * 作者 李苜菲
 * 日期 2016/11/1 10:08
 */
public class ROnSaleBO {
    public List<List<RListCommodityBO>> commoditys;
    //    public List<RListCommodityBO> tenList;
//    public List<RListCommodityBO> fifteenList;
//    public List<RListCommodityBO> twentyList;
    public List<RAdBO> advlist;
    public String activityExplain;
    public List<Pic> yhqs;

    public class Pic {
        public int awardAmt;
        public String voucherPic;

        @Override
        public String toString() {
            return "Pic{" +
                    "awardAmt=" + awardAmt +
                    ", voucherPic='" + voucherPic + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ROnSaleBO{" +
                "commoditys=" + commoditys +
                '}';
    }
}
