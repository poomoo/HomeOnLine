package com.poomoo.api;

import com.poomoo.model.ResponseBO;

/**
 * Api接口
 * <p/>
 * 作者: 李苜菲
 * 日期: 2015/11/11 11:06.
 */
public interface Api {

    ResponseBO login(String phoneNum, String passWord);

    ResponseBO getCode(String phoneNum,boolean flag);

    ResponseBO checkCode(String tel, String code);

    ResponseBO register(String phoneNum, String passWord);

}
