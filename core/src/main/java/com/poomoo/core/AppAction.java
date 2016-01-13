package com.poomoo.core;

/**
 * 接收app层的各种Action
 * 作者: 李苜菲
 * 日期: 2015/11/11 11:13.
 */
public interface AppAction {
    /**
     * 登陆
     *
     * @param phoneNum 手机号
     * @param passWord 密码
     * @param listener 回调监听器
     */
    void logIn(String phoneNum, String passWord, ActionCallbackListener listener);

    /**
     * 获取验证码
     *
     * @param phoneNum
     * @param listener
     */
    void getCode(String phoneNum, boolean flag, ActionCallbackListener listener);

    /**
     * 校验验证码
     *
     * @param tel
     * @param code
     * @param listener
     */
    void checkCode(String tel, String code, ActionCallbackListener listener);

    /**
     * 注册
     *
     * @param phoneNum
     * @param passWord
     */
    void register(String phoneNum, String passWord, ActionCallbackListener listener);
}
