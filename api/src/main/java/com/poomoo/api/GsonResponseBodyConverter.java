package com.poomoo.api;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.poomoo.model.ResponseBO;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Converter;

class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        final String response = value.string();
        ResponseBO responseBO = gson.fromJson(response, ResponseBO.class);
        if (responseBO.result) {
            if (type.equals(ResponseBO.class))
                return (T) responseBO;
            //result==1表示成功返回，继续用本来的Model类解析
            String jsonData = responseBO.content.toString();
            if (!TextUtils.isEmpty(jsonData)) {
                if (jsonData.contains("records")) {
                    responseBO.records = new ArrayList();
                    JSONObject jsonObject;
                    try {
                        jsonObject = new JSONObject(jsonData);
                        String records = jsonObject.getString("records");
                        return gson.fromJson(records, type);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else
                    return gson.fromJson(jsonData, type);
            }
        } else
            throw new ApiException(ApiException.SERVERERR, responseBO.msg);
        return null;
    }
}