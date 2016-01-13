package com.poomoo.api;

import com.poomoo.model.ResponseBO;
import com.poomoo.model.UserBO;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Api实现类
 * 作者: 李苜菲
 * 日期: 2015/11/11 11:06.
 */
public class ApiImpl implements Api {

    private HttpEngine httpEngine;

    public ApiImpl() {
        httpEngine = HttpEngine.getInstance();
    }

    @Override
    public ResponseBO login(String phoneNum, String passWord) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("method", Config.LOGIN);
        paramMap.put("account", phoneNum);
        paramMap.put("password", passWord);
        try {
            return httpEngine.postHandle(paramMap, UserBO.class);
        } catch (IOException e) {
            return new ResponseBO(Config.FALSE, Config.TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public ResponseBO getCode(String phoneNum, boolean flag) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("method", Config.CODE);
        paramMap.put("phoneNum", phoneNum);
        paramMap.put("flag", flag + "");

        try {
            return httpEngine.postHandle(paramMap, null);
        } catch (IOException e) {
            return new ResponseBO(Config.FALSE, Config.TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public ResponseBO checkCode(String tel, String code) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("method", Config.CHECK);
        paramMap.put("phoneNum", tel);
        paramMap.put("code", code);

        try {
            return httpEngine.postHandle(paramMap, null);
        } catch (IOException e) {
            return new ResponseBO(Config.FALSE, Config.TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public ResponseBO register(String phoneNum, String passWord) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("method", Config.REGISTER);
        paramMap.put("phoneNum", phoneNum);
        paramMap.put("password", passWord);

        try {
            return httpEngine.postHandle(paramMap, null);
        } catch (IOException e) {
            return new ResponseBO(Config.FALSE, Config.TIME_OUT_EVENT_MSG);
        }
    }

}
