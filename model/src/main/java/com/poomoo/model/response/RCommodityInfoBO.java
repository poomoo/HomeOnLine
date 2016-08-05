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
 * 类名 RCommodityInfoBO
 * 描述 商品详情
 * 作者 李苜菲
 * 日期 2016/8/5 16:55
 */
public class RCommodityInfoBO {
    public List<Pic> commodityPictures;
    public Commodity commodity;
    public List<Paramter> paramters;
    public List<TjCommodity> tjCommodity;
    public List<SpecialParamter> specialParamters;


    class Pic {
        public String url;

        @Override
        public String toString() {
            return "Pic{" +
                    "url='" + url + '\'' +
                    '}';
        }
    }

    class Commodity {
        public String commodityName;//商品名称
        public int categoryId;//分类主键
        public String listPic;//列表图片
        public List<detail> lowestPriceDetail;//当前的明细
        public String remark;//商品详情多媒体文本

        public class detail {
            public int id;//主键
            public double commonPrice;//市场价
            public double platformPrice;//平台价格
            public int repertory;//库存

            @Override
            public String toString() {
                return "detail{" +
                        "id=" + id +
                        ", commonPrice=" + commonPrice +
                        ", platformPrice=" + platformPrice +
                        ", repertory=" + repertory +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "Commodity{" +
                    "commodityName='" + commodityName + '\'' +
                    ", categoryId=" + categoryId +
                    ", listPic='" + listPic + '\'' +
                    ", lowestPriceDetail=" + lowestPriceDetail +
                    ", remark='" + remark + '\'' +
                    '}';
        }
    }

    /**
     * 商品描述
     */
    class Paramter {
        public String parameterName; //参数名字
        public String paramterValue; //参数值

        @Override
        public String toString() {
            return "Paramter{" +
                    "parameterName='" + parameterName + '\'' +
                    ", paramterValue='" + paramterValue + '\'' +
                    '}';
        }
    }

    /**
     * 商品规格
     */
    class SpecialParamter {
        public String parameterName;//参数名称
        public List<ParametersValues> parametersValues;//参数值实体

        public class ParametersValues {
            public int id;//参数值主键
            public String parameterValue;//参数值
            public boolean isCheck;//是否选中

            @Override
            public String toString() {
                return "ParametersValues{" +
                        "id=" + id +
                        ", parameterValue='" + parameterValue + '\'' +
                        ", isCheck=" + isCheck +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "SpecialParamter{" +
                    "parameterName='" + parameterName + '\'' +
                    ", parametersValues=" + parametersValues +
                    '}';
        }
    }

    /**
     * 推荐商品
     */
    class TjCommodity {
        public int commodityId;//商品主键
        public double platformPrice;//平台价格
        public double commonPrice;//市场价格
        public int commodityDetailId;//商品明细主键
        public String commodityName;//商品名称
        public String listPic;//图片

        @Override
        public String toString() {
            return "TjCommodity{" +
                    "commodityId=" + commodityId +
                    ", platformPrice=" + platformPrice +
                    ", commonPrice=" + commonPrice +
                    ", commodityDetailId=" + commodityDetailId +
                    ", commodityName='" + commodityName + '\'' +
                    ", listPic='" + listPic + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RCommodityInfoBO{" +
                "commodityPictures=" + commodityPictures +
                ", commodity=" + commodity +
                ", paramters=" + paramters +
                ", tjCommodity=" + tjCommodity +
                ", specialParamters=" + specialParamters +
                '}';
    }
}
