// (c)2016 Flipboard Inc, All Rights Reserved.

package com.poomoo.api;


import com.poomoo.api.api.MyApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;


public class NetWork {
    private static MyApi myApi;
//    private static UploadApi uploadApi;

    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
    public static HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;


    public static MyApi getMyApi() {
        if (myApi == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(level);
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().addInterceptor(loggingInterceptor);
            clientBuilder.connectTimeout(1, TimeUnit.MINUTES);
            Retrofit retrofit = new Retrofit.Builder()
                    .client(clientBuilder.build())
                    .baseUrl(NetConfig.url)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            myApi = retrofit.create(MyApi.class);
        }
        return myApi;
    }

//    public static UploadApi getUploadApi() {
//        if (uploadApi == null) {
//            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().addInterceptor(loggingInterceptor);
//            clientBuilder.connectTimeout(1, TimeUnit.MINUTES);
//            Retrofit retrofit = new Retrofit.Builder()
//                    .client(clientBuilder.build())
//                    .baseUrl(NetConfig.url)
//                    .addConverterFactory(gsonConverterFactory)
//                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
//                    .build();
//            uploadApi = retrofit.create(UploadApi.class);
//        }
//        return uploadApi;
//    }

}
