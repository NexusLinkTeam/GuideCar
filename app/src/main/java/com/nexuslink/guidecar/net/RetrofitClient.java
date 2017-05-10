package com.nexuslink.guidecar.net;

import com.nexuslink.guidecar.util.Config;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit请求客户端
 * Created by Rye on 2017/5/10.
 */

public class RetrofitClient {

    private static Retrofit instance;

    public static Retrofit getInstance(){
        if(instance == null){
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .build();

            instance = new Retrofit.Builder()
                    .baseUrl(Config.HOST_NAME)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return instance;
    }

    public static  <T> T Create (Class<T> service){
        return getInstance().create(service);
    }
}
