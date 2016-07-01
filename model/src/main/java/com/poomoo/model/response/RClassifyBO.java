/**
 * Copyright (c) 2016. 李苜菲 Inc. All rights reserved.
 */
package com.poomoo.model.response;

import java.util.List;

/**
 * 作者: 李苜菲
 * 日期: 2016/6/28 15:58.
 */
public class RClassifyBO {
    public String subTitle;
    public List<RSubClassifyBO> rSubClassifyBOs;

    @Override
    public String toString() {
        return "RClassifyBO{" +
                "subTitle='" + subTitle + '\'' +
                ", rSubClassifyBOs=" + rSubClassifyBOs +
                '}';
    }
}
