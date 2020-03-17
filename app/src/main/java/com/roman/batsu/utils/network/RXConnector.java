package com.roman.batsu.utils.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.roman.batsu.utils.api.ApiClient;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RXConnector {

    private Retrofit retrofit = null;
    private static final String MAIN_URL = "http://mir-dverei.by/";
    /**
     *
     * get XML RetrofitClient xml
     * приходит в
     * некоторых ответах,
     * в частности
     * в поездах
     */

    private Retrofit getRetrofitClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(MAIN_URL).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }


    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();


    /* Расписание и новости*/
    public ApiClient getScheduleApiInterface() {
        return getRetrofitClient()
                .create(ApiClient.class);
    }






}
