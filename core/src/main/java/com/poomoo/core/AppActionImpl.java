package com.poomoo.core;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.poomoo.api.Api;
import com.poomoo.api.ApiImpl;
import com.poomoo.model.ResponseBO;
import com.poomoo.model.UserBO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * AppAction接口的实现类
 * 作者: 李苜菲
 * 日期: 2015/11/11 11:14.
 */
public class AppActionImpl implements AppAction {
    private Context context;
    private Api api;

    public AppActionImpl(Context context) {
        this.context = context;
        this.api = new ApiImpl();
    }

    @Override
    public void logIn(final String phoneNum, final String passWord, final ActionCallbackListener listener) {
        // 参数检查
        if (TextUtils.isEmpty(phoneNum)) {
            if (listener != null) {
                listener.onFailure(ErrorEvent.FALSE, "手机号为空");
            }
            return;
        }
        Pattern pattern = Pattern.compile("1\\d{10}");
        Matcher matcher = pattern.matcher(phoneNum);
        if (!matcher.matches()) {
            if (listener != null) {
                listener.onFailure(ErrorEvent.FALSE, "手机号不正确");
            }
            return;
        }

        if (TextUtils.isEmpty(passWord)) {
            if (listener != null) {
                listener.onFailure(ErrorEvent.FALSE, "密码为空");
            }
            return;
        }
        // 请求Api
        new AsyncTask<Void, Void, ResponseBO<UserBO>>() {
            @Override
            protected ResponseBO<UserBO> doInBackground(Void... params) {
                return api.login(phoneNum, passWord);
            }

            @Override
            protected void onPostExecute(ResponseBO<UserBO> response) {
                if (listener != null && response != null) {
                    if (response.isSuccess()) {
                        listener.onSuccess(response);
                    } else {
                        listener.onFailure(response.isSuccess(), response.getMsg());
                    }
                }
            }
        }.execute();

    }

    @Override
    public void getCode(final String phoneNum, final boolean flag, final ActionCallbackListener listener) {
        // 参数检查
        if (TextUtils.isEmpty(phoneNum)) {
            if (listener != null) {
                listener.onFailure(ErrorEvent.FALSE, "手机号为空");
            }
            return;
        }
        Pattern pattern = Pattern.compile("1\\d{10}");
        Matcher matcher = pattern.matcher(phoneNum);
        if (!matcher.matches()) {
            if (listener != null) {
                listener.onFailure(ErrorEvent.FALSE, "手机号不正确");
            }
            return;
        }
        // 请求Api
        new AsyncTask<Void, Void, ResponseBO<Void>>() {
            @Override
            protected ResponseBO<Void> doInBackground(Void... voids) {
                return api.getCode(phoneNum, flag);
            }

            @Override
            protected void onPostExecute(ResponseBO<Void> response) {
                if (listener != null && response != null) {
                    if (response.isSuccess()) {
                        listener.onSuccess(response);
                    } else {
                        listener.onFailure(response.isResult(), response.getMsg());
                    }
                }
            }
        }.execute();
    }

    @Override
    public void checkCode(final String tel, final String code, final ActionCallbackListener listener) {
        // 参数检查
        if (TextUtils.isEmpty(code)) {
            if (listener != null) {
                listener.onFailure(ErrorEvent.FALSE, "验证码为空");
            }
            return;
        }

        // 请求Api
        new AsyncTask<Void, Void, ResponseBO<Void>>() {
            @Override
            protected ResponseBO<Void> doInBackground(Void... voids) {
                return api.checkCode(tel, code);
            }

            @Override
            protected void onPostExecute(ResponseBO<Void> response) {
                if (listener != null && response != null) {
                    if (response.isSuccess()) {
                        listener.onSuccess(response);
                    } else {
                        listener.onFailure(response.isSuccess(), response.getMsg());
                    }
                }
            }
        }.execute();
    }

    @Override
    public void register(final String phoneNum, final String passWord, final ActionCallbackListener listener) {
        // 参数检查
        if (TextUtils.isEmpty(phoneNum)) {
            if (listener != null) {
                listener.onFailure(ErrorEvent.FALSE, "手机号为空");
            }
            return;
        }

        Pattern pattern = Pattern.compile("1\\d{10}");
        Matcher matcher = pattern.matcher(phoneNum);
        if (!matcher.matches()) {
            if (listener != null) {
                listener.onFailure(ErrorEvent.FALSE, "手机号不正确");
            }
            return;
        }

        if (TextUtils.isEmpty(passWord)) {
            if (listener != null) {
                listener.onFailure(ErrorEvent.FALSE, "密码为空");
            }
            return;
        }
        if (passWord.length() < 6) {
            if (listener != null) {
                listener.onFailure(ErrorEvent.FALSE, "密码不能少于6位");
            }
            return;
        }

        // 请求Api
        new AsyncTask<Void, Void, ResponseBO<Void>>() {
            @Override
            protected ResponseBO<Void> doInBackground(Void... voids) {
                return api.register(phoneNum, passWord);
            }

            @Override
            protected void onPostExecute(ResponseBO<Void> response) {
                if (listener != null && response != null) {
                    if (response.isSuccess()) {
                        listener.onSuccess(null);
                    } else {
                        listener.onFailure(response.isSuccess(), response.getMsg());
                    }
                }
            }
        }.execute();
    }

}
