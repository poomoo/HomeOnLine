package com.poomoo.api;

import android.util.Log;

import com.google.gson.Gson;
import com.poomoo.model.ResponseBO;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpUtil {
    public static Thread thread = null;

    /**
     * @return void
     * @throws @date 2015-7-24下午3:07:36
     * @Title: SendPostRequest
     * @Description: TODO
     * @author 李苜菲
     */
    public static void SendPostRequest(final Map<String, String> map, final String address, final HttpCallbackListener listener) {
        thread = new Thread(new Runnable() {
            public void run() {
                HttpURLConnection connection = null;
                try {
                    Log.d("HttpUtil", "address:" + address);
                    Log.d("HttpUtil", "map:" + map);
                    Log.d("HttpUtil", "map.toString():" + map.toString());
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setReadTimeout(30 * 1000);
                    connection.setConnectTimeout(30 * 1000);
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    // Post 请求不能使用缓存
                    connection.setUseCaches(false);
                    connection.setInstanceFollowRedirects(true);
//                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.connect();

                    if (map != null && map.size() > 0) {
                        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                        byte[] content = map.toString().getBytes("utf-8");
                        out.write(content, 0, content.length);
                        out.flush();
                        out.close();
                        Log.d("HttpUtil", "content:" + content.toString());
                    }
                    Log.d("HttpUtil", "connection:" + connection);
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                    StringBuilder response = new StringBuilder();
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                    Log.d("HttpUtil", "response:" + response.toString());

                    if (listener != null) {
                        Gson gson = new Gson();
                        listener.onFinish(gson.fromJson(response.toString(), ResponseBO.class));
                    }
                } catch (Exception e) {
                    Log.d("HttpUtil", "SendPostRequest异常:" + e.getMessage());
                    if (listener != null) {
                        listener.onError(e);
                    }
                } finally {
                    if (connection != null)
                        connection.disconnect();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
