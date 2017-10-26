package com.dongjiquan.dongjiquan.http;

import android.util.Log;

import com.dongjiquan.dongjiquan.utils.UrlUtil;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wlx on 2017/9/28.
 */

public class Api {
    private static ApiService APISERVICE;
    public static ApiService getService(){
            if (APISERVICE==null){
                APISERVICE=new Retrofit.Builder().baseUrl(UrlUtil.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .client(getOkHttpClient())
                        .build().create(ApiService.class);

            }
        return APISERVICE;
    }

    /**
     * 打印请求日志
     * @return
     */
    private static OkHttpClient getOkHttpClient() {
        //显示日志级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("wlx", "OkHttpClient==log: "+message );
            }
        });
        loggingInterceptor.setLevel(level);
        //定制OkHttp
        OkHttpClient.Builder client=new OkHttpClient.Builder();
        //OkHttp进行添加拦截器
        client.addInterceptor(loggingInterceptor);
        return client.build();
    }

}
