/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.model.response;

import java.util.List;

/**
 * 类名 RAbroadClassifyBO
 * 描述 跨境分类
 * 作者 李苜菲
 * 日期 2016/8/4 16:07
 */
public class RAbroadClassifyBO {
    public int id;
    public String categoryName;
    public String pcPic;
    public String pcReplacePic;
    public List<RAbroadCommodityBO> commodityList;
}
